public class VariationsWithRep {
    static String[] elements = {"A", "B", "C"};
    static int k = 2; // Variation length

    public static void main(String[] args) {
        generateVariations(new String[k], 0);
    }

    private static void generateVariations(String[] variation, int index) {
        if (index == k) {
            printVariation(variation);
            return;
        }

        for (int i = 0; i < elements.length; i++) {
            variation[index] = elements[i];
            generateVariations(variation, index + 1);
        }
    }

    private static void printVariation(String[] variation) {
        System.out.println(String.join(" ", variation));
    }
}
