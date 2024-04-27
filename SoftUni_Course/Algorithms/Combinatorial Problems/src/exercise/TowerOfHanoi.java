package exercise;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TowerOfHanoi {
    public static Deque<Integer> source = new ArrayDeque<>();
    public static Deque<Integer> spare = new ArrayDeque<>();
    public static Deque<Integer> destination = new ArrayDeque<>();

    public static int steps = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfDisks = scanner.nextInt();

        for (int i = numberOfDisks; i >=1; i--) {
            source.push(i);
        }

        printState();
        solve(numberOfDisks,source,destination,spare);
    }

    private static void solve(int numberOfDisks, Deque<Integer> source, Deque<Integer> destination, Deque<Integer> spare) {
        if(numberOfDisks==1){
            destination.push(source.pop());
            System.out.println("Step #" + (steps++) + ": Moved disk");
            printState();

        }else{
            solve(numberOfDisks-1,source,spare,destination);
            solve(1,source,destination,spare);
            solve(numberOfDisks-1,spare,destination,source);
        }
    }

    public static void printState(){
        System.out.printf("Source : %s%n" +
                "Destination: %s%n" +
                "Spare: %s%n%n",formatState(source),formatState(destination),formatState(spare));
    }

    private static String formatState(Deque<Integer> stack) {
        return stack
                .stream()
                .sorted(Comparator.reverseOrder())
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}
