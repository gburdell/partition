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

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Undirected graph.
 *
 * @author gburdell
 */
public class Graph {

    /**
     * Add vertex to graph.
     *
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

    public boolean addEdge(final Edge edge) {
        if (m_edgeByName.containsKey(edge.getName())) {
            return false;
        }
        m_edgeByName.put(edge.getName(), edge);
        return true;
    }

    /**
     * Get edges connected to this Vertex.
     *
     * @param vx vertex to get edges of.
     * @return edges connected to vertex.
     */
    public List<Edge> getEdgesOfVertex(final Vertex vx) {
        final List<Edge> edges = new LinkedList<>();
        for (Edge edge : m_edgeByName.values()) {
            if (edge.containsVertex(vx)) {
                edges.add(edge);
            }
        }
        return edges;
    }

    public Vertex[] getVxById() {
        return m_vxById;
    }

    public Map<Name, Edge> getEdgeByName() {
        return m_edgeByName;
    }

    public Map<Name, Vertex> getVxByName() {
        return m_vxByName;
    }

    public void setVerticesById() throws PartitionException {
        if (null != m_vxById) {
            gblib.Util.abnormalExit("m_vxById already set");
        }
        final int maxId = Collections.max(m_vxByName.values(),
                (i, j) -> Integer.compare(i.getId(), j.getId())).getId();
        //a sanity check
        if (maxId != m_vxByName.size()) {
            throw new PartitionException("PG-CREATE-2", maxId, m_vxByName.size());
        }
        m_vxById = new Vertex[maxId + 1];
        for (Vertex vx : m_vxByName.values()) {
            m_vxById[vx.getId()] = vx;
        }
        //[0] refers to (virtual) ports and not user set
        assert (null == m_vxById[0]);
    }

    /**
     * Create adjacency matrix where weight is edge count between vertices.
     *
     * @return adjacency matrix (as symmetrical matrix).
     */
    public SymmetricWeightedAdjMatrix<Integer> getAdjMatrix() {
        final int N = m_vxById.length;
        final SymmetricWeightedAdjMatrix<Integer> mat
                = new SymmetricWeightedAdjMatrix<>(N, -1);
        for (int vxi = 0; vxi < N - 1; vxi++) {
            for (int vxj = vxi + 1; vxj < N; vxj++) {
                int edgeCnt = 0;
                for (Edge edge : m_edgeByName.values()) {
                    if (edge.adjacent(m_vxById[vxi], m_vxById[vxj])) {
                        edgeCnt++;
                    }
                }
                mat.set(vxi, vxj, edgeCnt);
            }
        }
        return mat;
    }

    /**
     * Vertices by name,
     */
    private final Map<Name, Vertex> m_vxByName = new HashMap<>();
    /**
     * Vertices by id (which is reference in edge set).
     */
    private Vertex m_vxById[];
    /**
     * Edges by name.
     */
    private final Map<Name, Edge> m_edgeByName = new HashMap<>();
}
