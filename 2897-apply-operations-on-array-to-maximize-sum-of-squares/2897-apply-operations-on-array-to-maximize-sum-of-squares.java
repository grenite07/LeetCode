import java.util.List;

class Solution {
    public int maxSum(List<Integer> nums, int k) {
        int[] bitCount = new int[32];
        
        // Step 1: Count the occurrences of each set bit across all numbers
        for (int num : nums) {
            for (int i = 0; i < 32; i++) {
                if ((num & (1 << i)) != 0) {
                    bitCount[i]++;
                }
            }
        }
        
        long mod = 1_000_000_007;
        long totalSum = 0;
        
        // Step 2: Greedily construct k largest numbers using available bits
        for (int i = 0; i < k; i++) {
            long currentNum = 0;
            for (int bit = 0; bit < 32; bit++) {
                if (bitCount[bit] > 0) {
                    currentNum |= (1L << bit);
                    bitCount[bit]--;
                }
            }
            // Add the square of the constructed number to the total sum
            totalSum = (totalSum + (currentNum * currentNum) % mod) % mod;
        }
        
        return (int) totalSum;
    }
}