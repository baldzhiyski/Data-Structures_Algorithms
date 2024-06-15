import java.util.ArrayList;
import java.util.List;

public class KnuthMorrisPratt {
    /**
     * Builds the partial match table (LPS array) for the given pattern.
     *
     * @param pattern the pattern string
     * @return the LPS array
     */
    public static int[] buildPartialMatchTable(String pattern) {
        int[] lps = new int[pattern.length()]; // lps stands for "longest proper prefix which is also suffix"
        int length = 0; // length of the previous longest prefix suffix
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        // Loop to fill lps array
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                // When characters match, increment length and set lps[i]
                length++;
                lps[i] = length;
                i++;
            } else {
                // When characters do not match
                if (length != 0) {
                    // Set length to lps[length-1]
                    length = lps[length - 1];
                } else {
                    // If length is 0, set lps[i] to 0 and move to next character
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps; // Return the completed LPS array
    }

    /**
     * Searches for the pattern in the text using the KMP algorithm.
     *
     * @param text the text string
     * @param pattern the pattern string
     * @return a list of starting indices where the pattern is found in the text
     */
    public static List<Integer> KMPSearch(String text, String pattern) {
        int[] lps = buildPartialMatchTable(pattern); // Get the LPS array for the pattern
        List<Integer> result = new ArrayList<>(); // List to store the starting indices of found patterns
        int i = 0; // index for text
        int j = 0; // index for pattern

        // Loop through the text
        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                // When characters match, increment both indices
                i++;
                j++;
            }
            if (j == pattern.length()) {
                // When we reach the end of the pattern, a match is found
                result.add(i - j); // Add the start index of the match to the result list
                j = lps[j - 1]; // Reset j to the last matched position in the pattern
            } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                // When characters do not match
                if (j != 0) {
                    // Use the LPS array to skip comparisons
                    j = lps[j - 1];
                } else {
                    // If j is 0, move to the next character in the text
                    i++;
                }
            }
        }
        return result; // Return the list of starting indices
    }

    public static void main(String[] args) {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        List<Integer> result = KMPSearch(text, pattern);
        System.out.println("Pattern found at indices: " + result);
    }
}