class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        long k = (long) k1 + k2;
        int n = nums1.length;
        int maxDiff = 0;
        
        // Frequency array to store counts of each absolute difference
        int[] count = new int[100005];
        
        for (int i = 0; i < n; i++) {
            int diff = Math.abs(nums1[i] - nums2[i]);
            count[diff]++;
            maxDiff = Math.max(maxDiff, diff);
        }
        
        // Greedily reduce the largest differences down to smaller ones in batches
        for (int i = maxDiff; i > 0; i--) {
            if (count[i] > 0) {
                if (k >= count[i]) {
                    k -= count[i];
                    count[i - 1] += count[i];
                    count[i] = 0;
                } else {
                    count[i] -= k;
                    count[i - 1] += k;
                    k = 0;
                    break;
                }
            }
        }
        
        // Calculate the final minimum sum of squared differences
        long result = 0;
        for (int i = 1; i <= maxDiff; i++) {
            if (count[i] > 0) {
                result += (long) count[i] * i * i;
            }
        }
        
        return result;
    }
}