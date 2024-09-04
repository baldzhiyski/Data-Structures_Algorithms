import java.util.*;

import java.util.*;

public class Dijkstra {

    // Method to find the shortest path using Dijkstra's algorithm
    public static List<Integer> dijkstraAlgorithm(int[][] graph, int sourceNode, int destinationNode) {
        // Array to store distances from the source node to each node
        int[] distances = new int[graph.length];
        // Array to store the previous node in the shortest path from the source to each node
        int[] prev = new int[graph.length];
        // Array to keep track of visited nodes
        boolean[] visited = new boolean[graph.length];

        // Initialize distances with infinity and previous nodes with -1 (indicating no previous node)
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        // Distance from source to itself is 0
        distances[sourceNode] = 0;

        // Priority queue to store nodes ordered by their distances
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(node -> distances[node]));

        // Add source node to the queue
        queue.offer(sourceNode);

        // Dijkstra's algorithm
        while (!queue.isEmpty()) {
            // Extract the node with the smallest distance from the queue
            Integer parent = queue.poll();

            // Mark the extracted node as visited
            visited[parent] = true;

            // Get the neighbors of the extracted node
            int[] children = graph[parent];

            // Iterate through the neighbors
            for (int childNode = 0; childNode < children.length; childNode++) {
                // If there is a connection to the neighbor and it's not visited
                if (children[childNode] != 0 && !visited[childNode]) {
                    // Add the neighbor to the queue
                    queue.offer(childNode);

                    // Calculate the new distance to the neighbor through the current node
                    int newDistance = distances[parent] + graph[parent][childNode];

                    // If the new distance is shorter than the existing distance to the neighbor
                    if (newDistance < distances[childNode]) {
                        // Update the distance and previous node
                        distances[childNode] = newDistance;
                        prev[childNode] = parent;

                        // Re-insert the child node into the queue with updated distance
                        queue.offer(childNode);
                    }
                }
            }
        }

        // Reconstruct the shortest path from source to destination
        List<Integer> shortestPath = new ArrayList<>();
        shortestPath.add(destinationNode);
        int current = prev[destinationNode];

        // Traverse through the previous nodes to reconstruct the path
        while (current != -1) {
            shortestPath.add(current);
            current = prev[current];
        }

        Collections.reverse(shortestPath);

        // Return the shortest path
        return shortestPath.size()==1 ? null : shortestPath;
    }
}
