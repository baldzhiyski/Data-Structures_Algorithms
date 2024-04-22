import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class WordCruncher {
    private static List<String> pool; // List to hold input strings
    private static String target; // Target string to be formed
    private static List<String> buffer = new ArrayList<>(); // Buffer to store current combination
    private static Set<String> result = new HashSet<>(); // Set to store unique combinations
    private static StringBuilder aggregator = new StringBuilder(); // StringBuilder to aggregate output
    private static Map<Integer, ArrayList<String>> tree = new HashMap<>(); // Tree to store possible matches for each index
    private static Map<String, Integer> resources = new HashMap<>(); // Map to store available resources and their counts
    private static Map<String, Integer> counter = new HashMap<>(); // Map to keep track of resource usage

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Read input strings and split them into a list
        pool = Arrays.stream(reader.readLine().split(", "))
                .collect(Collectors.toList());

        // Read the target string
        target = reader.readLine();

        // Initialize resources and counter maps
        ArrayList<String> toRemove = new ArrayList<>();
        for (String str : pool) {
            if (!target.contains(str)) {
                toRemove.add(str);
            } else if (!resources.containsKey(str)) {
                resources.put(str, 1);
                counter.put(str, 0);
            } else {
                resources.put(str, resources.get(str) + 1);
            }
        }
        pool.removeAll(toRemove);

        // Build the tree structure with possible matches for each index in the target string
        for (int i = 0; i < target.length(); i++) {
            tree.put(i, new ArrayList<>());
        }

        for (int i = 0; i < pool.size(); i++) {
            String element = pool.get(i);
            int index = target.indexOf(element);
            while (index != -1) {
                tree.get(index).add(element);
                index = target.indexOf(element, index + 1);
            }
        }

        // Traverse the tree to find all possible combinations
        treeTraverse(0);

        // Print the found combinations
        System.out.print(aggregator.toString());
    }

    // Recursive function to traverse the tree and find combinations
    private static void treeTraverse(int index) {
        if (index == target.length()) {
            printCombination();
        } else {
            for (int i = 0; i < tree.get(index).size(); i++) {
                String element = tree.get(index).get(i);
                if (resources.get(element) > counter.get(element)) {
                    counter.put(element, counter.get(element) + 1);
                    buffer.add(element);
                    treeTraverse(index + element.length());
                    buffer.remove(buffer.lastIndexOf(element));
                    counter.put(element, counter.get(element) - 1);
                }
            }
        }
    }

    // Function to print a valid combination
    private static void printCombination() {
        String text = String.join("", buffer);
        if (text.equals(target)) {
            String output = String.join(" ", buffer);
            if (result.add(output)) {
                aggregator.append(output).append(System.lineSeparator());
            }
        }
    }
}
