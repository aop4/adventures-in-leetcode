package com.andrewpuglionesi.datastructures.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

    @Override
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public boolean isConnectedGraph() {
        if (this.isEmpty()) {
            return false;
        }
        // In a directed graph, a DFS from a single node cannot determine whether the graph is *strongly* connected.
        // Consider a directed graph {A->B, A->C, A->D}. If we start the search with A, the graph seems connected as
        // heck, but B, C, and D have zero connections. So the obvious approach is to do a DFS from *every* node.
        // This is slightly optimized by maintaining a set of nodes known to have full connectivity--whenever we
        // encounter a member of the set, we know we can reach every other node, and thus the point of origin for the
        // search iteration must also be fully connected.
        // It turns out there's another algorithm for strong connectivity that runs in linear time. Oh well.
        final Set<T> fullyConnectedNodes = new HashSet<>(); // Nodes that are known to be connected to all other nodes
        for (final T node : this) {
            final Set<T> visited = new HashSet<>();
            final boolean canReachConnectedNode = this.canReachConnectedNode(node, visited, fullyConnectedNodes);
            if (canReachConnectedNode || visited.size() == this.size()) {
                fullyConnectedNodes.add(node);
            } else {
                return false; // if we find one node that is not connected to all others, the graph isn't connected
            }
        }
        return true;
    }

    /**
     * @param source a node's value.
     * @param visited set of nodes that have already been visited by this recursive search.
     * @param fullyConnectedNodes a set of nodes, each of which is known to be fully connected to all other nodes.
     * @return true if {@code source} is directly or indirectly connected to a node within {@code fullyConnectedNodes}.
     */
    private boolean canReachConnectedNode(final T source, final Set<T> visited, final Set<T> fullyConnectedNodes) {
        if (fullyConnectedNodes.contains(source)) {
            return true;
        }
        visited.add(source);
        for (final T neighbor : this.getNeighbors(source)) {
            if (!visited.contains(neighbor) && this.canReachConnectedNode(neighbor, visited, fullyConnectedNodes)) {
                return true;
            }
        }
        return false; // Neither this node nor any of its (indirect) connections was in fullyConnectedNodes
    }
}
