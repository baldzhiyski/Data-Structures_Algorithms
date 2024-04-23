import java.util.Arrays;

public class RadixSort {
    // Method to sort the given integer array using Radix Sort
    public int[] radixSort(int[] arr) {
        // Find the maximum element in the array
        int max = Arrays.stream(arr).max().orElse(Integer.MAX_VALUE);

        // Perform counting sort for every digit position
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(exp, arr);
        }

        // Return the sorted array
        return arr;
    }

    // Method to perform counting sort for the given digit position
    private void countSort(int exp, int[] arr) {
        // Initialize arrays to store the sorted elements and count of occurrences
        int[] output = new int[arr.length];
        int[] countArray = new int[10]; // 10 possible digits (0 to 9)

        // Store count of occurrences of each digit in countArray[]
        for (int value : arr) {
            countArray[(value / exp) % 10]++;
        }

        // Change countArray[i] to contain the actual position of each digit in output[]
        for (int i = 1; i < 10; i++) {
            countArray[i] += countArray[i - 1];
        }

        // Build the output array by placing elements in their correct positions
        for (int i = arr.length - 1; i >= 0; i--) {
            int current = arr[i];
            int positionInArray = countArray[(current / exp) % 10] - 1;
            output[positionInArray] = current;
            countArray[(current / exp) % 10]--;
        }

        // Copy the sorted elements from output[] to arr[]
        System.arraycopy(output, 0, arr, 0, arr.length);
    }

    // Main method for testing
    public static void main(String[] args) {
        // Example usage
        int[] array = {170, 45, 75, 90};

        System.out.println("Array before sorting:");
        printArray(array);

        RadixSort radixSort = new RadixSort();
        radixSort.radixSort(array);

        System.out.println("Array after sorting:");
        printArray(array);
    }

    // Utility method to print the elements of an integer array
    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
