import java.util.*;

public class StronglyConnectedComponents {

    private static  List<List<Integer>> stronglyConnectedComponents;

    private static List<Integer>[] reversedGraph;
    private static List<Integer>[] graph;
    private static Deque<Integer> stack = new ArrayDeque<>();
    private static  boolean[] visited;


    public static List<List<Integer>> findStronglyConnectedComponents(List<Integer>[] targetGraph) {
        graph = targetGraph;
        visited = new boolean[graph.length];
        stronglyConnectedComponents = new ArrayList<>();

        for (int node = 0; node < graph.length; node++) {
            if(!visited[node]){
                dfs(node);
            }
        }

        setReversedGraph();
        Arrays.fill(visited,false);

        while (!stack.isEmpty()){
            Integer node = stack.pop();

            if(!visited[node]){
                stronglyConnectedComponents.add(new ArrayList<>());
                reversedDfs(node);
            }
        }

        return stronglyConnectedComponents;
    }

    private static void reversedDfs(Integer node) {
        if(visited[node]){
            return;
        }

        visited[node ] = true;

        for (Integer child : reversedGraph[node]) {
            reversedDfs(child);
        }
        stronglyConnectedComponents.get(stronglyConnectedComponents.size()-1).add(node);

    }

    private static void setReversedGraph(){
        reversedGraph = new ArrayList[graph.length];

        for (int i = 0; i < reversedGraph.length; i++) {
            reversedGraph[i] = new ArrayList<>();
        }
        // We need to reverse the graph
        for (int node = 0; node < graph.length; node++) {
            for (int child = 0; child < graph[node].size(); child++) {
                Integer parent = graph[node].get(child);
                reversedGraph[parent].add(node);
            }
        }
    }

    private static void dfs(int node) {
        if(visited[node]){
            return;
        }

        visited[node] = true;

        for (Integer child : graph[node]) {
            dfs(child);
        }

        stack.push(node);


    }
}
