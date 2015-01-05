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
package partition;

/**
 * Graph Vertex.
 * @author gburdell
 */
public class Vertex {
    public Vertex(final Name name) {
        m_name = name;
    }

    public float getArea() {
        return m_area;
    }

    public int getId() {
        return m_id;
    }

    public int getLeafCnt() {
        return m_leafCnt;
    }

    public int getMacroCnt() {
        return m_macroCnt;
    }

    public Name getName() {
        return m_name;
    }

    public Name getDesignName() {
        return m_designName;
    }
    
    public void setArea(float m_area) {
        this.m_area = m_area;
    }

    public void setId(int m_id) {
        this.m_id = m_id;
    }

    public void setLeafCnt(int m_leafCnt) {
        this.m_leafCnt = m_leafCnt;
    }

    public void setMacroCnt(int m_macroCnt) {
        this.m_macroCnt = m_macroCnt;
    }

    public void setDesignName(Name m_designName) {
        this.m_designName = m_designName;
    }

    private final Name    m_name;
    private Name m_designName;
    private int m_id = -1, m_leafCnt = -1, m_macroCnt = -1;
    private float m_area = -1f;
}
