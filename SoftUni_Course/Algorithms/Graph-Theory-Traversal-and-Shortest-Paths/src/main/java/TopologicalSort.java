import java.util.*;

public class TopologicalSort {
    public static void main(String[] args) {

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
