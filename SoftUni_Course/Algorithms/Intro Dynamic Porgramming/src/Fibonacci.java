import java.math.BigInteger;
import java.util.Scanner;

public class Fibonacci {
    public static BigInteger[] dp;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();

        dp = new BigInteger[number + 1];
        for (int i = 0; i <= number; i++) {
            dp[i] = BigInteger.valueOf(-1); // Initialize dp array with -1
        }

        BigInteger fib = calcFib(BigInteger.valueOf(number));

        System.out.println(fib);
    }

    private static BigInteger calcFib(BigInteger number) {
        if (number.compareTo(BigInteger.valueOf(2)) <= 0) {
            return BigInteger.valueOf(1);
        }
        if (!dp[number.intValue()].equals(BigInteger.valueOf(-1))) {
            return dp[number.intValue()];
        }
        return dp[number.intValue()] = calcFib(number.subtract(BigInteger.valueOf(1)))
                .add(calcFib(number.subtract(BigInteger.valueOf(2))));
    }
}
