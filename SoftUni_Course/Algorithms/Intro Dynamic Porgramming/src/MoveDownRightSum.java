import java.util.*;

public class MoveDownRightSum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rows = Integer.parseInt(scanner.nextLine());
        int cols = Integer.parseInt(scanner.nextLine());

        int[][] elements = new int[rows][cols];

        creatingMatrix(elements, scanner);

        int[][] tableDP = new int[rows][cols];

        // Filling the first row of the tableDP
        tableDP[0][0] = elements[0][0];
        for (int col = 1; col < cols; col++) {
            tableDP[0][col] = tableDP[0][col - 1] + elements[0][col];
        }


        // Filling the first column of the tableDP
        for (int row = 1; row < rows; row++) {
            tableDP[row][0] = tableDP[row - 1][0] + elements[row][0];
        }

        // Filling other rows of the tableDP
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                tableDP[row][col] = Math.max(tableDP[row - 1][col] + elements[row][col],
                        tableDP[row][col - 1] + elements[row][col]);
            }
        }

        // Reconstruction of the path starting from the last
        int row = rows - 1;
        int col = cols - 1;

        List<String> path = new ArrayList<>();

        path.add(formatOutPut(row, col));

        while (row > 0 || col > 0) {

            int top =-1;
            if(row > 0){
                top = tableDP[row - 1][col];
            }

            int left = -1;
            if (col > 0) {
                left = tableDP[row][col - 1];
            }

            if (top > left) {
                row--;
            } else {
                col--;
            }
            path.add(formatOutPut(row, col));
        }

        Collections.reverse(path);
        System.out.println(String.join(" ",path));
    }

    private static String formatOutPut(int row, int col) {
        return "[" + row + ", " + col + "]";
    }

    private static void creatingMatrix(int[][] matrix, Scanner scanner) {
        for (int row = 0; row < matrix.length; row++) {
            matrix[row] = Arrays.stream(scanner.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        }
    }
}
