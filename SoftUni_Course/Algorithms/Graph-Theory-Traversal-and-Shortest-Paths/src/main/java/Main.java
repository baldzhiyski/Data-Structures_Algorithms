import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine());

        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String nextLine = scanner.nextLine();

            if(nextLine.trim().isEmpty()){
                graph.add(new ArrayList<>());
                continue;
            }

            List<Integer> nextNodes = Arrays.stream(nextLine.split("\\s+"))
                    .map(Integer::parseInt).collect(Collectors.toList());

            graph.add(nextNodes);
        }
        List<Deque<Integer>> components = getConnectedComponents(graph);
        printComponents(components);
    }

    private static void printComponents(List<Deque<Integer>> components) {
        for (Deque<Integer> component : components) {
            System.out.println("Conected component: ");
            for (Integer integer : component) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }
    }

    public static List<Deque<Integer>> getConnectedComponents(List<List<Integer>> graph) {
        boolean[] visited = new boolean[graph.size()];
        List<Deque<Integer>> components = new ArrayList<>();

        for (int i = 0; i < graph.size(); i++) {
            if(!visited[i]){
                components.add(new ArrayDeque<>());
                dfs(i,components,graph,visited);
            }
        }



        return components;
    }

    private static void dfs(int node, List<Deque<Integer>> components, List<List<Integer>> graph, boolean[] visited) {
        if(!visited[node]){
            visited[node] = true;

            for (Integer child : graph.get(node)) {
                dfs(child,components,graph,visited);
            }
            components.get(components.size()-1).offer(node);
        }
    }

    private static void bfs(int start, List<Deque<Integer>> components, List<List<Integer>> graph, boolean[] visited) {
        Deque<Integer> queue = new ArrayDeque<>();
        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();

            components.get(components.size() - 1).offer(node);

            for (int child : graph.get(node)) {
                if (!visited[child]) {
                    visited[child] = true;
                    queue.offer(child);
                }
            }
        }
    }
    public static Collection<String> topSort(Map<String, List<String>> graph) {
        throw new AssertionError("Not Implemented");
    }
}
