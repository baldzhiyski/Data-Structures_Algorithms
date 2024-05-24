import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class EdmondsKarp {
    // The capacity graph
    public static int[][] graph;
    // Array to store the path found by BFS
    public static int[] parents;

    // Method to find the maximum flow in a given graph
    public static int findMaxFlow(int[][] targetGraph) {
        graph = targetGraph;
        parents = new int[graph.length];
        Arrays.fill(parents, -1); // Initialize parents array

        int maxFlow = 0; // Initialize max flow to 0

        // Loop until there are no more augmenting paths from source to sink
        while (bfs()) {
            // Find the maximum flow through the path found by BFS
            int node = graph.length - 1; // Start from the sink node
            int flow = Integer.MAX_VALUE;

            // Traverse the path backwards from sink to source
            while (node != 0) {
                // Find the minimum capacity in the path
                flow = Math.min(flow, graph[parents[node]][node]);
                node = parents[node]; // Move to the parent node
            }

            // Add the found flow to the overall max flow
            maxFlow += flow;

            // Update the capacities in the residual graph
            node = graph.length - 1;
            while (node != 0) {
                // Decrease the capacity of the forward edge
                graph[parents[node]][node] -= flow;
                // Increase the capacity of the reverse edge
                graph[node][parents[node]] += flow;
                node = parents[node]; // Move to the parent node
            }
        }

        // Return the maximum flow found
        return maxFlow;
    }

    // Helper method to perform BFS and find an augmenting path
    private static boolean bfs() {
        boolean[] visited = new boolean[graph.length]; // Array to mark visited nodes
        Arrays.fill(parents, -1); // Reset parents array

        Deque<Integer> queue = new ArrayDeque<>(); // Queue for BFS
        queue.offer(0); // Start BFS from the source node
        visited[0] = true; // Mark the source node as visited

        // BFS loop
        while (!queue.isEmpty()) {
            Integer node = queue.poll(); // Dequeue a node
            // Traverse all adjacent nodes
            for (int child = 0; child < graph.length; child++) {
                // Check if the child node is not visited and there is available capacity
                if (graph[node][child] > 0 && !visited[child]) {
                    visited[child] = true; // Mark the child node as visited
                    parents[child] = node; // Set the parent of the child node
                    queue.offer(child); // Enqueue the child node
                }
            }
        }

        // Return true if the sink node was reached
        return visited[visited.length - 1];
    }
}
