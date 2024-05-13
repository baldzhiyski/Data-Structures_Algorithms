import java.util.*;
import java.util.stream.Collectors;

public class BellmanFordAlg {
    public static int[][] graph; // Graph representation
    public static int[] distance; // Distance array to store shortest distances
    public static int[] prev; // Array to store predecessor nodes in shortest paths

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

        // Running Bellman-Ford algorithm
        try {
            bellmanFord(source);
        } catch (IllegalStateException e) {
            // Handling negative cycle detected exception
            System.out.println(e.getMessage());
            return;
        }

        // Retrieving and printing the shortest path
        List<Integer> path = new ArrayList<>();
        path.add(destination);
        int node = prev[destination];

        while (node != -1) {
            path.add(node);
            node = prev[node];
        }

        Collections.reverse(path);
        String result = path.stream().map(String::valueOf).collect(Collectors.joining(" "));

        System.out.println(result);
        System.out.println(distance[destination]); // Printing the shortest distance
    }

    // Bellman-Ford algorithm to find shortest paths from source to all other nodes
    private static void bellmanFord(int source) {
        // Initializing distance and prev arrays
        distance = new int[graph.length];
        prev = new int[graph.length];

        // Setting initial distances to infinity and predecessors to -1
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);

        distance[source] = 0; // Distance from source to itself is 0

        // Relaxing edges repeatedly
        for (int i = 1; i < graph.length - 1; i++) {
            for (int r = 1; r < graph.length; r++) {
                for (int c = 1; c < graph[r].length; c++) {
                    int weight = graph[r][c];
                    if (weight != 0) {
                        int sour = r;
                        int dest = c;
                        if (distance[sour] != Integer.MAX_VALUE) {
                            int newValue = distance[sour] + weight;
                            // Relaxing the edge if a shorter path is found
                            if (newValue < distance[dest]) {
                                distance[dest] = newValue;
                                prev[dest] = sour;
                            }
                        }
                    }
                }
            }
        }

        // Checking for negative cycles
        for (int r = 1; r < graph.length; r++) {
            for (int c = 1; c < graph[r].length; c++) {
                int weight = graph[r][c];
                if (weight != 0) {
                    int sour = r;
                    int dest = c;
                    if (distance[sour] != Integer.MAX_VALUE) {
                        int newValue = distance[sour] + weight;
                        // If a shorter path is found after the final relaxation, there's a negative cycle
                        if (newValue < distance[dest]) {
                            throw new IllegalStateException("Negative Cycle Detected");
                        }
                    }
                }
            }
        }
    }
}
