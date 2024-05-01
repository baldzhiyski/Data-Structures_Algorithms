import java.util.*;

public class DistanceBetweenVertices {
    public static boolean[] visited;
    public static int[] prevNodes;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nodes = Integer.parseInt(scanner.nextLine());
        int pairs = Integer.parseInt(scanner.nextLine());


        // Initialize the adjacency list representing the graph
        List<List<Integer>> graph = new ArrayList<>();

        // We leave index 0 with an empty list, so we can start indexing from 1
        for (int i = 0; i < nodes + 1; i++) {
            graph.add(new ArrayList<>());
        }

        // Construct the graph by adding pairs
        for (int i = 0; i < nodes; i++) {
            String[] split = scanner.nextLine().split(":");

            // Add directed pairs to the adjacency list
            if (split.length > 1){
                int from = Integer.parseInt(split[0]);
                int[] array = Arrays.stream(split[1].split("\\s+")).mapToInt(Integer::parseInt).toArray();

                for (int to : array) {
                    graph.get(from).add(to);
                }
            }
        }

        for (int i = 0; i < pairs; i++) {
            int[] data = Arrays.stream(scanner.nextLine().split("-")).mapToInt(Integer::parseInt).toArray();

            int source = data[0];
            int destination = data[1];

            // Initialize arrays to track visited nodes and previous nodes
            visited = new boolean[nodes + 1];
            prevNodes = new int[nodes + 1];

            // Initialize prevNodes array with -1 (indicating no previous node)
            Arrays.fill(prevNodes, -1);

            // Perform BFS to find the shortest path
            bfs(graph, source, destination);

            // Reconstruct the shortest path
            List<Integer> path = new ArrayList<>();
            path.add(destination);
            int prevNode = prevNodes[destination];

            // Reconstruct the shortest path by following previous nodes
            while (prevNode != -1) {
                path.add(prevNode);
                prevNode = prevNodes[prevNode];
            }

            System.out.printf("{%d, %d} -> %d%nodes", source, destination, path.size() -1 == 0 ? -1 : path.size()-1);
        }


    }

    private static void bfs(List<List<Integer>> graph, int source, int destination) {
        Deque<Integer> queue = new ArrayDeque<>();

        // Add the source node to the queue and mark it as visited
        queue.offer(source);
        visited[source] = true;

        // Perform BFS
        while (!queue.isEmpty()) {
            Integer node = queue.poll();

            // If the destination node is reached, stop BFS
            if (node == destination) {
                return;
            }

            // Explore neighbors of the current node
            for (Integer child : graph.get(node)) {
                if (!visited[child]) {
                    visited[child] = true;
                    prevNodes[child] = node; // Record the previous node
                    queue.offer(child);
                }
            }
        }
    }
}
