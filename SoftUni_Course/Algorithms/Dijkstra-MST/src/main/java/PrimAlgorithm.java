import java.util.*;

public class PrimAlgorithm {

    /**
     * Finds the minimum spanning tree of a graph using Prim's algorithm.
     *
     * @param numberOfVertices the number of vertices in the graph
     * @param edges            the list of edges in the graph
     * @return the list of edges forming the minimum spanning tree
     */
    public static List<Edge> primAlg(int numberOfVertices, List<Edge> edges) {
        Set<Integer> visited = new HashSet<>();
        Map<Integer, List<Edge>> edgesByNode = new HashMap<>();
        List<Edge> minimumSpanningTree = new ArrayList<>();

        // Organize edges by their start and end nodes
        for (Edge edge : edges) {
            edgesByNode.computeIfAbsent(edge.getStartNode(), k -> new ArrayList<>()).add(edge);
            edgesByNode.computeIfAbsent(edge.getEndNode(), k -> new ArrayList<>()).add(edge);
        }

        // Start from an arbitrary vertex and grow the minimum spanning tree
        int startVertex = edges.get(0).getStartNode();
        visited.add(startVertex);
        // Initialize priority queue to prioritize edges by their weights
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        // Add all edges connected to the starting vertex to the priority queue
        priorityQueue.addAll(edgesByNode.get(startVertex));

        // Traverse until all vertices are included in the minimum spanning tree
        while (!priorityQueue.isEmpty()) {
            // Extract the edge with the smallest weight from the priority queue
            Edge minEdge = priorityQueue.poll();
            // Find the unvisited node connected to the minimum spanning tree by this edge
            int nextNode = getUnvisitedNode(minEdge, visited);
            if (nextNode != -1) {
                // Add the minimum weight edge to the minimum spanning tree
                minimumSpanningTree.add(minEdge);
                // Mark the newly visited node as visited
                visited.add(nextNode);
                // Add all edges connected to the newly visited node to the priority queue
                priorityQueue.addAll(edgesByNode.get(nextNode));
            }
        }

        return minimumSpanningTree;
    }

    /**
     * Finds the unvisited node connected to an edge.
     *
     * @param edge    the edge to check
     * @param visited the set of visited nodes
     * @return the unvisited node connected to the edge, or -1 if no such node exists
     */
    private static int getUnvisitedNode(Edge edge, Set<Integer> visited) {
        int startNode = edge.getStartNode();
        int endNode = edge.getEndNode();
        // Check if one node of the edge is visited and the other is not
        if (visited.contains(startNode) && !visited.contains(endNode)) {
            return endNode;
        } else if (!visited.contains(startNode) && visited.contains(endNode)) {
            return startNode;
        } else {
            return -1;
        }
    }
}
