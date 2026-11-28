import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] freq = new int[n + 1];

        long minPrice = Long.MAX_VALUE;

        for (int[] item : items) {
            freq[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        int[] div = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j += i) {
                div[i] += freq[j];
            }
            div[i]--;
        }

        long[][] a = new long[n][2];
        int m = 0;

        for (int[] item : items) {
            int d = div[item[0]];
            long p = item[1];

            if (d > 0 && p < 2 * minPrice) {
                a[m][0] = p;
                a[m][1] = d;
                m++;
            }
        }

        Arrays.sort(a, 0, m, Comparator.comparingLong(x -> x[0]));

        long[] cnt = new long[m + 1];
        long[] cost = new long[m + 1];

        long cap = (long) budget + 1;

        for (int i = 1; i <= m; i++) {
            cnt[i] = cnt[i - 1] + a[i - 1][1];

            long add = a[i - 1][0] * a[i - 1][1];

            cost[i] = Math.min(cap, cost[i - 1] + add);
        }

        long maxBonus = cnt[m];

        long low = 0;
        long high = 2L * (budget / minPrice);

        while (low < high) {
            long mid = low + (high - low + 1) / 2;

            long bonus = Math.min(maxBonus, mid / 2);
            long singles = mid - 2 * bonus;

            long bonusCost = 0;

            if (bonus > 0) {
                int l = 1, r = m;

                while (l < r) {
                    int k = l + (r - l) / 2;

                    if (cnt[k] >= bonus) {
                        r = k;
                    } else {
                        l = k + 1;
                    }
                }

                bonusCost = cost[l - 1];

                if (bonusCost <= budget) {
                    long remaining = bonus - cnt[l - 1];
                    long extra = remaining * a[l - 1][0];

                    if (extra > budget - bonusCost) {
                        bonusCost = cap;
                    } else {
                        bonusCost += extra;
                    }
                }
            }

            if (bonusCost <= budget &&
                singles <= (budget - bonusCost) / minPrice) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }

        return (int) low;
    }
}