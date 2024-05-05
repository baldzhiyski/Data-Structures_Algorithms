import java.util.Scanner;

/**
 * This program calculates the number of ways to climb N steps on a staircase.
 * The catch is, on each step, you can either climb one stair or two (by jumping over one).
 * The input is a single integer representing the number of steps on the stairs.
 * The output is a single integer representing the number of ways to climb the stairs.
 */
public class Stairs {
    public static long[] memory;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int stairs = Integer.parseInt(scanner.nextLine());

        if(stairs<3){
            System.out.println(stairs);
            return;
        }

        memory = new long[stairs + 1];
        memory[1] = 1;
        memory[2] = 2;

        long options = climbStairs(stairs);


        System.out.println(options);

    }

    private static long climbStairs(int stairs) {
        if(memory[stairs] !=0) return memory[stairs];

        return memory[stairs]= climbStairs(stairs-1) + climbStairs(stairs-2);
    }
}
