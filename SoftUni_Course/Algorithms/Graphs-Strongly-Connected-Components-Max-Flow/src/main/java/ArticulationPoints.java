import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticulationPoints {
    public static List<Integer> points;
    public static  boolean[] visited;

    public static int[] parents;
    public static int[] depths;
    public static int[] lowPoints;
    public static List<Integer>[] graph;

    public static List<Integer> findArticulationPoints(List<Integer>[] targetGraph) {
        points = new ArrayList<>();
        graph = targetGraph;
        visited = new boolean[graph.length];
        parents = new int[graph.length];
        depths = new int[graph.length];
        lowPoints = new int[graph.length];

        Arrays.fill(parents,-1);

        discoverArticulationPoints(0,1);

        return points;
    }

    private static void discoverArticulationPoints(int node, int depth) {
        // Mark the current node as visited
        visited[node] = true;
        // Set the depth and low point of the current node
        depths[node] = depth;
        lowPoints[node] = depth;

        int children = 0;  // Count of children in DFS tree
        boolean isArticulationPoint = false;  // Flag to check if the current node is an articulation point

        // Traverse all adjacent nodes (children)
        for (Integer child : graph[node]) {
            if (!visited[child]) {
                // If the child is not visited, set the current node as its parent
                parents[child] = node;
                children++;  // Increment child count

                // Recur for the child
                discoverArticulationPoints(child, depth + 1);

                // Check if the subtree rooted at child has a connection back to one of the ancestors of node
                if (lowPoints[child] >= depth) {
                    isArticulationPoint = true;
                }

                // Update the low point of the current node
                lowPoints[node] = Math.min(lowPoints[node], lowPoints[child]);
            } else if (parents[node] != child) {
                // Update the low point of the current node for back edge
                lowPoints[node] = Math.min(lowPoints[node], depths[child]);
            }
        }

        // If node is the root of DFS tree and has more than one child, or if it's not root and is an articulation point
        if ((parents[node] == -1 && children > 1) || (parents[node] != -1 && isArticulationPoint)) {
            points.add(node);
        }
    }
}
