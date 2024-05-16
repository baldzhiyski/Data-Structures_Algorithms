package exercise;

import java.util.*;

public class MostReliablePath {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nodes = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        String[] path  = scanner.nextLine().split("\\s+");

        int edges = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int source = Integer.parseInt(path[1]);
        int destination = Integer.parseInt(path[3]);

        // Using Adjacency Matrix with weights
        int[][] graph = new int[nodes][nodes];

        for (int i = 0; i < edges; i++) {
            int[] tokens = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            // Bidirectional
            graph[tokens[0]][tokens[1]] = tokens[2];
            graph[tokens[1]][tokens[0]] = tokens[2];
        }

        double[] distances = new double[nodes];
        boolean[] visited = new boolean[nodes];
        int[] prev = new int[nodes];
        Arrays.fill(prev,-1);
        distances[source] = 1.00;

        PriorityQueue<Integer> queue = new PriorityQueue<>((first,second)-> Double.compare(distances[second],distances[first]));

        queue.offer(source);

        while (!queue.isEmpty()){
            Integer minNode = queue.poll();

            visited[minNode] = true;

            for (int i = 0; i < graph[minNode].length; i++) {
                int weight = graph[minNode][i];
                if(weight !=0 && !visited[i]){

                    double newDistance= distances[minNode] * weight / 100.00;

                    if(newDistance > distances[i]){
                        distances[i] = newDistance;
                        prev[i] = minNode;
                    }
                    queue.offer(i);
                }
            }
        }
        System.out.printf("Most reliable path reliability: %.2f%%%n",distances[destination] * 100);

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(destination);

        int node = prev[destination];
        while (node!=-1){
            stack.push(node);
            node = prev[node];
        }

        while (!stack.isEmpty()){
            System.out.print(stack.pop());
            if(!stack.isEmpty()){
                System.out.print(" -> ");
            }
        }
    }
}
