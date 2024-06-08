import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class LongestCommonSubsequence {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String first = scanner.nextLine();
        String second = scanner.nextLine();

        int[][] dp = new int[first.length()+1][second.length() + 1];

        for (int rowIndex = 1; rowIndex <=first.length(); rowIndex++) {
            for (int colIndex = 1; colIndex <=second.length(); colIndex++) {
                if(first.charAt(rowIndex - 1) == second.charAt(colIndex - 1)){
                    dp[rowIndex][colIndex] = dp[rowIndex - 1][colIndex - 1] + 1;
                }else{
                    dp[rowIndex][colIndex] = Math.max(dp[rowIndex-1][colIndex] , dp[rowIndex][colIndex-1]);
                }
            }
        }
        // The number of longest common subsequence
        System.out.println(dp[first.length()][second.length()]);

        int row =first.length() - 1;
        int col = second.length() - 1;

        Deque<Character> stack = new ArrayDeque<>();
        while (row>=0 && col>=0){
            if(first.charAt(row) == second.charAt(col)){
                stack.push(first.charAt(row));
                row--;
                col--;
            }else if(row>0 && col == 0){
                row--;
            }else if(row==0 && col> 0){
                col--;
            }
            else if (dp[row-1][col] >= dp[row][col-1]){
                row--;
            }else{
                col--;
            }
        }

        while (!stack.isEmpty()){
            System.out.print(stack.pop());
        }
    }
}
