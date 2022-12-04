package com.andrewpuglionesi.datastructures.graph;

import com.andrewpuglionesi.datastructures.Counter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void equalsNUll() {
        DirectedGraph<Integer> graph = new DirectedGraph<>() {{
            addEdge(1, 2);
        }};
        assertFalse(graph.equals(null));
    }

    @Test
    void equalsObjectThatIsNotAGraph() {
        DirectedGraph<Integer> graph = new DirectedGraph<>() {{
            addEdge(1, 2);
        }};
        assertFalse(graph.equals("Daffy Duck"));
    }

    @Test
    void equalsGraphWithDifferentDataType() {
        DirectedGraph<Integer> graph1 = new DirectedGraph<>() {{
            addEdge(1, 2);
        }};
        DirectedGraph<String> graph2 = new DirectedGraph<>() {{
            addEdge("John", "Yoko");
        }};
        assertFalse(graph1.equals(graph2));
        assertNotEquals(graph1.hashCode(), graph2.hashCode());
    }

    @Test
    void equalsGraphWithDifferentEdges() {
        DirectedGraph<Integer> graph1 = new DirectedGraph<>() {{
            addEdge(1, 2);
        }};
        DirectedGraph<Integer> graph2 = new DirectedGraph<>() {{
            addEdge(2, 1);
        }};
        assertFalse(graph1.equals(graph2));
        assertNotEquals(graph1.hashCode(), graph2.hashCode());
    }

    @Test
    void equalsGraphWithSameEdges() {
        DirectedGraph<Integer> graph1 = new DirectedGraph<>() {{
            addEdge(1, 2);
            addEdge(1, 3);
            addEdge(2, 3);
        }};
        DirectedGraph<Integer> graph2 = new DirectedGraph<>() {{
            addEdge(1, 2);
            addEdge(1, 3);
            addEdge(2, 3);
        }};
        assertTrue(graph1.equals(graph2));
        assertEquals(graph1.hashCode(), graph2.hashCode());
    }

    @Test
    void distanceBetweenOriginDoesNotExist() {
        DirectedGraph<String> graph = new DirectedGraph<>();
        assertEquals(-1, graph.distanceBetween("Bob", "Alice"));
        graph.addEdge("John", "Yoko");
        assertEquals(-1, graph.distanceBetween("Bob", "Yoko"));
    }

    @Test
    void distanceBetweenDestinationDoesNotExist() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addEdge("John", "Yoko");
        }};
        assertEquals(-1, graph.distanceBetween("John", "Alice"));
    }

    @Test
    void distanceBetweenSelfDirectedEdgeExists() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addEdge("Bob", "Bob");
        }};
        assertEquals(1, graph.distanceBetween("Bob", "Bob"));
    }

    @Test
    void distanceBetweenSelfDirectedEdgeDoesNotExist() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addEdge("George", "Ringo");
        }};
        assertEquals(-1, graph.distanceBetween("George", "George"));
    }

    @Test
    void distanceBetweenNextDoorNeighbor() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addEdge("John", "Yoko");
        }};
        assertEquals(1, graph.distanceBetween("John", "Yoko"));
    }

    @Test
    void distanceBetweenQueryIsInWrongDirection() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addEdge("John", "Yoko");
        }};
        assertEquals(-1, graph.distanceBetween("Yoko", "John"));
    }

    @Test
    void distanceBetweenCycle() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            addEdge("George", "Ringo");
            addEdge("Ringo", "George");
        }};
        assertEquals(2, graph.distanceBetween("George", "George"));
        // the below tests that the graph does not cycle infinitely in search of the destination
        assertEquals(-1, graph.distanceBetween("George", "Yoko"));
    }

    @Test
    void distanceBetweenLongPath() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            // John -> Yoko -> Paul -> Ringo -> George
            addEdge("John", "Yoko");
            addEdge("Yoko", "Paul");
            addEdge("Paul", "Ringo");
            addEdge("Ringo", "George");
        }};
        assertEquals(4, graph.distanceBetween("John", "George"));
        assertEquals(3, graph.distanceBetween("Yoko", "George"));
    }

    @Test
    void distanceBetweenChoosesShortestPath() {
        DirectedGraph<String> graph = new DirectedGraph<>() {{
            // John -> John
            // John -> Yoko -> Jimi -> George
            // John -> Paul -> George
            addEdge("John", "John");

            addEdge("John", "Yoko");
            addEdge("Yoko", "Jimi");
            addEdge("Jimi", "George");

            addEdge("John", "Paul");
            addEdge("Paul", "George");
        }};
        assertEquals(2, graph.distanceBetween("John", "George"));
    }
}
