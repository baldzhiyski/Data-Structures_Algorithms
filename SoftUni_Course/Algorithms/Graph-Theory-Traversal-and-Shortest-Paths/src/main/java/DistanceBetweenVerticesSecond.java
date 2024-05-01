import java.util.*;

public class DistanceBetweenVerticesSecond {
    public static int[][] graph;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nodes = Integer.parseInt(scanner.nextLine());
        int pairs = Integer.parseInt(scanner.nextLine());

        graph = new int[nodes + 1][];

        for (int i = 1; i <= nodes; i++) {
            String[] edges = scanner.nextLine().split(":");
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

            bfsTraversal(source, destination, prev);

            List<Integer> path = new ArrayList<>();

            int parent = prev[destination];
            while (parent != -1) {
                path.add(parent);
                parent = prev[parent];
            }

            System.out.printf("{%d, %d} -> %d%n", source, destination, path.isEmpty() ? -1 : path.size());
        }
    }

    private static void bfsTraversal(int source, int destination, int[] prev) {
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(source);
        boolean[] visited = new boolean[graph.length];
        visited[source] = true;

        while (!queue.isEmpty()) {
            Integer node = queue.poll();

            if (node == destination) return;

            for (int child : graph[node]) {
                if (!visited[child]) {
                    prev[child] = node;
                    visited[child] = true;
                    queue.offer(child);
                }
            }
        }

        prev[source] = -1;
    }
}
