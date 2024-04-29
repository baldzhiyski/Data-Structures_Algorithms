import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BinarySearch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Integer> numbers = Arrays.stream(scanner.nextLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int element = scanner.nextInt();

        Collections.sort(numbers);

        binarySearch(numbers,element);
    }

    private static void binarySearch(List<Integer> numbers, int element) {
        int start = 0;
        int end = numbers.size() - 1;

        while (start<=end){
            int mid = (start+end) / 2;

            Integer curr = numbers.get(mid);
            if(element< curr){
                end = mid-1;
            }else if(element> curr){
                start= mid + 1;
            }else{
                System.out.println("Element found at " + mid + " index!");
                return;
            }
        }
        System.out.println(-1);
    }
}
