import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LongestIncreasingSubsequence {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input sequence
        int[] sequence = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // Length of longest increasing subsequence ending at each index
        int[] lisLength = new int[sequence.length];

        // Previous index of the longest increasing subsequence
        int[] prevIndex = new int[sequence.length];
        Arrays.fill(prevIndex, -1);

        // Initialize variables to track the maximum length and its corresponding index
        int maxLength = 0, maxLengthIndex = -1;

        // Calculate the length of the longest increasing subsequence
        for (int i = 0; i < sequence.length; i++) {
            int currentNumber = sequence[i];

            int currentLength = 1;
            int prevIndexForCurrent = -1;

            // Find the longest increasing subsequence ending at current index
            for (int j = i - 1; j >= 0; j--) {
                if (sequence[j] < currentNumber && lisLength[j] + 1 >= currentLength) {
                    currentLength = lisLength[j] + 1;
                    prevIndexForCurrent = j;
                }
            }

            // Update the length and previous index arrays
            prevIndex[i] = prevIndexForCurrent;
            lisLength[i] = currentLength;

            // Update maximum length and its corresponding index
            if (maxLength < currentLength) {
                maxLength = currentLength;
                maxLengthIndex = i;
            }
        }

        // Reconstruct the longest increasing subsequence
        List<Integer> longestIncreasingSubsequence = new ArrayList<>();
        int currentIndex = maxLengthIndex;
        while (currentIndex != -1) {
            longestIncreasingSubsequence.add(sequence[currentIndex]);
            currentIndex = prevIndex[currentIndex];
        }

        // Print the longest increasing subsequence in reverse order
        for (int i = longestIncreasingSubsequence.size() - 1; i >= 0; i--) {
            System.out.print(longestIncreasingSubsequence.get(i) + " ");
        }
    }
}