import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Salaries {

    public static List<List<Integer>> graph = new ArrayList<>(); // Adjacency list representing the graph

    public static long[] salaries; // Array to store the salaries of employees
    public static boolean[] visited; // Array to mark visited nodes during DFS traversal

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int employees = Integer.parseInt(scanner.nextLine()); // Read the number of employees

        salaries = new long[employees]; // Initialize the array to store salaries
        visited = new boolean[employees]; // Initialize the array to mark visited nodes

        int[] managersCount = new int[employees]; // Array to count how many employees each employee manages

        // Build the graph and count managers for each employee
        for (int i = 0; i < employees; i++) {
            graph.add(new ArrayList<>()); // Initialize the adjacency list for each employee
            String line = scanner.nextLine(); // Read the manager information for the current employee

            // Process each character in the line to determine the managed employees
            for (int emp = 0; emp < line.length(); emp++) {
                char letter = line.charAt(emp);
                if (letter == 'Y') {
                    managersCount[emp]++; // Increment the count of managed employees
                    graph.get(i).add(emp); // Add the managed employee to the graph
                }
            }
        }

        List<Integer> sources = new ArrayList<>();

        // Find employees who don't manage anyone (sources in the graph)
        for (int i = 0; i < managersCount.length; i++) {
            if (managersCount[i] == 0) {
                sources.add(i);
            }
        }

        // Perform DFS traversal from each source node
        for (Integer source : sources) {
            dfs(source);
        }

        // Calculate the total sum of salaries
        long sum = Arrays.stream(salaries).sum();

        // Print the total sum of salaries
        System.out.println(sum);
    }

    // Depth-First Search (DFS) traversal
    private static void dfs(int node) {
        if (visited[node]) {
            return; // If the node is already visited, return
        }

        visited[node] = true; // Mark the current node as visited

        // Traverse the children of the current node
        for (Integer child : graph.get(node)) {
            if (!visited[child]) {
                dfs(child); // Recursively traverse unvisited children

                // Calculate the salary of the current node based on the salaries of its children
                long sum = graph.get(child).stream().mapToLong(c -> salaries[c]).sum();
                salaries[child] = sum == 0 ? 1 : sum; // Set the salary of the current node
            }
        }

        // Calculate the salary of the current node based on the salaries of its children
        long sum = graph.get(node).stream().mapToLong(c -> salaries[c]).sum();
        salaries[node] = sum == 0 ? 1 : sum; // Set the salary of the current node
    }
}
