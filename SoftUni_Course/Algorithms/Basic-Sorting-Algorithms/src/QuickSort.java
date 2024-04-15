import java.util.Arrays;
import java.util.Scanner;

public class QuickSort {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[] numbers = Arrays.stream(scan.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        quickSort(numbers, 0, numbers.length - 1);

        for (int num : numbers) {
            System.out.print(num + " ");
        }
    }

    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            // Swap middle element with the last element
            int middle = (low + high) / 2;
            swap(array, middle, high);

            // Partition the array and get the pivot index
            int pivotIndex = partition(array, low, high);

            // Recursively sort the subarrays on both sides of the pivot
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
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
                swap(array, smallerIndex, j);
            }
        }

        // Swap the pivot element with the element at the smaller element index + 1
        swap(array, smallerIndex + 1, high);

        // Return the correct pivot index
        return smallerIndex + 1;
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
