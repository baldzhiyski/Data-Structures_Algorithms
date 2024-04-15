import java.util.Arrays;
import java.util.Scanner;

public class BubbleSort {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[] numbers = Arrays.stream(scan.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        /*
        pairs of adjacent elements are compared , and the elements swapped if they
        are not in order.
        Runtime complexity : O(n^2)
        bad for big data set
         */
        bubbleSortWhile(numbers);
        for (int num : numbers) {
            System.out.print(num + " ");
        }
    }

    private static void bubbleSort(int[] numbers) {
        for (int i = 0; i < numbers.length-1; i++) {
            for (int j = 0; j < numbers.length-i-1; j++) {
                if(numbers[j]>numbers[j+1]){
                    int temp = numbers[j];
                    numbers[j]=numbers[j+1];
                    numbers[j+1]=temp;
                }
            }
        }
    }
    public static void bubbleSortWhile(int[] numbers) {
        int n = numbers.length;
        boolean swapped; // Flag to track if any swapping is done in a pass
        // Loop until no more swaps are needed
        do {
            swapped = false; // Reset the swapped flag for each pass
            // Perform one pass of bubble sort
            for (int i = 0; i < n - 1; i++) {
                // If current element is greater than the next one, swap them
                if (numbers[i] > numbers[i + 1]) {
                    int temp = numbers[i];
                    numbers[i] = numbers[i + 1];
                    numbers[i + 1] = temp;
                    swapped = true; // Set the swapped flag to true
                }
            }
            // Reduce the range for the next pass as the largest element is now at the end
            n--;
        } while (swapped); // Continue until no more swaps are done in a pass
    }
}
