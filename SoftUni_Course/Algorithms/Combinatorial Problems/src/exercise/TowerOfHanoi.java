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
        // Base case: if there's only one disk to move
        if (numberOfDisks == 1) {
            // Move the disk directly from source to destination
            destination.push(source.pop());
            System.out.println("Step #" + (steps++) + ": Moved disk");
            // Print the current state of the towers
            printState();
        } else {
            // Move the top (numberOfDisks-1) disks from source to spare,
            // using destination as the temporary storage
            solve(numberOfDisks - 1, source, spare, destination);

            // Move the largest disk from source to destination
            solve(1, source, destination, spare);

            // Move the (numberOfDisks-1) disks from spare to destination,
            // using source as the temporary storage
            solve(numberOfDisks - 1, spare, destination, source);
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
