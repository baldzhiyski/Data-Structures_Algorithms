public class VariationsWithoutRep {

    static String[] elements = {"A", "B", "C"};
    static int k = 2; // Variation length

    public static void main(String[] args) {
        generateVariations(new String[k], new boolean[elements.length], 0);
    }

    private static void generateVariations(String[] variation, boolean[] used, int index) {
        if (index == k) {
            printVariation(variation);
            return;
        }

        for (int i = 0; i < elements.length; i++) {
            if (!used[i]) {
                variation[index] = elements[i];
                used[i] = true;
                generateVariations(variation, used, index + 1);
                used[i] = false;
            }
        }
    }

    private static void printVariation(String[] variation) {
        System.out.println(String.join(" ", variation));
    }
}
