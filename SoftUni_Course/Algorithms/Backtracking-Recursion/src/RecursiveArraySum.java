import java.util.Arrays;
import java.util.Scanner;

public class RecursiveArraySum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int arr[] = Arrays.stream(scanner.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        int sumOfArray = recursiveSum(arr,0);

        System.out.println(sumOfArray);
    }

    private static int recursiveSum(int[] arr, int index) {
        if(index <= arr.length -1 ){
            return arr[index] + recursiveSum(arr, index+1);
        }

        return 0;
    }
}
