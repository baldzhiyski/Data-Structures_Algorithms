import java.util.Arrays;
import java.util.Scanner;

public class   QuickSort {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[] numbers = Arrays.stream(scan.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        quickSort(numbers, 0, numbers.length - 1);
        // quick sort = moves smaller elements to left of a pivot.
        // recursively divide array in 2 partitions

        // run-time complexity - best case : (n log(n))
        // Average case 0(n log(n))
        // Worst case 0(n^2) if already sorted

        for (int num : numbers) {
            System.out.print(num + " ");
        }
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Partition the array and get the pivot index
            int pivotIndex = partition(arr, low, high);

            // Recursively sort the subarrays on both sides of the pivot
            // using the same strategy
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    public static int partition(int[] array, int low, int high) {
        // Choose the pivot (in this case, the last element)
        int pivot = array[high];

        // Index of the smaller element
        int smallerIndex = low - 1;

        // Iterate through the array from low to high-1
        for (int j = low; j < high; j++) {
            // If the current element is smaller than or equal to the pivot
            if (array[j] <= pivot) {
                // Increment the smaller element index
                smallerIndex++;

                // Swap the smaller element with the current element
                int temp = array[smallerIndex];
                array[smallerIndex] = array[j];
                array[j] = temp;
            }
        }

        // Swap the pivot element with the element at the smaller element index + 1
        // so each  item before pivot is less than it and each after higher
        int temp = array[smallerIndex + 1];
        array[smallerIndex + 1] = array[high];
        array[high] = temp;

        // Return the pivot index
        return smallerIndex + 1;
    }

}