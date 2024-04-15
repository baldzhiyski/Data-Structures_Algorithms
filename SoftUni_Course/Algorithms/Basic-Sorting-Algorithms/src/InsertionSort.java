public class InsertionSort {
    public static void main(String[] args) {
        // Test the insertionSort method
        int[] numbers = {5, 2,9, 4, 6, 3,1};
        System.out.println("Unsorted array:");
        printArray(numbers);
        insertionSort(numbers);
        System.out.println("Sorted array:");
        printArray(numbers);
    }

    // Method to perform insertion sort
    public static void insertionSort(int[] numbers) {
        // Iterate through the array
        for (int i = 1; i < numbers.length; i++) {
            // Store the current element to be inserted
            int element = numbers[i];
            // Initialize a pointer to the current position
            int j = i;
            // Move elements of the sorted subarray that are greater than the element
            // to one position ahead of their current position
            while (j > 0 && numbers[j - 1] > element) {
                // Shift the element to the right
                numbers[j] = numbers[j - 1];
                // Move to the previous position
                j--;
            }
            // Insert the stored element at the correct position
            numbers[j] = element;
        }
    }

    // Method to print an array
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}



