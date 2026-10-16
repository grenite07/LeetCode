import java.util.HashMap;
import java.util.Map;

class Solution {
    private static final int MOD = 1_000_000_007;

    static class Node {
        long countTotal = 0;
        long sqTotal = 0;
        long lazy = 0;
        Node left, right;
    }

    private void push(int tleft, int tright, Node node) {
        if (node.left == null) node.left = new Node();
        if (node.right == null) node.right = new Node();
        
        if (node.lazy != 0) {
            long x = node.lazy % MOD;
            long l = tright - tleft + 1;
            
            long term1 = node.sqTotal;
            long term2 = (2 * x % MOD * (node.countTotal % MOD)) % MOD;
            long term3 = (l * x % MOD * x) % MOD;
            
            long newSqTotal = (term1 + term2 + term3) % MOD;
            long newCountTotal = (node.countTotal + l * x) % MOD;
            
            node.sqTotal = newSqTotal;
            node.countTotal = newCountTotal;
            
            if (tleft != tright) {
                node.left.lazy = (node.left.lazy + node.lazy) % MOD;
                node.right.lazy = (node.right.lazy + node.lazy) % MOD;
            }
            node.lazy = 0;
        }
    }

    private void update(int tleft, int tright, Node node, int ql, int qr) {
        push(tleft, tright, node);
        if (qr < tleft || tright < ql) {
            return;
        }
        if (ql <= tleft && tright <= qr) {
            node.lazy = (node.lazy + 1) % MOD;
            push(tleft, tright, node);
            return;
        }
        int mid = tleft + (tright - tleft) / 2;
        if (node.left == null) node.left = new Node();
        if (node.right == null) node.right = new Node();
        
        update(tleft, mid, node.left, ql, qr);
        update(mid + 1, tright, node.right, ql, qr);
        
        node.countTotal = (node.left.countTotal + node.right.countTotal) % MOD;
        node.sqTotal = (node.left.sqTotal + node.right.sqTotal) % MOD;
    }

    public int sumCounts(int[] nums) {
        int n = nums.length;
        Node root = new Node();
        Map<Integer, Integer> lastSeen = new HashMap<>();
        long ans = 0;

        for (int i = 0; i < n; i++) {
            int lasti = lastSeen.getOrDefault(nums[i], -1);
            update(0, n - 1, root, lasti + 1, i);
            lastSeen.put(nums[i], i);
            ans = (ans + root.sqTotal) % MOD;
        }
        return (int) ans;
    }
}