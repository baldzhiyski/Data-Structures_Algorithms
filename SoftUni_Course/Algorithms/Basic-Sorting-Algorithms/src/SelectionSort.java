import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectionSort {

    public static void main(String[] args) {
        // Test the selectionSort method
        List<Integer> numbers = new ArrayList<>(List.of(-10,50,23,45,-5,-1,0,21,2,3,8,15,-64));
        selectionSort(numbers);
        System.out.println("Sorted list: " + numbers);
    }

    public static void selectionSort(List<Integer> numbers) {
        int endIndex = numbers.size() - 1;
        while (endIndex > 0) {
            int maxNumberIndex = findMaxNumber(numbers, endIndex);
            Collections.swap(numbers, endIndex, maxNumberIndex);
            endIndex--;
        }
    }

    private static int findMaxNumber(List<Integer> numbers, int endIndex) {
        int number = Integer.MIN_VALUE; // Initialize to the smallest possible integer
        int index = 0;
        for (int i = 0; i <= endIndex; i++) { // Corrected loop condition
            if (numbers.get(i) > number) {
                number = numbers.get(i);
                index = i;
            }
        }
        return index;
    }
}
