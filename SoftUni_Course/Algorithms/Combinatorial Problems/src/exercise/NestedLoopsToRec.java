package exercise;

import java.util.Scanner;

public class NestedLoopsToRec {
    public  static int[] elements ;
    public static  int num;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        num = scanner.nextInt();

        elements = new int[num];

        permute(0);

    }

    private static void permute(int index) {
        if(index == elements.length) {
            printEl(elements);
        }else{
            for (int i = 1; i <= num; i++) {
                elements[index] = i;
                permute(index+1);
            }
        }

    }

    private static void printEl(int[] elements) {
        for (int element : elements) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
}
