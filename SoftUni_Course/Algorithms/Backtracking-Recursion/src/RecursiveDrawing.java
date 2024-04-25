import java.util.Scanner;

public class RecursiveDrawing {
    public static void main(String[] args) {
        int number = new Scanner(System.in).nextInt();
        drawFig(number);
    }

    private static void drawFig(int number) {
        if(number==0){
            return;
        }
        printLineOfChars('*',number);
        drawFig(number-1);
        printLineOfChars('#',number);
    }

    private static void printLineOfChars(char symbol, int number) {
        for (int times = 0; times < number; times++) {
            System.out.print(symbol);
        }
        System.out.println();
    }
}
