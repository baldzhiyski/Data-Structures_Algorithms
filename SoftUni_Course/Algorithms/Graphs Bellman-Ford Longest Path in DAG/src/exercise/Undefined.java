package exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Undefined {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read number of nodes and edges
        int nodes = Integer.parseInt(scanner.nextLine());
        int edgesCount = Integer.parseInt(scanner.nextLine());

        // Initialize graph as adjacency matrix
        int[][] graph = new int[nodes + 1][nodes + 1];
        List<int[]> edges = new ArrayList<>();

        // Read each edge and update the graph and edge list
        for (int i = 0; i < edgesCount; i++) {
            int[] tokens = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            graph[tokens[0]][tokens[1]] = tokens[2];
            edges.add(new int[]{tokens[0], tokens[1]});
        }

        // Initialize distances array with max value
        int[] distances = new int[nodes + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);

        // Initialize previous node array to track the path
        int[] prev = new int[nodes + 1];
        Arrays.fill(prev, -1);

        // Read source and destination nodes
        int source = Integer.parseInt(scanner.nextLine());
        distances[source] = 0; // Distance to source is 0

        int destination = Integer.parseInt(scanner.nextLine());

        boolean hasNegativeCycle = false;

        // Bellman-Ford algorithm to find the shortest paths
        for (int i = 0; i < nodes - 1; i++) {
            for (int[] edge : edges) {
                int from = edge[0];
                int to = edge[1];
                if (distances[from] != Integer.MAX_VALUE) {
                    int newDistance = distances[from] + graph[from][to];
                    if (newDistance < distances[to]) {
                        distances[to] = newDistance;
                        prev[to] = from;
                    }
                }
            }
        }

        // Check for negative-weight cycles
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            if (distances[from] != Integer.MAX_VALUE) {
                int newDistance = distances[from] + graph[from][to];
                if (newDistance < distances[to]) {
                    hasNegativeCycle = true;
                    break;
                }
            }
        }

        // Output results based on the presence of a negative cycle
        if (hasNegativeCycle) {
            System.out.println("Undefined");
        } else {
            List<Integer> path = new ArrayList<>();
            path.add(destination);

            // Reconstruct the path from destination to source
            int node = prev[destination];
            while (node != -1) {
                path.add(node);
                node = prev[node];
            }

            // Print the path in correct order
            for (int i = path.size() - 1; i >= 0; i--) {
                System.out.print(path.get(i) + " ");
            }
            System.out.println();
            // Print the shortest distance to the destination
            System.out.println(distances[destination]);
        }
    }
}
