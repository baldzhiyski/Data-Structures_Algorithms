import java.util.*;

public class TopologicalSort {
    public static void main(String[] args) {

    }

    public static Collection<String> topSortRec(Map<String, List<String>> graph) {
        List<String> sorted = new ArrayList<>();

        Set<String> visited = new HashSet<>();
        Set<String> detectCycles = new HashSet<>();

        for (Map.Entry<String, List<String>> node : graph.entrySet()) {
            dfs(node.getKey(),graph,visited,sorted,detectCycles);
        }

        Collections.reverse(sorted);

        return sorted;
    }

    private static void dfs(String key, Map<String, List<String>> graph, Set<String> visited, List<String> sorted, Set<String> detectCycles) {
        if(detectCycles.contains(key)){
            throw new IllegalArgumentException();
        }
        if (!visited.contains(key)) {
            visited.add(key);
            detectCycles.add(key); // Add to detectCycles before exploring children

            for (String child : graph.getOrDefault(key, Collections.emptyList())) {
                dfs(child, graph, visited, sorted, detectCycles);
            }

            sorted.add(key); // Add to sorted after visiting all children
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
