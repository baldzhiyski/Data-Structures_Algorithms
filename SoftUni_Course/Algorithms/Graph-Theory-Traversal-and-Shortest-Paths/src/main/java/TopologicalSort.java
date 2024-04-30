import java.util.*;

public class TopologicalSort {
    public static void main(String[] args) {

    }

    // Topological sort using recursive DFS
    public static Collection<String> topSortRec(Map<String, List<String>> graph) {
        List<String> sorted = new ArrayList<>();

        // Set to track visited nodes during DFS
        Set<String> visited = new HashSet<>();

        // Set to detect cycles during DFS
        Set<String> detectCycles = new HashSet<>();

        // Perform DFS for each node in the graph
        for (Map.Entry<String, List<String>> node : graph.entrySet()) {
            dfs(node.getKey(), graph, visited, sorted, detectCycles);
        }

        // Reverse the sorted list to get the topological order
        Collections.reverse(sorted);

        return sorted;
    }

    // Depth-first search (DFS) traversal
    private static void dfs(String key, Map<String, List<String>> graph, Set<String> visited, List<String> sorted, Set<String> detectCycles) {
        // If the current node is already in detectCycles, a cycle is detected
        if (detectCycles.contains(key)) {
            throw new IllegalArgumentException("Cycle detected");
        }

        // If the current node has not been visited yet
        if (!visited.contains(key)) {
            visited.add(key);
            detectCycles.add(key); // Add to detectCycles before exploring children

            // Explore children of the current node recursively
            for (String child : graph.getOrDefault(key, Collections.emptyList())) {
                dfs(child, graph, visited, sorted, detectCycles);
            }

            // Add the current node to the sorted list after visiting all children
            sorted.add(key);
            detectCycles.remove(key); // Remove from detectCycles after DFS completes
        }
    }

    public static Collection<String> topSort(Map<String, List<String>> graph) {
        Map<String,Integer> dependenciesCount = getDepCount(graph);

        List<String> sorted = new ArrayList<>();

        while (!graph.isEmpty()){
            String current = graph.keySet()
                    .stream()
                    .filter(k-> dependenciesCount.get(k) == 0)
                    .findFirst()
                    .orElse(null);

            if(current == null) break;


            // Decrement the count of edges
            for (String child : graph.get(current)) {
                dependenciesCount.put(child,dependenciesCount.get(child)-1);
            }

            sorted.add(current);
            graph.remove(current);
        }

        if(!graph.isEmpty()) throw new IllegalArgumentException();

        return sorted;
    }

    private static Map<String, Integer> getDepCount(Map<String, List<String>> graph) {
        Map<String,Integer> dependenciesCount = new HashMap<>();

        for (Map.Entry<String, List<String>> node : graph.entrySet()) {
            dependenciesCount.putIfAbsent(node.getKey(),0);

            for (String child : node.getValue()) {
                dependenciesCount.putIfAbsent(child,0);
                dependenciesCount.put(child,dependenciesCount.get(child) + 1);
            }

        }

        return dependenciesCount;
    }
}
