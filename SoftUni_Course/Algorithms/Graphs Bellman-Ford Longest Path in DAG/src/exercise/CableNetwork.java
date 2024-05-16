package exercise;

import java.util.*;
import java.util.stream.Collectors;

public class CableNetwork {
    // Graph represented as an adjacency list
    public static Map<Integer, List<Edge>> graph = new LinkedHashMap<>();
    public static int cost = 0; // Total cost of the used budget

    // Class to represent an edge in the graph
    public static class Edge implements Comparable<Edge> {
        public int from;
        public int to;
        public int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight); // Compare edges based on their weight
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read budget, nodes, and edges count
        int budget = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int nodes = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int edgesCount = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        boolean[] used = new boolean[nodes]; // Array to track which nodes are already connected

        // Priority queue to manage edges based on their weights
        PriorityQueue<Edge> queue = new PriorityQueue<>();

        // Read each edge and populate the graph
        for (int i = 0; i < edgesCount; i++) {
            String[] tokens = scanner.nextLine().split("\\s+");

            int from = Integer.parseInt(tokens[0]);
            int to = Integer.parseInt(tokens[1]);
            int weight = Integer.parseInt(tokens[2]);

            Edge edge = new Edge(from, to, weight);

            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(edge);

            // If the edge is part of the existing network, mark nodes as used
            if (tokens.length == 4) {
                used[from] = used[to] = true;
            }
        }

        // Run Prim's algorithm to extend the network within the budget
        prim(used, budget);

        // Output the total budget used
        System.out.println("Budget used: " + cost);
    }

    private static boolean prim(boolean[] used, int budget) {
        // Collect all edges that connect used and unused nodes
        PriorityQueue<Edge> edges = new PriorityQueue<>(graph.values()
                .stream()
                .flatMap(List::stream)
                .filter(e -> (!used[e.from] && used[e.to]) || (used[e.from] && !used[e.to]))
                .collect(Collectors.toList()));

        while (!edges.isEmpty()) {
            Edge minEdge = edges.poll(); // Get the edge with the smallest weight

            int to = minEdge.to;
            int from = minEdge.from;
            int weight = minEdge.weight;

            // Relaxation step
            int removedValue = -1;

            // Connect the node if it's not already connected
            if (used[from] && !used[to]) {
                used[to] = true;
                removedValue = weight;
            } else if (!used[from] && used[to]) {
                used[from] = true;
                removedValue = weight;
            }

            // Update the priority queue with the new edges
            edges = graph.values()
                    .stream()
                    .flatMap(List::stream)
                    .filter(e -> (!used[e.from] && used[e.to]) || (used[e.from] && !used[e.to]))
                    .collect(Collectors.toCollection(PriorityQueue::new));

            // Check if adding the current edge exceeds the budget
            if (removedValue != -1 && budget - removedValue >= 0) {
                budget -= removedValue; // Deduct the edge weight from the budget
                cost += removedValue; // Add the edge weight to the total cost
            } else if (budget - removedValue < 0) {
                return false; // Return false if the budget is exceeded
            }
        }

        return true; // Return true if the process completes within the budget
    }
}
