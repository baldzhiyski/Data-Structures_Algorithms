import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.IntStream;

public class DividingPresents {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Read input presents and convert them to an array of integers
        int[] presents = Arrays.stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        // Calculate the total sum of all presents
        int totalSum = IntStream.of(presents).sum();

        // Initialize an array to keep track of possible sums
        int[] sums = new int[totalSum + 1];

        // Initialize the array with -1, indicating sums that are not achievable
        for (int i = 1; i < sums.length; i++) {
            sums[i] = -1;
        }

        // Dynamic programming approach to find possible sums
        for (int i = 0; i < presents.length; i++) {
            for (int prevSumIndex = totalSum - presents[i]; prevSumIndex >= 0; prevSumIndex--) {
                int presentValue = presents[i];

                // If the sum is achievable and the new sum is not already set, update the sums array
                if (sums[prevSumIndex] != -1 && sums[prevSumIndex + presentValue] == -1) {
                    sums[prevSumIndex + presentValue] = i; // Store the index of the present
                }
            }
        }

        // Find the minimal difference between Alan and Bob's presents
        int half = totalSum / 2;

        // Iterate from half of total sum to 0 to find the minimal difference
        for (int i = half; i >= 0; i--) {
            if (sums[i] == -1) {
                continue; // If this sum is not achievable, skip
            }

            // Print the minimal difference between Alan and Bob's presents
            System.out.printf("Difference: %d%n", totalSum - i - i);
            System.out.printf("Alan:%d Bob:%d%n", i, totalSum - i);
            System.out.print("Alan takes:");

            // Backtrack through the sums array to print Alan's presents
            while (i != 0) {
                System.out.printf(" %d", presents[sums[i]]);
                i -= presents[sums[i]];
            }

            System.out.println();
            System.out.println("Bob takes the rest.");
        }
    }
}
