/*
 * The MIT License
 *
 * Copyright 2014 karlp.
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
package kwp.partition;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import kwp.partition.Util.Pair;

/**
 * Undirected graph.
 *
 * @author karlp
 */
public class Graph {

    private static class Creator {

        /**
         * Create graph from file. 
         *
         * @param fname filename with graph description
         * @return graph created from fname.
         */
        //
        // File has simple syntax expressing adjacency/edges: 
        // graph := (comment | entry)* 
        // comment := #this is a comment 
        // entry := name area ncells (ename numNets)+ 
        // name := is string using '/' as hierarchical separator 
        // area := double (represents area)
        // ncells := integer (number of leaf cells) 
        // numNets := edge weight connecting name (this vertex) to other ename (vertex).
        //
        public static Graph create(String fname) {
            Creator cg = null;
            try {
                cg = new Creator(fname);
            } catch (IOException ex) {
                Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
                Util.error("Abort due to previous error(s)");
            }
            return (null != cg) ? cg.m_graph : null;
        }

        private Graph m_graph;
        private StringTokenizer m_toks;
        private final String m_fname;
        private final LineNumberReader m_rdr;

        private Creator(String fname) throws FileNotFoundException, IOException {
            m_fname = fname;
            m_rdr = new LineNumberReader(new FileReader(fname));
            init();
        }

        private void init() throws IOException {
            String line;
            while (null != (line = m_rdr.readLine())) {
                line = line.trim();
                if (line.isEmpty() || (line.startsWith("#"))) {
                    continue;
                }
                m_toks = new StringTokenizer(line);
                final String vxName = nextToken("vxName");
                final double area = nextTokenAsDouble("area");
                final int ncells = nextTokenAsInt("ncells");
                final List<Pair<String,Integer>> edges = new LinkedList<>();
                while (m_toks.hasMoreTokens()) {
                    String adjVx = nextToken("adjVx");
                    int edgeWt = nextTokenAsInt("edgeWt");
                    edges.add(new Pair(adjVx, edgeWt));
                }
                //TODO: create new vertex and add adjacencies
            }
        }

        private double nextTokenAsDouble(String fld) {
            return nextTokenAsT(fld, (String s) -> Double.parseDouble(s));
        }

        private int nextTokenAsInt(String fld) {
            return nextTokenAsT(fld, (String s) -> Integer.parseInt(s));
        }

        private static interface StringToT<T> {

            public T convert(String s) throws Exception;
        }

        private <T> T nextTokenAsT(String fld, StringToT<T> conv) {
            T val = null;
            final String tok = nextToken(fld);
            try {
                val = conv.convert(tok);
            } catch (Exception ex) {
                error("syntax error processing '" + "' for field '"
                        + fld + "'");
            }
            return val;
        }

        private String nextToken(String fld) {
            String nextToken = null;
            if (m_toks.hasMoreTokens()) {
                nextToken = m_toks.nextToken();
            } else {
                error(": missing field '" + fld + "'");
            }
            return nextToken;
        }

        private void error(String msg) {
            Util.error(m_fname + ":" + m_rdr.getLineNumber() + ": " + msg);
        }
    }
}
