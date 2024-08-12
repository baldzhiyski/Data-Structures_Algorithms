package knapsack.iterative;

import java.util.*;

public class Knapsack {
    public static class Item  implements Comparable<Item>{

        String name;
        int weight;

        int price;

        public Item(String name, int weight, int price) {
            this.name = name;
            this.weight = weight;
            this.price = price;
        }

        @Override
        public int compareTo(Item o) {
            return this.name.compareTo(o.name);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int capacity = Integer.parseInt(scanner.nextLine());


        String line = scanner.nextLine();
        List<Item> items = new ArrayList<>();

        while (!line.equals("end")) {

            String[] tokens = line.split("\\s+");
            items.add(new Item(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));

            line = scanner.nextLine();
        }

        int[][] dp = new int[items.size() + 1][capacity + 1];
        boolean[][] takenItems = new boolean[items.size() + 1][capacity + 1];

        for (int itemRow = 1; itemRow <= items.size(); itemRow++) {
            Item item = items.get(itemRow - 1);

            for (int capacityCol = 0; capacityCol <= capacity; capacityCol++) {

                // Here we look up to the cell and see if we do not include the item what value are we going to have
                int excluded = dp[itemRow - 1][capacityCol];
                // If there is not enough capacity yet we overwrite the value of the excluded from the top
                if (capacityCol - item.weight < 0) {
                    dp[itemRow][capacityCol] = excluded;
                } else {

                    // Here we  include the item and then we take the maximum of it
                    int included = dp[itemRow - 1][capacityCol - item.weight] + item.price;

                    if(excluded> included){
                        dp[itemRow][capacityCol] = excluded;
                    }else{
                        dp[itemRow][capacityCol] = included;
                        takenItems[itemRow][capacityCol] = true;
                    }
                }
            }
        }
        int weight = capacity;

        int bestValue = dp[items.size()][capacity];
        while (dp[items.size()][weight-1] == bestValue){
            weight--;
        }

        Set<Item> result = new TreeSet<>();

        int lastItem = items.size();

        while (lastItem >= 0){
            if(takenItems[lastItem][capacity]) {
                Item item = items.get(lastItem-1);
                result.add(item);
                capacity -= item.weight;
            }
            lastItem--;
        }

        System.out.println("Total weight: " + weight);
        System.out.println("Total value: " + bestValue);

        for (Item item : result) {
            System.out.printf("%s %d\n",item.name,item.weight );
        }


    }
}
