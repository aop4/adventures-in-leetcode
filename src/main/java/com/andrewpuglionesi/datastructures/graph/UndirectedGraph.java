package com.andrewpuglionesi.datastructures.graph;

import java.util.Collection;

/**
 * An undirected graph, in which each edge can be traversed from either node that it connects. (This is accomplished by
 * creating two directional edges whenever a single edge is added).
 * @param <T> @param <T> the data type of nodes' values. This type should have a reliable hashCode() and equals()
 *           implementation, as the graph uses a hash table to store and retrieve nodes.
 */
@SuppressWarnings("PMD.ShortVariable")
public class UndirectedGraph<T> extends Graph<T> {

    /**
     * See {@link Graph#Graph()}
     */
    public UndirectedGraph() {
        super();
    }

    /**
     * See {@link Graph#Graph(Collection)}
     */
    public UndirectedGraph(final Collection<T> values) {
        super(values);
    }

    @Override
    public void addEdge(final T from, final T to) {
        super.insertEdge(from, to);
        super.insertEdge(to, from);
    }

    @Override
    public void removeEdge(final T from, final T to) {
        super.deleteEdge(from, to);
        super.deleteEdge(to, from);
    }
}
