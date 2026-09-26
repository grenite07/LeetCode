class Solution {
    public int smallestDivisor(int[] nums, int threshold) {

        int left = 1;
        int right = 0;

        // Find maximum element
        for (int num : nums) {
            right = Math.max(right, num);
        }

        // Binary Search
        while (left < right) {
            int mid = left + (right - left) / 2;

            int sum = 0;

            for (int num : nums) {
                // Ceiling of num / mid
                sum += (num + mid - 1) / mid;

                // Optimization
                if (sum > threshold) {
                    break;
                }
            }

            if (sum <= threshold) {
                // mid is valid, try smaller
                right = mid;
            } else {
                // mid is too small
                left = mid + 1;
            }
        }

        return left;
    }
}