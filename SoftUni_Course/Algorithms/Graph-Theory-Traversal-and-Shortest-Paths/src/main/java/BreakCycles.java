import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class BreakCycles {
    // Data structures to store the graph, removed edges, and output cycle
    private static Map<String, Set<String>> graph = new HashMap<>();
    private static List<String> removedEdges = new ArrayList<>();
    private static SortedSet<String> outputCycle;

    public static void main(String[] args) throws IOException {
        // Read input graph from standard input
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();

        // Parse the input graph and build the adjacency list representation
        while (line != null && !line.equals("")) {
            String[] node = line.split(" -> ");
            String[] edgeParts = node.length > 1 ? node[1].split(" ") : new String[0];
            Set<String> temp = new HashSet<>();
            // Store the edges for each node, removing duplicates
            for (String edge : edgeParts) {
                boolean success = temp.add(edge.trim());
                // If an edge already exists, add it to the list of removed edges
                if (!success) {
                    if (node[0].compareTo(edge) > 0) {
                        removedEdges.add(String.format("%s - %s", edge, node[0]));
                    }
                }
            }
            graph.put(node[0], temp);
            line = reader.readLine();
        }

        // Iterate until all cycles are broken in the graph
        while (true) {
            Set<String> visited = new HashSet<>();
            boolean noCycles = true;
            // Check for cycles starting from each node in the graph
            for (String node : graph.keySet()) {
                SortedSet<String> cycle = new TreeSet<>();
                outputCycle = new TreeSet<>();
                // Search for cycles using depth-first search (DFS)
                boolean cyclic = searchForCycles(node, visited, cycle, null);
                if (cyclic) {
                    noCycles = false;
                    // Break the cycle by removing the minimum number of edges
                    breakCycle();
                    break;
                }
            }
            // If no cycles were found in the current iteration, exit the loop
            if (noCycles) {
                break;
            }
        }

        // Print the number of removed edges and the removed edges themselves
        System.out.println("Edges to remove: " + removedEdges.size());
        // Sort the removed edges alphabetically
        removedEdges = removedEdges.stream().sorted().collect(Collectors.toList());
        for (String edge : removedEdges) {
            System.out.println(edge);
        }
    }

    // Method to break a cycle by removing edges
    private static void breakCycle() {
        // Count edges for each node in the cycle
        Map<String, Integer> vertexEdges = new HashMap<>();
        for (String node : outputCycle) {
            for (String vertex : outputCycle) {
                if (graph.get(node).contains(vertex)) {
                    vertexEdges.putIfAbsent(node, 0);
                    vertexEdges.put(node, vertexEdges.get(node) + 1);
                }
            }
        }

        // Iterate through nodes in the cycle, removing redundant edges
        while (true) {
            boolean hasChanged = false;
            for (String node : vertexEdges.keySet()) {
                if (vertexEdges.get(node) == 1 && outputCycle.contains(node)) {
                    outputCycle.remove(node);
                    hasChanged = true;
                    break;
                }
            }
            // If no more redundant edges can be removed, exit the loop
            if (!hasChanged) {
                break;
            }
        }

        // Remove the edge that closes the cycle
        for (String node : outputCycle) {
            for (String vertex : outputCycle) {
                if (graph.get(node).contains(vertex)) {
                    removedEdges.add(String.format("%s - %s", node, vertex));
                    // Remove the edge from the adjacency lists of both nodes
                    graph.get(node).remove(vertex);
                    graph.get(vertex).remove(node);
                    return;
                }
            }
        }
    }

    // Depth-first search to detect cycles in the graph
    private static boolean searchForCycles(String node, Set<String> visited, SortedSet<String> cycle, String parent) {
        boolean output = false;
        // If the current node is already in the cycle, a cycle is found
        if (cycle.contains(node)) {
            outputCycle.addAll(cycle);
            return true;
        }
        // If the current node is not visited yet, explore its neighbors
        if (!visited.contains(node)) {
            visited.add(node);
            cycle.add(node);
            // Recursively visit the neighbors of the current node
            if (graph.containsKey(node)) {
                for (String child : graph.get(node)) {
                    if (!child.equals(parent)) {
                        output = output || searchForCycles(child, visited, cycle, node);
                    }
                }
                cycle.remove(node);
            }
        }
        return output;
    }
}
