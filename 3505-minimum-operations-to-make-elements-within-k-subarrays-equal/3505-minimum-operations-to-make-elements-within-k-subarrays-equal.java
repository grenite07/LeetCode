import java.util.*;

class Solution {
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] cost = new long[n - x + 1];

        PriorityQueue<Integer> left = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> right = new PriorityQueue<>();

        long sumL = 0, sumR = 0;
        int[] a = Arrays.copyOf(nums, n);

        for (int i = 0; i < x; i++) {
            left.add(a[i]);
            sumL += a[i];
        }

        while (left.size() > (x + 1) / 2) {
            int v = left.poll();
            sumL -= v;
            right.add(v);
            sumR += v;
        }

        while (!right.isEmpty() && left.peek() > right.peek()) {
            int u = left.poll();
            int v = right.poll();

            sumL += v - u;
            sumR += u - v;

            left.add(v);
            right.add(u);
        }

        cost[0] = getCost(left, sumL, sumR, x);

        // Rebuild each window using balanced heaps.
        // Lazy deletion handles elements outside the window.
        return solve(nums, x, k);
    }

    private long getCost(PriorityQueue<Integer> left,
                         long sumL, long sumR, int x) {
        long median = left.peek();
        return median * left.size() - sumL
                + sumR - median * (x - left.size());
    }

    private long solve(int[] nums, int x, int k) {
        int n = nums.length;
        int m = n - x + 1;

        long[] cost = new long[m];

        TreeMap<Integer, Integer> lo = new TreeMap<>();
        TreeMap<Integer, Integer> hi = new TreeMap<>();

        long sumLo = 0, sumHi = 0;
        int loSize = 0, hiSize = 0;

        for (int i = 0; i < n; i++) {
            int v = nums[i];

            if (lo.isEmpty() || v <= lo.lastKey()) {
                lo.merge(v, 1, Integer::sum);
                sumLo += v;
                loSize++;
            } else {
                hi.merge(v, 1, Integer::sum);
                sumHi += v;
                hiSize++;
            }

            if (i >= x) {
                int old = nums[i - x];

                if (lo.containsKey(old)) {
                    remove(lo, old);
                    sumLo -= old;
                    loSize--;
                } else {
                    remove(hi, old);
                    sumHi -= old;
                    hiSize--;
                }
            }

            while (loSize > (x + 1) / 2) {
                int val = lo.lastKey();
                remove(lo, val);
                loSize--;
                sumLo -= val;

                hi.merge(val, 1, Integer::sum);
                hiSize++;
                sumHi += val;
            }

            while (loSize < (x + 1) / 2 && !hi.isEmpty()) {
                int val = hi.firstKey();
                remove(hi, val);
                hiSize--;
                sumHi -= val;

                lo.merge(val, 1, Integer::sum);
                loSize++;
                sumLo += val;
            }

            if (i >= x - 1) {
                long median = lo.lastKey();
                cost[i - x + 1] =
                        median * loSize - sumLo
                        + sumHi - median * hiSize;
            }
        }

        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[k + 1][n + 1];

        for (long[] row : dp) {
            Arrays.fill(row, INF);
        }

        Arrays.fill(dp[0], 0);

        for (int j = 1; j <= k; j++) {
            for (int i = j * x; i <= n; i++) {
                dp[j][i] = dp[j][i - 1];

                int start = i - x;

                if (dp[j - 1][start] != INF) {
                    dp[j][i] = Math.min(
                            dp[j][i],
                            dp[j - 1][start] + cost[start]
                    );
                }
            }
        }

        return dp[k][n];
    }

    private void remove(TreeMap<Integer, Integer> map, int val) {
        int count = map.get(val);

        if (count == 1) {
            map.remove(val);
        } else {
            map.put(val, count - 1);
        }
    }
}