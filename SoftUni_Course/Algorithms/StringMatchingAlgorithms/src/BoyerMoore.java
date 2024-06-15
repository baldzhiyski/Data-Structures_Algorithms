import java.util.*;

public class BoyerMoore {

    // Method to preprocess the pattern and create the bad character table
    private static Map<Character, Integer> buildBadCharacterTable(String pattern) {
        Map<Character, Integer> badCharTable = new HashMap<>();
        int patternLength = pattern.length();

        // Initialize the table with default values (shift by pattern length)
        for (int i = 0; i < patternLength - 1; i++) {
            badCharTable.put(pattern.charAt(i), patternLength - i - 1);
        }
        return badCharTable;
    }

    // Method to preprocess the pattern and create the good suffix table
    private static int[] buildGoodSuffixTable(String pattern) {
        int patternLength = pattern.length();
        int[] goodSuffixTable = new int[patternLength];
        int[] suffixes = new int[patternLength];

        Arrays.fill(suffixes, -1);
        suffixes[patternLength - 1] = patternLength;

        // Calculate suffixes array
        for (int i = patternLength - 2, j = patternLength - 1; i >= 0; i--) {
            while (j < patternLength - 1 && pattern.charAt(i) != pattern.charAt(j)) {
                if (suffixes[j] == -1) {
                    suffixes[j] = j - i - 1;
                }
                j = patternLength - suffixes[j] - 1;
            }
            suffixes[i] = --j;
        }

        // Calculate good suffix table using the suffixes array
        Arrays.fill(goodSuffixTable, patternLength);
        for (int i = patternLength - 1; i >= 0; i--) {
            if (suffixes[i] == i + 1) {
                for (int j = 0; j < patternLength - i - 1; j++) {
                    if (goodSuffixTable[j] == patternLength) {
                        goodSuffixTable[j] = patternLength - i - 1;
                    }
                }
            }
        }
        for (int i = 0; i < patternLength - 1; i++) {
            goodSuffixTable[patternLength - suffixes[i] - 1] = patternLength - i - 1;
        }
        return goodSuffixTable;
    }

    // Method to search for the pattern in the text using Boyer-Moore algorithm
    public static List<Integer> boyerMooreSearch(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        Map<Character, Integer> badCharTable = buildBadCharacterTable(pattern);
        int[] goodSuffixTable = buildGoodSuffixTable(pattern);

        int textLength = text.length();
        int patternLength = pattern.length();
        int shift = 0;

        // Loop through the text to search for the pattern
        while (shift <= textLength - patternLength) {
            int j = patternLength - 1;

            // Compare the pattern with the current shift in the text
            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j)) {
                j--;
            }

            // If the pattern is found, add the start index to the result
            if (j < 0) {
                result.add(shift);
                shift += goodSuffixTable[0];
            } else {
                // Calculate the shift using bad character and good suffix rules
                int badCharShift = badCharTable.getOrDefault(text.charAt(shift + j), patternLength);
                int goodSuffixShift = goodSuffixTable[j];
                shift += Math.max(badCharShift, goodSuffixShift);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String text = "HERE IS A SIMPLE EXAMPLE";
        String pattern = "EXAMPLE";
        List<Integer> result = boyerMooreSearch(text, pattern);
        System.out.println("Pattern found at indices: " + result);
    }
}