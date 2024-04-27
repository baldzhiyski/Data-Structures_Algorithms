
public class PermutationsWithoutRep {
    public  static String[] elements = {"A","B","C","D"};
    public static String[] permutations = new String[elements.length];
    public static boolean[] usedPos = new boolean[elements.length];
    public static void main(String[] args) {

        permute(0);
    }

    private static void permute(int index) {
        if(index==elements.length) {
            printPermutation();
            return;
        }
        for (int i = 0; i < elements.length; i++) {
            if(!usedPos[i]) {
                usedPos[i] = true;
                permutations[index] = elements[i];
                permute(index+1);
                usedPos[i] = false;
            }
        }


    }

    private static void printPermutation() {
        System.out.println(String.join(" ",permutations));
    }
}
