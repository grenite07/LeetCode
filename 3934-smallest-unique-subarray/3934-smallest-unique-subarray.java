import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        Integer[] sa = new Integer[n];
        int[] rank = nums.clone();

        for (int i = 0; i < n; i++) {
            sa[i] = i;
        }

        int[] temp = nums.clone();
        Arrays.sort(temp);

        int classes = 1;
        for (int i = 1; i < n; i++) {
            if (temp[i] != temp[i - 1]) {
                classes++;
            }
        }

        for (int k = 1; classes < n; k *= 2) {
            final int len = k;
            final int[] r = rank;

            Arrays.sort(sa, (a, b) -> {
                if (r[a] != r[b]) {
                    return Integer.compare(r[a], r[b]);
                }

                int ra = a + len < n ? r[a + len] : -1;
                int rb = b + len < n ? r[b + len] : -1;

                return Integer.compare(ra, rb);
            });

            int[] newRank = new int[n];
            int newClasses = 1;

            newRank[sa[0]] = 0;

            for (int i = 1; i < n; i++) {
                int a = sa[i - 1];
                int b = sa[i];

                int a1 = rank[a];
                int b1 = rank[b];

                int a2 = a + len < n ? rank[a + len] : -1;
                int b2 = b + len < n ? rank[b + len] : -1;

                if (a1 != b1 || a2 != b2) {
                    newClasses++;
                }

                newRank[b] = newClasses - 1;
            }

            rank = newRank;
            classes = newClasses;
        }

        int[] pos = new int[n];
        for (int i = 0; i < n; i++) {
            pos[sa[i]] = i;
        }

        int[] lcp = new int[n];
        int h = 0;

        for (int i = 0; i < n; i++) {
            int p = pos[i];

            if (p == 0) {
                h = 0;
                continue;
            }

            int j = sa[p - 1];

            while (i + h < n && j + h < n &&
                   nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[p] = h;

            if (h > 0) {
                h--;
            }
        }

        int ans = n;

        for (int p = 0; p < n; p++) {
            int start = sa[p];

            int maxLcp = lcp[p];

            if (p + 1 < n) {
                maxLcp = Math.max(maxLcp, lcp[p + 1]);
            }

            int required = maxLcp + 1;

            if (required <= n - start) {
                ans = Math.min(ans, required);
            }
        }

        return ans;
    }
}
