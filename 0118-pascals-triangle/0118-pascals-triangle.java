import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        
        for (int n = 0; n < numRows; n++) {
            List<Integer> row = new ArrayList<>();
            row.add(1); // The first element is always 1
            long val = 1; // Use long to prevent overflow for larger rows
            
            // Compute subsequent elements using the binomial coefficient relation
            for (int k = 1; k <= n; k++) {
                val = val * (n - k + 1) / k;
                row.add((int) val);
            }
            
            res.add(row);
        }
        
        return res;
    }
}