import java.util.stream.IntStream;

public record ShellSort<T extends Comparable<T>>(T[] arr) {

    // Method to perform Shell Sort on a generic array
    public void sort(T[] arr) {
        // Start with a gap size of half the array length
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            // Perform insertion sort with the current gap
            for (int i = gap; i < arr.length; i++) {
                int j = i; // Initialize j to the current index i

                // Insertion sort within the current gap in ascending
                while (j >= gap && arr[j].compareTo(arr[j - gap]) < 0) {
                    // Swap elements if they are out of order
                    T temp = arr[j];
                    arr[j] = arr[j - gap];
                    arr[j - gap] = temp;
                    j -= gap; // Move j back by the gap size
                }
            }
        }
    }

    public void sortRecursively() {
        IntStream.iterate(arr.length / 2, gap -> gap > 0, gap -> gap / 2).forEach(gap ->
                IntStream.range(gap, arr.length).forEach(index -> sort(index, gap))
        );
    }

    private void sort(int index, int gap) {
        if (index >= gap && arr[index].compareTo(arr[index - gap]) < 0) {
            T temp = arr[index];
            arr[index] = arr[index - 1];
            arr[index - 1] = temp;
            sort(index - gap, gap);
        }
    }

    public static void main(String[] args) {
        // Example usage
        Integer[] array = {12, 34, 54, -10,2, 3, 9,1};

        System.out.println("Array before sorting:");
        printArray(array);

        ShellSort<Integer> shellSort = new ShellSort<>(array);
        shellSort.sort(array);


        System.out.println("Array after sorting:");
        printArray(array);
    }

    // Method to print the elements of the array
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
}