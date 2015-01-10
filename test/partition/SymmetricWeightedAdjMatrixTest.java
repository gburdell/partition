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

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import partition.SymmetricWeightedAdjMatrix.Ele;

/**
 *
 * @author gburdell
 */
public class SymmetricWeightedAdjMatrixTest {
    
    /**
     * Test of set method, of class SymmetricWeightedAdjMatrix.
     */
    @Test
    public void testSet() {
        System.out.println("set");
        final int n = 4;
        SymmetricWeightedAdjMatrix<Integer> mat = new SymmetricWeightedAdjMatrix<>(n,0);
        int val = 1;
        for (int row = 0; row < n; row++) {
            for (int col = row+1; col < n; col++) {
                mat.set(row, col, val++);
            }
        }
        val = 1;
        for (int row = 0; row < n; row++) {
            for (int col = row+1; col < n; col++) {
                assertTrue(val++ == mat.get(row, col));
            }
        }
        System.out.println(mat.toString());
        List<Ele<Integer>> sorted = mat.sort();
    }
    
}
