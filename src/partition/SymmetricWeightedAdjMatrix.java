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

import static gblib.Util.downCast;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import partition.Util.Pair;

/**
 * A symmetric weighed adjacency matrix for undirected graph.
 *
 * @param <T> weight value type.
 * @author gburdell
 */
public class SymmetricWeightedAdjMatrix<T extends Comparable> {

    public SymmetricWeightedAdjMatrix(int n, final T dflt) {
        m_n = n;
        init(dflt);
    }

    private void init(final T dflt) {
        m_matrix = new Object[m_n - 1]; //last row never used
        for (int row = 0; row < m_matrix.length; row++) {
            final int ncols = getNcols(row);
            m_matrix[row] = Array.newInstance(dflt.getClass(), ncols);
            for (int col = 0; col < ncols; col++) {
                Array.set(m_matrix[row], col, dflt);
            }
        }
    }

    public T set(int row, int col, final T val) {
        Pair<Integer, Integer> rowCol = getCoords(row, col);
        Array.set(m_matrix[rowCol.first], rowCol.second, val);
        return val;
    }

    public T get(int row, int col) {
        Pair<Integer, Integer> rowCol = getCoords(row, col);
        return downCast(Array.get(m_matrix[rowCol.first], rowCol.second));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < m_n; row++) {
            sb.append('[');
            for (int col = 0; col < m_n; col++) {
                if (0 < col) {
                    sb.append(',');
                }
                if (row != col) {
                    sb.append(get(row, col));
                } else {
                    sb.append('_');
                }
            }
            sb.append("]\n");
        }
        return sb.toString();
    }

    /**
     * Composite of weight and vertex pair. Used for sorting (by weight).
     * @param <T> weight type.
     */
    public static class Ele<T extends Comparable> {

        public Ele(int vx1, int vx2, T weight) {
            m_vx1 = vx1;
            m_vx2 = vx2;
            m_weight = weight;
        }

        public final int m_vx1, m_vx2;
        public final T m_weight;
    }

    public List<Ele<T>> toList() {
        final List<Ele<T>> list = new LinkedList<>();
        for (int i = 0; i < m_n - 1; i++) {
            for (int j = i + 1; j < m_n; j++) {
                list.add(new Ele<>(i, j, get(i, j)));
            }
        }
        return list;
    }

    /**
     * Sort by by weight.
     *
     * @return Sorted list (in descending order by weight).
     */
    public List<Ele<T>> sort() {
        List<Ele<T>> list = toList();
        Collections.sort(list, (Ele<T> o1, Ele<T> o2)
                -> o2.m_weight.compareTo(o1.m_weight)
        );
        return list;
    }

    private Pair<Integer, Integer> getCoords(int row, int col) {
        gblib.Util.assertTrue(row != col,
                "diagonal reference not allowed: [" + row + "," + col + "]");
        Pair<Integer, Integer> rowcol;
        if (row < col) {
            rowcol = new Pair<>(row, col - (row + 1));
        } else {
            rowcol = new Pair<>(col, row - (col + 1));
        }
        //adjust col offset
        return rowcol;
    }

    private int getNcols(int row) {
        return m_n - row - 1;
    }

    private final int m_n;
    /**
     * Maintain symmetric matrix with valid indices [row,col] where row < col.
     * For example: we have [0,1], [0,2], [0,3], and [1,2], [1,3]. We do not
     * have [1,0] since that is same as [0,1] (and we'll return same value for
     * [0,1] and [1,0]). Since no self-loops, diagonal values [i,i] are invalid.
     */
    private Object m_matrix[];
}
