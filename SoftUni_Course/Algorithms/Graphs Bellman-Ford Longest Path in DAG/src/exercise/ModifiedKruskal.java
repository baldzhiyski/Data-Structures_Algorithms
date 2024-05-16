package exercise;

import java.util.*;
import java.util.stream.Collectors;

public class ModifiedKruskal {
    public static Map<Integer, List<Edge>> graph = new LinkedHashMap<>();

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

        int nodes = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int edgesCount = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        int[] parents = new int[nodes];

        for (int i = 0; i < nodes; i++) {
            parents[i] = i;
        }

        int forestWeight = 0;

        for (int i = 0; i < edgesCount; i++) {
            String[] tokens = scanner.nextLine().split("\\s+");

            int from = Integer.parseInt(tokens[0]);
            int to = Integer.parseInt(tokens[1]);
            int weight = Integer.parseInt(tokens[2]);

            Edge edge = new Edge(from, to, weight);

            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(edge);
        }

        PriorityQueue<Edge> edges = graph.values().stream().flatMap(List::stream).collect(Collectors.toCollection(PriorityQueue::new));
        StringBuilder result = new StringBuilder();

        while (!edges.isEmpty()) {
            Edge minEdge = edges.poll();

            int firstRoot = findRoot(minEdge.from, parents);
            int secondRoot = findRoot(minEdge.to, parents);

            if (firstRoot != secondRoot) {
                result.append(String.format("(%d %d) -> %d",minEdge.from,minEdge.to,minEdge.weight))
                        .append(System.lineSeparator());
                parents[secondRoot] = firstRoot;
                forestWeight += minEdge.weight;


                for (int i = 0; i < parents.length; i++) {
                    if(parents[i] == secondRoot){
                        parents[i] = firstRoot;
                    }
                }
            }
        }

        System.out.println("Minimum spanning forest weight: " + forestWeight);
//            System.out.println(result.toString().trim());
    }

    private static int findRoot(int node, int[] parents) {
        int root = parents[node];

        while (parents[node] != root) {
            root = parents[root];
        }
        return root;
    }

}
