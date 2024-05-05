import java.util.Arrays;
import java.util.Scanner;

/**
 * This program calculates the minimum number of platforms required to avoid delay in any train's arrival.
 * The input consists of two sequences of numbers: train arrival times and train departure times.
 * The output is a single integer representing the minimum number of platforms needed.
 */
public class Trains {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double[] arrivalTimes = Arrays.stream(scanner.nextLine().split("\\s+")).mapToDouble(Double::parseDouble).toArray();
        double[] departureTimes = Arrays.stream(scanner.nextLine().split("\\s+")).mapToDouble(Double::parseDouble).toArray();

        Arrays.sort(arrivalTimes);
        Arrays.sort(departureTimes);

        int platforms = 0;
        int maxPlatformsCount = 0;

        for (int arrivalIndex = 0, departureIndex = 0; arrivalIndex < arrivalTimes.length;) {
            if(arrivalTimes[arrivalIndex] < departureTimes[departureIndex]){
                platforms++;
                arrivalIndex++;

                maxPlatformsCount = Math.max(platforms,maxPlatformsCount);
            }else{
                platforms--;
                departureIndex++;
            }


        }
        System.out.println(maxPlatformsCount);

    }
}
