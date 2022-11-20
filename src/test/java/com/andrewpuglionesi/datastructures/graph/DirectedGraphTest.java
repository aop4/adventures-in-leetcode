package com.andrewpuglionesi.datastructures.graph;

import com.andrewpuglionesi.datastructures.Counter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectedGraphTest {

    @Test
    void addEdgeToGraphWhenNodesExist() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addNode("Bob");
            addNode("Alice");
            addEdge("Bob", "Alice");
        }};
        assertEquals(2, graph.size());
        assertEquals(Collections.singletonList("Alice"), graph.getNeighbors("Bob"));
        assertTrue(graph.getNeighbors("Alice").isEmpty());
        assertTrue(graph.containsEdge("Bob", "Alice"));
        assertFalse(graph.containsEdge("Alice", "Bob"));
    }

    @Test
    void addEdgeToGraphBeforeNodesExist() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addEdge("Bob", "Alice");
        }};
        assertEquals(2, graph.size());
        assertEquals(Collections.singletonList("Alice"), graph.getNeighbors("Bob"));
        assertTrue(graph.getNeighbors("Alice").isEmpty());
        assertTrue(graph.containsEdge("Bob", "Alice"));
        assertFalse(graph.containsEdge("Alice", "Bob"));
    }

    @Test
    void addEdgeMultipleEdgesFromOneNode() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addEdge("Bob", "Alice");
            addEdge("Bob", "Darren");
        }};
        assertEquals(3, graph.size());
        assertTrue(equalsIgnoreOrder(graph.getNeighbors("Bob"), List.of("Alice", "Darren")));
        assertTrue(graph.getNeighbors("Alice").isEmpty());
        assertTrue(graph.getNeighbors("Darren").isEmpty());

        assertTrue(graph.containsEdge("Bob", "Alice"));
        assertTrue(graph.containsEdge("Bob", "Darren"));
    }

    @Test
    void addEdgeDuplicateEdge() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addEdge("Bob", "Alice");
            addEdge("Bob", "Alice");
        }};
        assertEquals(2, graph.size());
        assertEquals(Collections.singletonList("Alice"), graph.getNeighbors("Bob"));
        assertTrue(graph.getNeighbors("Alice").isEmpty());
        assertTrue(graph.containsEdge("Bob", "Alice"));
    }

    @Test
    void addEdgeReflexiveEdge() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addEdge("Bob", "Bob");
        }};
        assertEquals(1, graph.size());
        assertEquals(Collections.singletonList("Bob"), graph.getNeighbors("Bob"));
        assertTrue(graph.containsEdge("Bob", "Bob"));
    }

    @Test
    void addEdgeCycle() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addEdge("Bob", "Alice");
            addEdge("Alice", "Bob");
        }};
        assertEquals(2, graph.size());
        assertEquals(Collections.singletonList("Alice"), graph.getNeighbors("Bob"));
        assertEquals(Collections.singletonList("Bob"), graph.getNeighbors("Alice"));
        assertTrue(graph.containsEdge("Bob", "Alice"));
        assertTrue(graph.containsEdge("Alice", "Bob"));
    }

    @Test
    void removeEdgeBothNodesDoNotExist() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            removeEdge("Bob", "Alice");
        }};
        assertEquals(0, graph.size());
    }

    @Test
    void removeEdgeDestinationNodeDoesNotExist() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addNode("Bob");
            removeEdge("Bob", "Alice");
        }};
        assertEquals(1, graph.size());
        assertTrue(graph.getNeighbors("Bob").isEmpty());
    }

    @Test
    void removeEdge() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addEdge("Bob", "Alice");
            removeEdge("Bob", "Alice");
        }};
        assertEquals(2, graph.size());
        assertTrue(graph.getNeighbors("Bob").isEmpty());
        assertTrue(graph.getNeighbors("Alice").isEmpty());
        assertFalse(graph.containsEdge("Bob", "Alice"));
    }

    @Test
    void removeSameEdgeTwice() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addEdge("Bob", "Alice");
            removeEdge("Bob", "Alice");
            removeEdge("Bob", "Alice");
        }};
        assertEquals(2, graph.size());
        assertTrue(graph.getNeighbors("Bob").isEmpty());
        assertTrue(graph.getNeighbors("Alice").isEmpty());
    }

    @Test
    void removeEdgeReflexiveEdge() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addEdge("Bob", "Bob");
            removeEdge("Bob", "Bob");
        }};
        assertEquals(1, graph.size());
        assertTrue(graph.getNeighbors("Bob").isEmpty());
        assertFalse(graph.containsEdge("Bob", "Bob"));
    }

    private <T> boolean equalsIgnoreOrder(Collection<T> c1, Collection<T> c2) {
        return (new Counter<>(c1)).equals(new Counter<>(c2));
    }

    @Test
    void collectionConstructor() {
        DirectedGraph<String> graph = new DirectedGraph<>(List.of("a", "b"));
        List<String> nodes = new ArrayList<>();
        graph.iterator().forEachRemaining(nodes::add);
        assertTrue(equalsIgnoreOrder(List.of("a", "b"), nodes));
    }

    @Test
    void getNeighborsNodeDoesNotExist() {
        DirectedGraph<String> graph = new DirectedGraph<>(List.of("a", "b"));
        assertThrows(NoSuchElementException.class, () -> graph.getNeighbors("x"));
    }

    @Test
    void addNodeDuplicateAddition() {
        DirectedGraph<Integer> graph = new DirectedGraph<>();
        assertThrows(UnsupportedOperationException.class, () -> {
            graph.addNode(1);
            graph.addNode(1);
        });
    }
}
