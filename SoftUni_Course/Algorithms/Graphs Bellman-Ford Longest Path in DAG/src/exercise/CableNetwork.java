package exercise;

import java.util.*;
import java.util.stream.Collectors;

public class CableNetwork {
    public static Map<Integer, List<Edge>> graph = new LinkedHashMap<>();

    public  static int cost = 0;
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
            return Integer.compare(this.weight, o.weight);
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int budget = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int nodes = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int edgesCount = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        boolean[] used = new boolean[nodes];

        PriorityQueue<Edge> queue = new PriorityQueue<>();

        for (int i = 0; i < edgesCount; i++) {
            String[] tokens = scanner.nextLine().split("\\s+");

            int from = Integer.parseInt(tokens[0]);
            int to = Integer.parseInt(tokens[1]);
            int weight = Integer.parseInt(tokens[2]);

            Edge edge = new Edge(from, to, weight);

            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(edge);

            if (tokens.length == 4) {
                used[from] = used[to] = true;
            }
        }

        prim(used,budget);

        System.out.println("Budget used: " + cost);

    }

    private static boolean prim(boolean[] used, int budget) {
        // We follow prims alg and add all the children
        PriorityQueue<Edge> edges = new PriorityQueue<>(graph.values()
                .stream()
                .flatMap(List::stream)
                .filter(e-> (!used[e.from] && used[e.to]) || (used[e.from] && !used[e.to]))
                        .collect(Collectors.toList()));

        while (!edges.isEmpty()) {
            Edge minEdge = edges.poll();

            int to = minEdge.to;
            int from = minEdge.from;
            int weight = minEdge.weight;

            // Relaxation step
            int removedValue = -1;

            if (used[from] && !used[to]) {
                used[to] = true;
                removedValue = weight;

            } else if (!used[from] && used[to]) {
                used[from] = true;
                removedValue = weight;
            }

            edges = graph.values()
                    .stream()
                    .flatMap(List::stream)
                    .filter(e-> (!used[e.from] && used[e.to]) || (used[e.from] && !used[e.to]))
                    .collect(Collectors.toCollection(PriorityQueue::new));

            if(removedValue !=-1 && budget-removedValue > 0){
                budget-=removedValue;
                cost+=removedValue;
            }else if (budget-removedValue<0){
                return false;
            }
        }

        return true;
    }
}
