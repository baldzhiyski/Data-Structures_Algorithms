import java.util.Scanner;

public class BinomialCoeff {
    public static  long memory[][];
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int k = scanner.nextInt();

        memory = new long[n+1][k+1];

        long binom = calcBinom(n,k);
        System.out.println(binom);
    }

    private static long calcBinom(int n, int k) {
        if(k == 0 || k == n){
            return 1;
        }

        if(memory[n][k]!=0){
            return memory[n][k];
        }

        return memory[n][k]= calcBinom(n-1,k) + calcBinom(n-1,k-1);
    }

    private static void calcBinomIterative(int n, int k) {
        for (int row = 0; row <=n; row++) {
            for (int col = 0; col <= Math.min(row,k); col++) {
                if(col == 0 || col == row){
                    memory[row][col] = 1;
                }else{
                    memory[row][col] = memory[row-1][col] + memory[row-1][col-1];
                }
            }
        }
    }
}
