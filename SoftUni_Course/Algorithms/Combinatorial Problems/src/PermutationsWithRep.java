import java.util.HashSet;

public class PermutationsWithRep {
    public  static String[] elements = {"A","B","B"};
    public static void main(String[] args) {
        permute(0);
    }
    private static void permute(int index) {
        if(index==elements.length) {
            printPermutation();
            return;
        }
        permute(index + 1);
        HashSet<String> swapped = new HashSet<>();
        swapped.add(elements[index]);
        for (int i = index + 1; i < elements.length; i++) {
            if(!swapped.contains(elements[i])){
                swap(index,i);
                permute(index + 1);
                swap(index,i);
                swapped.add(elements[i]);
            }

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

