import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FindBiConnectedComponents {
    public static int[][] graph;
    public static int[] depths;
    public static int[] lowPoints;
    public static int[] parents;
    public static int[] reachableCount;
    public static List<List<Integer>> subGraphs = new ArrayList<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nodes = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int edges = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        graph = new int[nodes][nodes];
        depths = new int[nodes];
        lowPoints = new int[nodes];
        parents = new int[nodes];
        reachableCount = new int[nodes];

        Arrays.fill(parents,-1);

        for (int i = 0; i < edges; i++) {
            int[] tokens = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int source = tokens[0];
            int destination = tokens[1];

            graph[source][reachableCount[source]++] = destination;
            graph[destination][reachableCount[destination]++] = source;
        }

        findArticulationsPoints(0,0,new ArrayList<>());

        System.out.println("Number of bi-connected components: "+ subGraphs.size());
    }


    private static void findArticulationsPoints(int node, int depth,List<Integer> subSet) {
        depths[node] = depth;
        lowPoints[node] = depth;

        for (int i = 0; i < reachableCount[node]; i++) {
            int child = graph[node][i];
            if(depths[child] == 0){
                parents[child] = node;

                List<Integer> components = new ArrayList<>();
                findArticulationsPoints(child,depth + 1,components);
                if(lowPoints[child] >= depths[node] || parents[child] == -1){
                    components.add(node);
                    subGraphs.add(components);
                }else{
                    subSet.addAll(components);
                }

                lowPoints[node] = Math.min(lowPoints[node],lowPoints[child]);
            }else if (child != parents[node]){
                lowPoints[node] = Math.min(lowPoints[node],depths[child]);
            }
        }
        subSet.add(node);

    }

}
