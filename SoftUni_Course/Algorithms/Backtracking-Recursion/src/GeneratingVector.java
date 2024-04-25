import java.util.Scanner;

public class GeneratingVector {
    public static void main(String[] args) {
        int number = new Scanner(System.in).nextInt();

        Integer[] memory = new Integer[number];

        fillVector(memory,0);
    }

    private static void fillVector(Integer[] memory, int index) {
        if(index==memory.length){
            print(memory);
            return;
        }

        for (int i = 0; i <=1; i++) {
            memory[index] = i;
            fillVector(memory, index+1);
        }

    }

    private static void print(Integer[] memory) {
        for (Integer integer : memory) {
            System.out.print(integer);
        }
        System.out.println();
    }
}
