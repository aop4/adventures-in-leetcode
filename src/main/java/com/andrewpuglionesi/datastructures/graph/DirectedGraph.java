package com.andrewpuglionesi.datastructures.graph;

import java.util.Collection;

/**
 * A directed graph, in which creating a new edge is a directional operation from one node to another.
 * @param <T> @param <T> the data type of nodes' values. This type should have a reliable hashCode() and equals()
 *           implementation, as the graph uses a hash table to store and retrieve nodes.
 */
@SuppressWarnings("PMD.ShortVariable")
public class DirectedGraph<T> extends Graph<T> {

    /**
     * See {@link Graph#Graph()}
     */
    public DirectedGraph() {
        super();
    }

    /**
     * See {@link Graph#Graph(Collection)}
     */
    public DirectedGraph(final Collection<T> values) {
        super(values);
    }

    @Override
    public void addEdge(final T from, final T to) {
        super.insertEdge(from, to);
    }

    @Override
    public void removeEdge(final T from, final T to) {
        super.deleteEdge(from, to);
    }
}
