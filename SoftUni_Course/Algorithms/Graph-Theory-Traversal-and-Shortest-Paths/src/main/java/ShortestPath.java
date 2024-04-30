import java.util.*;

public class ShortestPath {
    // Global variables to track visited nodes and previous nodes in the shortest path
    public static boolean[] visited;
    public static int[] prevNodes;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the number of nodes in the graph
        int n = Integer.parseInt(scanner.nextLine());

        // Initialize the adjacency list representing the graph
        List<List<Integer>> graph = new ArrayList<>();

        // We leave index 0 with an empty list, so we can start indexing from 1
        for (int i = 0; i < n + 1; i++) {
            graph.add(new ArrayList<>());
        }

        // Read the number of edges in the graph
        int edges = Integer.parseInt(scanner.nextLine());

        // Construct the graph by adding edges
        for (int i = 0; i < edges; i++) {
            int[] paths = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            // Add directed edges to the adjacency list
            graph.get(paths[0]).add(paths[1]);
        }

        // Read the source and destination nodes for the shortest path
        int source = Integer.parseInt(scanner.nextLine());
        int destination = Integer.parseInt(scanner.nextLine());

        // Initialize arrays to track visited nodes and previous nodes
        visited = new boolean[n+1];
        prevNodes = new int[n+1];

        // Initialize prevNodes array with -1 (indicating no previous node)
        Arrays.fill(prevNodes,-1);

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

        // Print the shortest path length and the nodes in the shortest path
        System.out.println("Shortest path length is: " + (path.size()-1));
        for (int i = path.size()-1; i >= 0; i--) {
            System.out.print(path.get(i) + " ");
        }
    }

    // Breadth-first search (BFS) to find the shortest path
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

