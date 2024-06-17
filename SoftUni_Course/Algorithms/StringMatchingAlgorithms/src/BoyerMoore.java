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
        int[] suffixes = new int[patternLength + 1];

        Arrays.fill(goodSuffixTable, patternLength);
        Arrays.fill(suffixes, -1);

        // Calculate suffixes array
        int f = 0, g;
        suffixes[patternLength - 1] = patternLength;
        g = patternLength - 1;
        for (int i = patternLength - 2; i >= 0; --i) {
            if (i > g && suffixes[i + patternLength - 1 - f] < i - g) {
                suffixes[i] = suffixes[i + patternLength - 1 - f];
            } else {
                if (i < g) {
                    g = i;
                }
                f = i;
                while (g >= 0 && pattern.charAt(g) == pattern.charAt(g + patternLength - 1 - f)) {
                    --g;
                }
                suffixes[i] = f - g;
            }
        }

        // Calculate good suffix table using the suffixes array
        for (int i = 0; i < patternLength; i++) {
            goodSuffixTable[i] = patternLength;
        }
        int j = 0;
        for (int i = patternLength - 1; i >= -1; i--) {
            if (i == -1 || suffixes[i] == i + 1) {
                while (j < patternLength - 1 - i) {
                    if (goodSuffixTable[j] == patternLength) {
                        goodSuffixTable[j] = patternLength - 1 - i;
                    }
                    j++;
                }
            }
        }
        for (int i = 0; i <= patternLength - 2; i++) {
            goodSuffixTable[patternLength - 1 - suffixes[i]] = patternLength - 1 - i;
        }

        return goodSuffixTable;
    }

    // Method to search for the pattern in the text using Boyer-Moore algorithm
    public static List<Integer> boyerMooreSearch(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        if (pattern.isEmpty() || text.isEmpty()) {
            return result;
        }

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