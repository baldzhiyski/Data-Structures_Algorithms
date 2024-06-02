import java.util.*;

public class MaximumTasksAssignment {
    public static boolean[][] graph;
    private static int source;
    private static int sink;
    public static int[] parents;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int people = Integer.parseInt(scanner.nextLine().split(" ")[1]);
        int tasks = Integer.parseInt(scanner.nextLine().split(" ")[1]);

        int nodes = people + tasks + 2;

        graph = new boolean[nodes][nodes];
        parents = new int[nodes];

        Arrays.fill(parents, -1);


        source = nodes - 2;//node before last
        sink = nodes - 1;//last node
        graph = new boolean[nodes][nodes];
        parents = new int[nodes];
        parents[source] = -1;
        //read task/person table
        for (int i = 0; i < people; ++i) {
            String line = scanner.nextLine();
            for (int j = 0; j < tasks; ++j)
                if (line.charAt(j) == 'Y')
                    graph[i][people + j] = true;
        }
        //add source to persons edges
        for (int i = 0; i < people; ++i)
            graph[source][i] = true;
        //add tasks to sink edges
        for (int i = 0; i < tasks; ++i)
            graph[people + i][sink] = true;

        //Edmonds-Karp
        while (bfs()) {
            int node = sink;
            while (node != source) {
                graph[parents[node]][node] = false;
                graph[node][parents[node]] = true;
                node = parents[node];
            }
        }

        StringBuilder result = new StringBuilder(16384);
        for (int i = 0; i < people; ++i)
            for (int j = 0; j < tasks; ++j)
                if (graph[people + j][i])//back edge from j-th task to i-th person, so there is flow there
                    result.append((char) ('A' + i)).append('-').append(j + 1).append('\n');//j -> j+1, renumber tasks

        System.out.println(result);

        scanner.close();
    }

    private static boolean bfs() {
        boolean[] visited = new boolean[graph.length];

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int child = graph[node].length - 1; child >= 0; --child)
                if (graph[node][child] && !visited[child]) {
                    visited[child] = true;
                    parents[child] = node;
                    queue.offer(child);
                }
        }

        return visited[sink];
    }
}
