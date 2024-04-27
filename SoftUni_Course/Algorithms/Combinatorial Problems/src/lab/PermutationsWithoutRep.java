package lab;

public class PermutationsWithoutRep {
    public  static String[] elements = {"A","B","C"};
    public static void main(String[] args) {

        permute(0);
    }

    private static void permute(int index) {
        if(index==elements.length) {
            printPermutation();
            return;
        }
        permute(index + 1);
        for (int i = index + 1; i < elements.length; i++) {
            swap(index,i);
            permute(index + 1);
            swap(index,i);
        }


    }

    private static void swap(int first, int second) {
        String temp = elements[first];
        elements[first] = elements[second];
        elements[second] = temp;
    }

    private static void printPermutation() {
        System.out.println(String.join(" ",elements));
    }
}
