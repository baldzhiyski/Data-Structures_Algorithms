import java.util.Scanner;

public class Factorial {
    public static void main(String[] args) {
        int number = new Scanner(System.in).nextInt();

        System.out.println("The factorial of " + number + " is " + getFactorialRec(number));
    }

    private static int getFactorialRec(int number) {
        if(number==0) return 1;

        return number * getFactorialRec(number-1);
    }
}
