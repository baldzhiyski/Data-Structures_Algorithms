import java.util.*;

public class Guards {
    public static   Set<Integer> notVisitedNodes;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<Integer, Set<Integer>> graph = new HashMap<>();
        notVisitedNodes = new HashSet<>();

        int nodes = Integer.parseInt(scanner.nextLine());
        int edgeCount = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < nodes; i++) {
            graph.put(i+1, new HashSet<>());
            notVisitedNodes.add(i+1);
        }

        for (int i = 0; i < edgeCount; i++) {
            String[] edges = scanner.nextLine().split(" ");
            int from= Integer.parseInt(edges[0]);
            int to = Integer.parseInt(edges[1]);

            graph.get(from).add(to);

        }
        int startNode = Integer.parseInt(scanner.nextLine());

        dfs(graph,startNode);

        for (Integer notVisitedNode : notVisitedNodes) {
            System.out.print(notVisitedNode + " ");
        }
    }

    private static void dfs(Map<Integer, Set<Integer>> graph, int currentNode) {
        if(!notVisitedNodes.contains(currentNode)){
            return;
        }

        Set<Integer> connectedNodes = graph.get(currentNode);
        notVisitedNodes.remove(currentNode);
        for (Integer connectedNode : connectedNodes) {
            dfs(graph,connectedNode);
        }
    }
}
