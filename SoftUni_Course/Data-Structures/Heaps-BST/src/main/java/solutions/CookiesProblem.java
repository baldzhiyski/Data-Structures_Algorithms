package solutions;

import java.util.PriorityQueue;
import java.util.Queue;

public class CookiesProblem {
    public Integer solve(int requiredSweetness, int[] cookiesSweetness) {
        Queue<Integer> cookies = new PriorityQueue<>();

        for (int sweetness : cookiesSweetness) {
            cookies.add(sweetness);
        }
        int currentMinSweetness = cookies.peek();
        int steps = 0;
        while (currentMinSweetness<requiredSweetness && cookies.size()>1){
            int leastSweetness = cookies.poll();
            int secondLeastSweetness = cookies.poll();

            int combined = leastSweetness + 2*secondLeastSweetness;
            cookies.add(combined);

            currentMinSweetness = cookies.peek();
            steps++;
        }


        return currentMinSweetness>requiredSweetness ? steps : -1;
    }
}
