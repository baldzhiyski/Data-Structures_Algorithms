import java.util.*;

public class DistanceBetweenVerticesSecond {
    public static int[][] graph;

    public static Map<Integer,Integer> indexMapper = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nodes = Integer.parseInt(scanner.nextLine());
        int pairs = Integer.parseInt(scanner.nextLine());

        graph = new int[nodes + 1][];

        for (int i = 1; i <= nodes; i++) {
            String[] edges = scanner.nextLine().split(":");

            indexMapper.put(Integer.parseInt(edges[0]),i);

            if (edges.length == 1) {
                graph[i] = new int[0];
            } else {
                graph[i] = Arrays.stream(edges[1].split("\\s+"))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }
        }

        while (pairs-- > 0) {
            int[] relations = Arrays.stream(scanner.nextLine().split("-")).mapToInt(Integer::parseInt).toArray();

            int source = relations[0];
            int destination = relations[1];

            int[] prev = new int[graph.length];
            Arrays.fill(prev, -1);

            bfsTraversal(indexMapper.get(source), indexMapper.get(destination), prev);

            List<Integer> path = new ArrayList<>();

            int parent = prev[indexMapper.get(destination)];
            while (parent != -1) {
                path.add(parent);
                parent = prev[parent];
            }

            System.out.printf("{%d, %d} -> %d%n", source, destination, path.isEmpty() ? -1 : path.size());
        }
    }

    private static void bfsTraversal(int source, int destination, int[] prev) {
        // Create a queue to store nodes to be visited
        Deque<Integer> queue = new ArrayDeque<>();

        // Enqueue the source node to start BFS traversal
        queue.offer(source);

        // Initialize a boolean array to mark visited nodes
        boolean[] visited = new boolean[graph.length + 1];

        // Mark the source node as visited
        visited[source] = true;

        // BFS traversal loop
        while (!queue.isEmpty()) {
            // Dequeue a node from the front of the queue
            Integer node = queue.poll();

            // Check if the dequeued node is the destination
            if (node == destination) return; // If destination is reached, terminate

            // Explore neighbors of the current node
            for (int i = 0; i < graph[node].length; i++) {
                // Get the index of the child node in the graph array using indexMapper
                int child = indexMapper.get(graph[node][i]);

                // If the child node is not visited
                if (!visited[child]) {
                    // Record the parent node of the child
                    prev[child] = node;

                    // Mark the child node as visited
                    visited[child] = true;

                    // Enqueue the child node for further exploration
                    queue.offer(child);
                }
            }
        }

        // If destination is not reachable from the source, mark source node with -1 in the prev array
        prev[source] = -1;
    }
}
