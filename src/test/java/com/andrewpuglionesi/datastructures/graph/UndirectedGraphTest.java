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

public class UndirectedGraphTest {

    @Test
    void addEdgeToGraphWhenNodesExist() {
        UndirectedGraph<String> graph = new UndirectedGraph<>() {{
            addNode("Bob");
            addNode("Alice");
            addEdge("Bob", "Alice");
        }};
        assertEquals(2, graph.size());
        assertEquals(Collections.singletonList("Alice"), graph.getNeighbors("Bob"));
        assertEquals(Collections.singletonList("Bob"), graph.getNeighbors("Alice"));
        assertTrue(graph.containsEdge("Bob", "Alice"));
        assertTrue(graph.containsEdge("Alice", "Bob"));
    }

    @Test
    void addEdgeToGraphBeforeNodesExist() {
        UndirectedGraph<String> graph = new UndirectedGraph<>() {{
            addEdge("Bob", "Alice");
        }};
        assertEquals(2, graph.size());
        assertEquals(Collections.singletonList("Alice"), graph.getNeighbors("Bob"));
        assertEquals(Collections.singletonList("Bob"), graph.getNeighbors("Alice"));
        assertTrue(graph.containsEdge("Bob", "Alice"));
        assertTrue(graph.containsEdge("Alice", "Bob"));
    }

    @Test
    void addEdgeMultipleEdgesFromOneNode() {
        UndirectedGraph<String> graph = new UndirectedGraph<>() {{
            addEdge("Bob", "Alice");
            addEdge("Bob", "Darren");
        }};
        assertEquals(3, graph.size());
        assertTrue(equalsIgnoreOrder(graph.getNeighbors("Bob"), List.of("Alice", "Darren")));
        assertEquals(Collections.singletonList("Bob"), graph.getNeighbors("Alice"));
        assertEquals(Collections.singletonList("Bob"), graph.getNeighbors("Darren"));
        assertTrue(graph.containsEdge("Bob", "Alice"));
        assertTrue(graph.containsEdge("Alice", "Bob"));
        assertTrue(graph.containsEdge("Bob", "Darren"));
        assertTrue(graph.containsEdge("Darren", "Bob"));
    }

    @Test
    void addEdgeDuplicateEdge() {
        UndirectedGraph<String> graph = new UndirectedGraph<>() {{
            addEdge("Bob", "Alice");
            addEdge("Bob", "Alice");
        }};
        assertEquals(2, graph.size());
        assertEquals(Collections.singletonList("Alice"), graph.getNeighbors("Bob"));
        assertEquals(Collections.singletonList("Bob"), graph.getNeighbors("Alice"));
        assertTrue(graph.containsEdge("Bob", "Alice"));
        assertTrue(graph.containsEdge("Alice", "Bob"));
    }

    @Test
    void addEdgeReflexiveEdge() {
        UndirectedGraph<String> graph = new UndirectedGraph<>() {{
            addEdge("Bob", "Bob");
        }};
        assertEquals(1, graph.size());
        assertEquals(Collections.singletonList("Bob"), graph.getNeighbors("Bob"));
        assertTrue(graph.containsEdge("Bob", "Bob"));
    }

    @Test
    void addEdgeCycle() {
        UndirectedGraph<String> graph = new UndirectedGraph<>() {{
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
        UndirectedGraph<String> graph = new UndirectedGraph<>() {{
            removeEdge("Bob", "Alice");
        }};
        assertEquals(0, graph.size());
    }

    @Test
    void removeEdgeDestinationNodeDoesNotExist() {
        UndirectedGraph<String> graph = new UndirectedGraph<>() {{
            addNode("Bob");
            removeEdge("Bob", "Alice");
        }};
        assertEquals(1, graph.size());
        assertTrue(graph.getNeighbors("Bob").isEmpty());
    }

    @Test
    void removeEdge() {
        UndirectedGraph<String> graph = new UndirectedGraph<>() {{
            addEdge("Bob", "Alice");
            removeEdge("Bob", "Alice");
        }};
        assertEquals(2, graph.size());
        assertTrue(graph.getNeighbors("Bob").isEmpty());
        assertTrue(graph.getNeighbors("Alice").isEmpty());
        assertFalse(graph.containsEdge("Bob", "Alice"));
        assertFalse(graph.containsEdge("Alice", "Bob"));
    }

    @Test
    void removeSameEdgeTwice() {
        UndirectedGraph<String> graph = new UndirectedGraph<>() {{
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
        UndirectedGraph<String> graph = new UndirectedGraph<>() {{
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
        UndirectedGraph<String> graph = new UndirectedGraph<>(List.of("a", "b"));
        List<String> nodes = new ArrayList<>();
        graph.iterator().forEachRemaining(nodes::add);
        assertTrue(equalsIgnoreOrder(List.of("a", "b"), nodes));
    }

    @Test
    void getNeighborsNodeDoesNotExist() {
        UndirectedGraph<String> graph = new UndirectedGraph<>(List.of("a", "b"));
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
