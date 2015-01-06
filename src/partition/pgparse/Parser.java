/*
 * The MIT License
 *
 * Copyright 2014 gburdell.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package partition.pgparse;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBufState;
import apfe.runtime.CharBuffer;
import apfe.runtime.InputStream;
import apfe.runtime.Marker;
import apfe.runtime.ParseError;
import apfe.runtime.PrioritizedChoice;
import apfe.runtime.Repetition;
import apfe.runtime.Sequence;
import static apfe.runtime.Util.extractEle;
import static apfe.runtime.Util.extractList;
import gblib.Util;
import static gblib.Util.downCast;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import partition.Graph;
import partition.Name;
import partition.PartitionException;
import static partition.Util.error;
import partition.Vertex;
import partition.pgparse.generated.BitSelect;
import partition.pgparse.generated.CellBodyItem;
import partition.pgparse.generated.CellEntry;
import partition.pgparse.generated.CellName;
import partition.pgparse.generated.EdgeStart;
import partition.pgparse.generated.FullName;
import partition.pgparse.generated.Grammar;
import partition.pgparse.generated.NameEle;
import partition.pgparse.generated.NameMapEntry;
import partition.pgparse.generated.PgFloat;
import partition.pgparse.generated.PgInteger;
import partition.pgparse.generated.PgName;

/**
 * Parse partition graph file to create initial Graph.
 *
 * @author gburdell
 */
public class Parser {

    public static Graph create(final String fname) throws PartitionException {
        Parser parser = new Parser();
        parser.addListeners();
        final boolean ok = parser.parse(fname);
        return ok ? parser.m_graph : null;
    }

    private boolean parse(final String fname) {
        boolean ok = false;
        try {
            //create buffer with no overhead
            final CharBuffer cbuf = InputStream.create(fname, 0f);
            final CharBufState cbufSt = CharBufState.create(cbuf);
            Grammar gram = new Grammar();
            Acceptor parseTree = gram.accept();
            ok = (null != parseTree) && CharBufState.getTheOne().isEOF();
            if (!ok) {
                ParseError.printTopMessage();
            }
        } catch (Exception ex) {
            Util.abnormalExit(ex);
        }
        return ok;
    }

