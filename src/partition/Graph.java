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

import java.util.HashMap;
import java.util.Map;

/**
 * Undirected graph.
 *
 * @author gburdell
 */
public class Graph {
    /**
     * Add vertex to graph.
     * @param vx vertex to add.
     * @return true if added; else false if another vertex by same name exists,
     */
    public boolean addVertex(final Vertex vx) {
        if (m_vxByName.containsKey(vx.getName())) {
            return false;
        }
        m_vxByName.put(vx.getName(), vx);
        return true;
    }
    
    public void setVerticesById() throws PartitionException {
        if (null != m_vxById) {
            gblib.Util.abnormalExit("m_vxById already set");
        }
        int maxId = -1;
        for (Vertex vx : m_vxByName.values()) {
            if (maxId < vx.getId()) {
                maxId = vx.getId();
            }
        }
        //a sanity check
        if (maxId != m_vxByName.size()) {
            throw new PartitionException("PG-CREATE-2", maxId, m_vxByName.size());
        }
        m_vxById = new Vertex[maxId+1];
        for (Vertex vx : m_vxByName.values()) {
            m_vxById[vx.getId()] = vx;
        }
        //[0] refers to (virtual) ports and not user set
        assert (null == m_vxById[0]);
    }
    /**
     * Vertices by name,
     */
    private final Map<Name,Vertex>    m_vxByName = new HashMap<>();
    /**
     * Vertices by id (which is reference in edge set).
     */
    private Vertex  m_vxById[];
}
