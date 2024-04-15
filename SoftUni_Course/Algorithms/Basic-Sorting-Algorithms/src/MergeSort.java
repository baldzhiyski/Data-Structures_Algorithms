import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] numbers = {5, 2, 4, 6, 1, 3};
        System.out.println("Unsorted array:");
        printArray(numbers);
        mergeSort(numbers);
        System.out.println("Sorted array:");
        printArray(numbers);
    }

    public static void mergeSort(int[] arr) {
        if (arr.length < 2) return;

        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        mergeSort(left);
        mergeSort(right);
        merge(arr, left, right);
    }
    public static void merge(int[] array, int[] leftArray, int[] rightArray) {
        int leftIndex = 0;      // Index for tracking the current position in the left array
        int rightIndex = 0;     // Index for tracking the current position in the right array
        int mergedIndex = 0;    // Index for tracking the current position in the merged array

        // Compare elements of left and right arrays and merge them into the original array
        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            // If the current element in the left array is smaller or equal, add it to the merged array
            if (leftArray[leftIndex] <= rightArray[rightIndex]) {
                array[mergedIndex++] = leftArray[leftIndex++];
            } else {  // Otherwise, add the current element from the right array to the merged array
                array[mergedIndex++] = rightArray[rightIndex++];
            }
        }

        // Copy any remaining elements from the left array to the merged array
        while (leftIndex < leftArray.length) {
            array[mergedIndex++] = leftArray[leftIndex++];
        }

        // Copy any remaining elements from the right array to the merged array
        while (rightIndex < rightArray.length) {
            array[mergedIndex++] = rightArray[rightIndex++];
        }
    }

    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}