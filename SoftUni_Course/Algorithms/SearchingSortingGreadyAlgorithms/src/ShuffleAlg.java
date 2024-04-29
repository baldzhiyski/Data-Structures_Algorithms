import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class ShuffleAlg {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] numbers = Arrays.stream(scanner.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();


        Arrays.sort(numbers);

        shuffle(numbers);

        Arrays.stream(numbers).forEach(System.out::println);


    }

    private static void shuffle(int[] numbers) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = numbers.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            swap(numbers, i, j);
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
