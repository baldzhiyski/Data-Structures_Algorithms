import java.util.*;


public class SupplementGraphToMakeItStronglyConnected {
    public static boolean[][] graph;
    public static List<List<Integer>> components;
    public static boolean[][] reversedGraph;
    public static boolean[] visited;

    public static ArrayDeque<Integer> stack;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nodes = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int edges = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        graph = new boolean[nodes][nodes];
        reversedGraph = new boolean[nodes][nodes];
        components = new ArrayList<>();
        visited = new boolean[nodes];
        stack = new ArrayDeque<>();

        for (int i = 0; i < edges; i++) {
            int[] tokens = Arrays.stream(scanner.nextLine().split( " -> "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int source = tokens[0];
            int destination = tokens[1];

            graph[source][destination] = true;
            reversedGraph[destination][source] = true;
        }

        for (int node = 0; node < nodes; node++) {
            if (!visited[node]) {
                dfs(node);
            }
        }

        Arrays.fill(visited, false);

        while (!stack.isEmpty()) {
            Integer node = stack.pop();
            if (!visited[node]) {
                components.add(new ArrayList<>());
                reversedDfs(node);
            }
        }

        boolean[][] DAG = new boolean[components.size()][components.size()];
        for (int i = 0; i < components.size(); i++) {
            List<Integer>subGraph = components.get(i);

            for (Integer element : subGraph) {
                for (int j = 0; j < nodes; j++) {
                    if(graph[element][j] && j!= element){

                        for (int inner = 0; inner < components.size(); inner++) {
                            if(components.get(inner).contains(j) && i !=inner){
                                DAG[i][inner] = true;
                            }
                        }

                    }
                }
            }
        }

        int zeroIncomingDegree =0;

        // No incoming edges
        for (int col = 0; col < DAG.length; col++) {
            boolean hasEdge = false;
            for (int row = 0; row < DAG[col].length; row++) {
                hasEdge = DAG[row][col];
                if(hasEdge){
                    break;
                }
            }
            if(!hasEdge){
                zeroIncomingDegree++;
            }
        }



        int zeroOutgoingIncomingDegree =0;
        // No outgoing edges
        for (int row = 0; row < DAG.length; row++) {
            boolean hasEdge = false;
            for (int col = 0; col < DAG[row].length; col++) {

                hasEdge = DAG[col][col];
                if(hasEdge){
                    break;
                }
            }
            if(!hasEdge){
                zeroOutgoingIncomingDegree++;
            }
        }

        System.out.println(Math.min(zeroIncomingDegree,zeroOutgoingIncomingDegree));
    }

    private static void reversedDfs(Integer node) {
        if (!visited[node]) {
            visited[node] = true;

            components.get(components.size() - 1).add(node);

            for (int i = 0; i < reversedGraph[node].length; i++) {
                if (reversedGraph[node][i]) {
                    reversedDfs(i);
                }
            }
        }
    }

    private static void dfs(int node) {
        if (!visited[node]) {
            visited[node] = true;

            for (int i = 0; i < graph[node].length; i++) {
                if (graph[node][i]) {
                    dfs(i);
                }
            }

            stack.push(node);
        }
    }
}
