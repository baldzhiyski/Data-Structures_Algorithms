import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Scanner;

public class LongestPathDAG {

    public static int[][] graph; // Graph representation
    public static int[] distance; // Distance array to store the longest distances
    public static boolean[] visited; // Array to track visited nodes

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Reading the number of nodes and edges
        int nodes = Integer.parseInt(scanner.nextLine());
        int edges = Integer.parseInt(scanner.nextLine());

        // Initializing the graph with nodes+1 to accommodate 1-based indexing
        graph = new int[nodes + 1][nodes + 1];

        // Reading edge information and populating the graph
        for (int i = 0; i < edges; i++) {
            int[] tokens = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int source = tokens[0];
            int destination = tokens[1];
            int weight = tokens[2];

            graph[source][destination] = weight;
        }

        // Reading the source and destination nodes
        int source = Integer.parseInt(scanner.nextLine());
        int destination = Integer.parseInt(scanner.nextLine());

        // Initializing the distance array with negative infinity
        distance = new int[graph.length];
        Arrays.fill(distance, Integer.MIN_VALUE);
        distance[source] = 0; // Distance from source to itself is 0

        // Initializing the visited array
        visited = new boolean[graph.length];

        // ArrayDeque to store nodes in topological order
        ArrayDeque<Integer> sorted = new ArrayDeque<>();

        // Performing topological sorting on each node
        for (int i = 1; i < graph.length; i++) {
            topologicalSort(i, sorted);
        }

        // Relaxing edges to find the longest path
        while (!sorted.isEmpty()) {
            Integer node = sorted.pop();

            for (int i = 1; i < graph[node].length; i++) {
                int weight = graph[node][i];
                if (weight != 0) {
                    if (distance[node] + weight > distance[i]) {
                        // Updating distance if a longer path is found
                        distance[i] = distance[node] + weight;
                    }
                }
            }
        }

        // Printing the longest distance to the destination node
        System.out.println(distance[destination]);
    }

    // Recursive method to perform topological sorting
    private static void topologicalSort(int node, ArrayDeque<Integer> sorted) {
        // If node is already visited, return
        if (visited[node]) return;

        // Mark node as visited
        visited[node] = true;

        // Recursively visit adjacent nodes
        for (int i = 1; i < graph[node].length; i++) {
            if (graph[node][i] != 0) {
                topologicalSort(i, sorted);
            }
        }

        // Push the current node onto the stack after visiting all its neighbors
        sorted.push(node);
    }
}
