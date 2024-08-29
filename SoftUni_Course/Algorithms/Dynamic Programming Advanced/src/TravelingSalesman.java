import java.util.Arrays;

public class TravelingSalesman {

    private final int numberOfCities;
    private final int[][] costMatrix;
    private boolean[] visitedCities;
    private int minTourCost = Integer.MAX_VALUE;
    private int[] bestTour;
    private int[] currentTour;

    public TravelingSalesman(int[][] costMatrix) {
        this.numberOfCities = costMatrix.length;
        this.costMatrix = costMatrix;
        this.visitedCities = new boolean[numberOfCities];
        this.bestTour = new int[numberOfCities + 1];
        this.currentTour = new int[numberOfCities + 1];
    }

    // Public method to solve the TSP
    public void solve() {
        visitedCities[0] = true;  // Start at the first city
        currentTour[0] = 0;       // Index 0 is the starting city
        search(1, 0);             // Start the recursive search from the second city
    }

    // Recursive method implementing the Branch and Bound strategy
    private void search(int level, int currentCost) {
        if (level == numberOfCities) {
            completeTour(currentCost);
            return;
        }

        for (int city = 1; city < numberOfCities; city++) {
            if (!visitedCities[city]) {
                visitedCities[city] = true;
                currentTour[level] = city;

                int newCost = currentCost + costMatrix[currentTour[level - 1]][city];

                if (newCost < minTourCost) {
                    search(level + 1, newCost);
                }

                visitedCities[city] = false;
            }
        }
    }

    // Method to finalize and evaluate a complete tour
    private void completeTour(int currentCost) {
        int tourCost = currentCost + costMatrix[currentTour[numberOfCities - 1]][currentTour[0]];

        if (tourCost < minTourCost) {
            minTourCost = tourCost;
            System.arraycopy(currentTour, 0, bestTour, 0, numberOfCities);
            bestTour[numberOfCities] = currentTour[0]; // Close the loop back to the start
        }
    }

    // Getters for the results
    public int getMinTourCost() {
        return minTourCost;
    }

    public int[] getBestTour() {
        return bestTour;
    }

    // Example usage
    public static void main(String[] args) {
        // Example cost matrix: Modify this for your specific problem
        int[][] costMatrix = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };

        TravelingSalesman tsp = new TravelingSalesman(costMatrix);
        tsp.solve();

        System.out.println("Minimal Tour Cost: " + tsp.getMinTourCost());
        System.out.println("Best Tour: " + Arrays.toString(tsp.getBestTour()));
    }
}
