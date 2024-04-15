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
        int leftLength = leftArray.length;
        int rightLength = rightArray.length;
        int i = 0, j = 0, k = 0;

        // Compare elements of left and right arrays and merge them into original array
        while (i < leftLength && j < rightLength) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }

        // Copy remaining elements of left array, if any
        while (i < leftLength) {
            array[k++] = leftArray[i++];
        }

        // Copy remaining elements of right array, if any
        while (j < rightLength) {
            array[k++] = rightArray[j++];
        }
    }

    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}