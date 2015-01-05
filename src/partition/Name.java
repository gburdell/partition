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
import java.util.Arrays;
import java.util.List;

/**
 * A hierarchical name with optional bit select.
 * @author gburdell
 */
public class Name {
    public static final String stHierSep = "/";
    
    public Name(final List<String> eles) {
        this(eles, -1);
    }
    public Name(final List<String> eles, final int bit) {
        m_nameEles = eles.toArray(new String[eles.size()]);
        m_bit = bit;
    }
    @Override
    public boolean equals(final Object other) {
        boolean eq = false;
        if (other instanceof Name) {
            final Name otherNm = downCast(other);
            if (m_nameEles.length == otherNm.m_nameEles.length) {
                for (int i = 0; i < m_nameEles.length; i++) {
                    if (!m_nameEles[i].equals(otherNm.m_nameEles[i])) {
                        return false;
                    }
                }
                eq = true;
            }
        }
        return eq;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Arrays.deepHashCode(m_nameEles);
        hash = 89 * hash + m_bit;
        return hash;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = getBaseNameX();
        if (hasBit()) {
            sb.append('[').append(getBit()).append(']');
        }
        return sb.toString();
    }
    
    public boolean hasBit() {
        return (0 <= m_bit);
    }
    
    public int getBit() {
        return m_bit;
    }
    
    /**
     * Get name without bit.
     * @return name without bit.
     */
    public String getBaseName() {
        return getBaseNameX().toString();
    }
    
    private StringBuilder getBaseNameX() {
        StringBuilder sb = new StringBuilder();
        for (String ele : m_nameEles) {
            if (0 < sb.length()) {
                sb.append(stHierSep);
            }
            sb.append(ele);
        }
        return sb;
    }
    
    private final String    m_nameEles[];
    private final int       m_bit;
}
