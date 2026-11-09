
import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        TreeSet<Integer> available = new TreeSet<>();

        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[1], b[1])
        );

        int[] count = new int[k];

        for (int i = 0; i < k; i++) {
            available.add(i);
        }

        for (int i = 0; i < arrival.length; i++) {

            while (!busy.isEmpty() && busy.peek()[1] <= arrival[i]) {
                available.add(busy.poll()[0]);
            }

            if (available.isEmpty()) {
                continue;
            }

            int server = i % k;

            Integer assigned = available.ceiling(server);

            if (assigned == null) {
                assigned = available.first();
            }

            available.remove(assigned);

            count[assigned]++;

            busy.offer(new int[]{
                assigned,
                arrival[i] + load[i]
            });
        }

        int max = 0;

        for (int c : count) {
            max = Math.max(max, c);
        }

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (count[i] == max) {
                result.add(i);
            }
        }

        return result;
    }
}
