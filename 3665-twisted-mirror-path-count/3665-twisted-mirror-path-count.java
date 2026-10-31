
class Solution {
    public int uniquePaths(int[][] grid) {
        int MOD = 1_000_000_007;
        int m = grid.length;
        int n = grid[0].length;

        long[] dp = new long[n];
        dp[0] = 1;

        for (int i = 0; i < m; i++) {
            long left = 0;

            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    long temp = dp[j];
                    dp[j] = left;
                    left = temp;
                } else {
                    long total = (dp[j] + left) % MOD;
                    dp[j] = total;
                    left = total;
                }
            }
        }

        return (int) dp[n - 1];
    }
}
