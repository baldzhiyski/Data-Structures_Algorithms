import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * A class representing the solution for transforming one string into another with minimal cost.
 */
public class MinimumEditDistance {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int costReplace = Integer.parseInt(scanner.nextLine());
        int costInsert = Integer.parseInt(scanner.nextLine());
        int costDelete = Integer.parseInt(scanner.nextLine());

        char[] first = scanner.nextLine().toCharArray();
        char[] second = scanner.nextLine().toCharArray();

        int[][] dp = new int[first.length + 1][second.length + 1];

        for (int i = 1; i <= second.length; i++) {
            dp[0][i] = dp[0][i - 1] + costInsert;
        }

        for (int i = 1; i <= first.length; i++) {
            dp[i][0] = dp[i - 1][0] + costDelete;
        }

        for (int i = 1; i <= first.length; i++) {
            for (int j = 1; j <= second.length; j++) {
                if (first[i - 1] == second[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int insert = dp[i][j - 1] + costInsert;
                    int replace = dp[i - 1][j - 1] + costReplace;
                    int delete = dp[i - 1][j] + costDelete;
                    dp[i][j] = Math.min(insert, Math.min(replace, delete));
                }
            }
        }


        // Reconstruct the path
        List<String> operations = new ArrayList<>();
        int i = first.length;
        int j = second.length;

        while (i > 0 && j > 0) {
            if (first[i - 1] == second[j - 1]) {
                // No operation needed (characters are the same)
                i--;
                j--;
            } else if (dp[i][j] == dp[i - 1][j - 1] + costReplace) {
                operations.add("Replace " + first[i - 1] + " with " + second[j - 1]);
                i--;
                j--;
            } else if (dp[i][j] == dp[i][j - 1] + costInsert) {
                operations.add("Insert " + second[j - 1]);
                j--;
            } else {
                operations.add("Delete " + first[i - 1]);
                i--;
            }
        }

        // Handle remaining characters
        while (i > 0) {
            operations.add("Delete " + first[i - 1]);
            i--;
        }

        while (j > 0) {
            operations.add("Insert " + second[j - 1]);
            j--;
        }

        // Print the result
        System.out.println("Minimum edit distance: " + dp[first.length][second.length]);
        System.out.println("Operations:");
        for (int k = operations.size() - 1; k >= 0; k--) {  // Reverse the list to print in the correct order
            System.out.println(operations.get(k));
        }

    }
}
