class Solution {
    public String maxSumOfSquares(int num, int sum) {
        // If the required sum exceeds the maximum possible sum for 'num' digits or is less than 1
        if (sum > 9 * (long) num || sum < 1) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        
        int nines = sum / 9;
        int remainder = sum % 9;
        
        // Append as many 9s as possible
        for (int i = 0; i < nines; i++) {
            sb.append('9');
        }
        
        // Append the remainder if it's non-zero
        if (remainder > 0) {
            sb.append(remainder);
        }
        
        // Pad the rest of the digits with 0 to reach length 'num'
        while (sb.length() < num) {
            sb.append('0');
        }
        
        return sb.toString();
    }
}