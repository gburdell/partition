/*
 * The MIT License
 *
 * Copyright 2015 gburdell.
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
package partition;

import gblib.MessageMgr;
import java.util.logging.Level;
import java.util.logging.Logger;
import partition.pgparse.Parser;
import static partition.Util.error;

/**
 *
 * @author gburdell
 */
public class Main {
    public static void main(final String argv[]) {
        stTheOne.process(argv);
    }

    private void process(final String argv[]) {
        try {
            m_graph = Parser.create(argv[0]);
            if (null == m_graph) {
                error("PG-CREATE-1");
            }
        } catch (PartitionException ex) {
            ex.asError();
            assert false;//never get here
        }
    }
    
    private final static Main stTheOne = new Main();
    
    private Graph   m_graph;
    
    static {
        final String fname = System.getProperty("partition.messages");
        if (null != fname) {
            MessageMgr.addMessages(fname);
        }
    }
}
