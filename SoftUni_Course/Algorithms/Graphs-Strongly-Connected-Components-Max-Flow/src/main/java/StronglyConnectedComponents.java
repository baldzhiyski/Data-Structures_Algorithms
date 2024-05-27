import java.util.*;

public class StronglyConnectedComponents {

    private static List<List<Integer>> stronglyConnectedComponents; // List to store all SCCs
    private static List<Integer>[] reversedGraph; // Reversed graph
    private static List<Integer>[] graph; // Original graph
    private static Deque<Integer> stack = new ArrayDeque<>(); // Stack to store vertices by finish time
    private static boolean[] visited; // Visited array for DFS

    // Main method to find SCCs in the given graph
    public static List<List<Integer>> findStronglyConnectedComponents(List<Integer>[] targetGraph) {
        graph = targetGraph;
        visited = new boolean[graph.length];
        stronglyConnectedComponents = new ArrayList<>();

        // First pass: Perform DFS on the original graph and push nodes onto the stack
        for (int node = 0; node < graph.length; node++) {
            if (!visited[node]) {
                dfs(node);
            }
        }

        // Reverse the graph
        setReversedGraph();
        Arrays.fill(visited, false); // Reset visited array for second pass

        // Second pass: Process all nodes in the order defined by the stack
        while (!stack.isEmpty()) {
            Integer node = stack.pop();

            if (!visited[node]) {
                stronglyConnectedComponents.add(new ArrayList<>());
                reversedDfs(node); // Find all nodes in the current SCC
            }
        }

        return stronglyConnectedComponents;
    }

    // Helper method to perform DFS on the reversed graph
    private static void reversedDfs(Integer node) {
        if (visited[node]) {
            return;
        }

        visited[node] = true;

        // Explore all nodes reachable from the current node in the reversed graph
        for (Integer child : reversedGraph[node]) {
            reversedDfs(child);
        }

        // Add the current node to the current SCC
        stronglyConnectedComponents.get(stronglyConnectedComponents.size() - 1).add(node);
    }

    // Helper method to reverse the graph
    private static void setReversedGraph() {
        reversedGraph = new ArrayList[graph.length];

        // Initialize reversedGraph with empty lists
        for (int i = 0; i < reversedGraph.length; i++) {
            reversedGraph[i] = new ArrayList<>();
        }

        // Reverse the edges of the original graph to create the reversed graph
        for (int node = 0; node < graph.length; node++) {
            for (int child : graph[node]) {
                reversedGraph[child].add(node);
            }
        }
    }

    // Helper method to perform DFS and push nodes onto the stack
    private static void dfs(int node) {
        if (visited[node]) {
            return;
        }

        visited[node] = true;

        // Explore all nodes reachable from the current node
        for (Integer child : graph[node]) {
            dfs(child);
        }

        // Push the current node onto the stack after visiting all descendants
        stack.push(node);
    }
}
