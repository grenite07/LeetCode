import java.util.Arrays;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        long[] squares = new long[n];
        
        // Step 1: Compute the square of each element
        for (int i = 0; i < n; i++) {
            squares[i] = (long) nums[i] * nums[i];
        }
        
        // Step 2: Sort the squares in ascending order
        Arrays.sort(squares);
        
        long score = 0;
        int m = n / 2;
        
        // Step 3: The first m smallest squares go to minus slots
        for (int i = 0; i < m; i++) {
            score -= squares[i];
        }
        
        // Step 4: The remaining squares go to plus slots
        for (int i = m; i < n; i++) {
            score += squares[i];
        }
        
        return score;
    }
}