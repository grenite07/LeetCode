class Solution {
    public int findKthPositive(int[] arr, int k) {
        int missing = 0;
        int num = 1;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == num) {
                num++;
            } else {
                missing++;
                
                if (missing == k) {
                    return num;
                }
                
                num++;
                i--;
            }
        }

        // If kth missing number is after the array
        return num + (k - missing - 1);
    }
}