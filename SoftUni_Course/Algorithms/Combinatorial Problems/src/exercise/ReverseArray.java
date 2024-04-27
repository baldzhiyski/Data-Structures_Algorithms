package exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReverseArray {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] arr = reader.readLine().split("\\s+");

        printReversedArr(arr,arr.length-1);
    }

    private static void printReversedArr(String[] arr, int index) {
        if(index < 0) return;

        System.out.print(arr[index] + " ");
        printReversedArr(arr,index-1);
    }
}
