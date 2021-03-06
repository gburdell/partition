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

import java.util.LinkedList;
import java.util.List;

/**
 * Graph edge.
 *
 * @author gburdell
 */
public class Edge {

    public Edge(final Name name) {
        m_name = name;
    }

    /**
     * Add vertex to this edge.
     *
     * @param vx vertex to add.
     */
    public void addVertex(final Vertex vx) {
        m_vertices.add(vx);
    }

    public Name getName() {
        return m_name;
    }

    /**
     * Test if this edge is connected to a specific Vertex.
     *
     * @param vx vertex to check.
     * @return true if edge is connected to vertex.
     */
    public boolean containsVertex(final Vertex vx) {
        return m_vertices.contains(vx);
    }

    /**
     * Test if this edge connects to specified vertices.
     * @param vxs vertices to check for adjacency.
     * @return true if all vertices adjacent.
     */
    public boolean adjacent(final Vertex... vxs) {
        for (Vertex vx : vxs) {
            if (! containsVertex(vx)) {
                return false;
            }
        }
        return true;
    }

    private final Name m_name;
    /**
     * Collection of vertices connected by this edge.
     */
    private final List<Vertex> m_vertices = new LinkedList<>();
}
