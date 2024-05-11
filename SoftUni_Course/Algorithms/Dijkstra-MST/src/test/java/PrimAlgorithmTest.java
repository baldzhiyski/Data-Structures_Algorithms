import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class PrimAlgorithmTest {
    @Test
    public void testPrimAlgorithm() {
        // Create edges for a sample graph
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 4));
        edges.add(new Edge(0, 7, 8));
        edges.add(new Edge(1, 2, 8));
        edges.add(new Edge(1, 7, 11));
        edges.add(new Edge(2, 3, 7));
        edges.add(new Edge(2, 5, 4));
        edges.add(new Edge(2, 8, 2));
        edges.add(new Edge(3, 4, 9));
        edges.add(new Edge(3, 5, 14));
        edges.add(new Edge(4, 5, 10));
        edges.add(new Edge(5, 6, 2));
        edges.add(new Edge(6, 7, 1));
        edges.add(new Edge(6, 8, 6));
        edges.add(new Edge(7, 8, 7));

        // Expected minimum spanning tree edges for the sample graph
        List<Edge> expected = Arrays.asList(
                new Edge(0, 1, 4),
                new Edge(1, 2, 8),
                new Edge(2, 3, 7),
                new Edge(3, 4, 9),
                new Edge(2, 5, 4),
                new Edge(5, 6, 2),
                new Edge(6, 7, 1),
                new Edge(2, 8, 2)
        );

        // Find the minimum spanning tree using Prim's algorithm
        List<Edge> minimumSpanningTree = PrimAlgorithm.primAlg(9, edges);

        // Sort the edges for comparison
        Collections.sort(minimumSpanningTree, Comparator.comparing(Edge::toString));
        Collections.sort(expected, Comparator.comparing(Edge::toString));

        // Assert that the actual minimum spanning tree matches the expected one
        assertEquals(expected, minimumSpanningTree);
    }
}