    private void addListeners() {
        NameMapEntry.addListener((Acceptor accepted) -> {
            m_acc = accepted;
            final int key = toInt(extractEle(m_acc, 0));
            final String val = extractEle(m_acc, 1).toString();
            if (m_nameMap.containsKey(key)) {
                error("PGP-MAP-1", getMark(), key);
            } else {
                m_nameMap.put(key, val);
            }
        });
        CellName.addListener((Acceptor accepted) -> {
            m_acc = accepted;
            m_vx = new Vertex(asName(downCast(m_acc)));
            if (false == m_graph.addVertex(m_vx)) {
                error("PG-VX-1", getMark(), m_vx.getName().toString());
            }
        });
        CellBodyItem.addListener((Acceptor accepted) -> {
            m_acc = accepted;
            PrioritizedChoice opt = downCast(m_acc);
            switch (opt.whichAccepted()) {
                case 0: //design
                    Name val = m_vx.getDesignName();
                    if (null != val) {
                        error("PG-VX-2", getMark(), "design", val.toString());
                    }
                    m_vx.setDesignName(asName(extractEle(opt.getAccepted(), 2)));
                    break;
                case 1: //area
                    if (0 <= m_vx.getArea()) {
                        error("PG-VX-2", getMark(), "area", m_vx.getArea());
                    }
                    m_vx.setArea(toFloat(extractEle(opt.getAccepted(), 2)));
                    break;
                case 2:    //id, {leaf,macro}cnt
                    int ival = toInt(extractEle(opt.getAccepted(), 2));
                    PrioritizedChoice pc = extractEle(opt.getAccepted(), 0);
                    switch (pc.whichAccepted()) {
                        case 0: //id
                            if (0 <= m_vx.getId()) {
                                error("PG-VX-2", getMark(), "id", m_vx.getId());
                            }
                            if (0 >= ival) {
                                error("PG-VX-4", getMark(), ival);
                            }
                            m_vx.setId(ival);
                            break;
                        case 1: //leafCnt
                            if (0 <= m_vx.getLeafCnt()) {
                                error("PG-VX-2", getMark(), "leafCnt", m_vx.getLeafCnt());
                            }
                            m_vx.setLeafCnt(ival);
                            break;
                        case 2: //macroCnt
                            if (0 <= m_vx.getMacroCnt()) {
                                error("PG-VX-2", getMark(), "macroCnt", m_vx.getMacroCnt());
                            }
                            m_vx.setMacroCnt(ival);
                            break;
                        default:
                            assert false;
                    }
                    break;
                default:
                    assert false;
            }
        });
        CellEntry.addListener((Acceptor accepted) -> {
            m_acc = accepted;
            final String cellNm = m_vx.getName().toString();
            //check a few things
            if (0 > m_vx.getId()) {
                error("PG-VX-3", getMark(), "id", cellNm);
            }
            if (0 > m_vx.getLeafCnt()) {
                error("PG-VX-3", getMark(), "leafCnt", cellNm);
            }
            if (0 > m_vx.getMacroCnt()) {
                error("PG-VX-3", getMark(), "macroCnt", cellNm);
            }
            if (0f > m_vx.getArea()) {
                error("PG-VX-3", getMark(), "area", cellNm);
            }
            if (null == m_vx.getDesignName()) {
                error("PG-VX-3", getMark(), "design", cellNm);
            }
        });
        EdgeStart.addListener((Acceptor accepted) -> {
            m_acc = accepted;
            try {
                m_graph.setVerticesById();
            } catch (PartitionException ex) {
                ex.asError();
            }
        });
    }

    private String getMark() {
        final Marker mark = m_acc.getStartMark();
        final String loc = gblib.File.getCanonicalPath(mark.getFileName())
                + ":" + mark.toString();
        return loc;
    }

    private static int toInt(final PgInteger val) {
        return Integer.parseInt(val.toString());
    }

    private static float toFloat(final PgFloat val) {
        return Float.parseFloat(val.toString());
    }

    private Name asName(final PgName nm) {
        int ix = -1;
        List<String> baseNm = asFullName(extractEle(nm.getBaseAccepted(), 0));
        Repetition bit = extractEle(nm.getBaseAccepted(), 1);
        if (0 < bit.sizeofAccepted()) {
            BitSelect bs = bit.getOnlyAccepted();
            ix = toInt(extractEle(bs.getBaseAccepted(), 1));
            assert (0 <= ix);
        }
        return new Name(baseNm, ix);
    }

    private List<String> asFullName(final FullName fn) {
        List<String> eles = new LinkedList<>();
        Sequence seq = fn.getBaseAccepted();
        append(eles, extractEle(seq, 0));
        List<NameEle> neles = extractList(extractEle(seq, 1), 1);
        if (null != neles) {
            for (NameEle ele : neles) {
                append(eles, ele);
            }
        }
        return eles;
    }

    private void append(final List<String> eles, final NameEle ele) {
        PrioritizedChoice pc = ele.getBaseAccepted();
        String s;
        if (0 == pc.whichAccepted()) {
            final int key = toInt(extractEle(pc.getAccepted(), 1));
            s = m_nameMap.get(key);
            if (null == s) {
                error("PGP-MAP-2", getMark(), key);
            }
        } else {
            s = pc.getAccepted().toString();
        }
        eles.add(s);
    }
    /**
     * Accepted item during listener/callback processing.
     */
    private Acceptor m_acc;
    /**
     * Current Vertex (cell).
     */
    private Vertex m_vx;
    private final Graph m_graph = new Graph();
    private final Map<Integer, String> m_nameMap = new HashMap<>();
}
