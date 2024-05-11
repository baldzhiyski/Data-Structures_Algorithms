import java.util.*;

public class KruskalAlgorithm {

    /**
     * Finds the minimum spanning tree of a graph using Kruskal's algorithm.
     *
     * @param numberOfVertices the number of vertices in the graph
     * @param edges            the list of edges in the graph
     * @return the list of edges forming the minimum spanning tree
     */
    public static List<Edge> kruskal(int numberOfVertices, List<Edge> edges) {
        // Sort the edges in non-decreasing order of weight
        Collections.sort(edges);

        // Initialize an empty forest to hold the minimum spanning tree
        List<Edge> forest = new ArrayList<>();

        // Initialize an array to track the parent of each vertex
        int[] parents = new int[numberOfVertices];
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i; // Each vertex is initially its own parent
        }

        // Iterate over the sorted edges
        while (!edges.isEmpty()) {
            // Get the edge with the smallest weight
            Edge edge = edges.remove(0);

            // Get the vertices connected by this edge
            int source = edge.getStartNode();
            int destination = edge.getEndNode();

            // Find the root of the trees containing the source and destination vertices
            int firstRoot = findRoot(source, parents);
            int secondRoot = findRoot(destination, parents);

            // If adding this edge does not create a cycle, add it to the forest
            if (firstRoot != secondRoot) {
                forest.add(edge);
                // Union the trees by setting the parent of one tree's root to the other tree's root
                parents[firstRoot] = secondRoot;
            }
        }

        // Return the minimum spanning tree
        return forest;
    }

    /**
     * Finds the root of the tree containing the specified node.
     *
     * @param node    the node whose root is to be found
     * @param parents the array containing the parent of each node
     * @return the root of the tree containing the specified node
     */
    public static int findRoot(int node, int[] parents) {
        // Traverse the parent array until reaching the root
        while (parents[node] != node) {
            node = parents[node];
        }
        // Return the root of the tree
        return node;
    }
}