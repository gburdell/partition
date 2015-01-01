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
import apfe.runtime.ParseError;
import gblib.Util;
import partition.pgparse.generated.Grammar;

/**
 * Parse partition graph file.
 *
 * @author gburdell
 */
public class Parser {

    public static void main(final String argv[]) {
        parse(argv[0]);
    }

    private static boolean parse(final String fname) {
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
}
