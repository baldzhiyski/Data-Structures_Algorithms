package lab;

public class CombinationsWithoutRep {
    static String[] elements = {"A", "B", "C","D"};
    static  int k = 3;

    static String[] variations = new String[k];

    public static void main(String[] args) {
        combinations(0,0);
    }

    private static void combinations(int index, int start) {
        if(index==variations.length){
            print(variations);
        }else{
            for (int i = start; i < elements.length; i++) {
                variations[index] = elements[i];

                combinations(index+1, i+1); // Allow repetition by passing the same start index ( just i)
            }
        }
    }

    private static void print(String[] arr) {
        System.out.println(String.join(" ",arr));
    }


}
