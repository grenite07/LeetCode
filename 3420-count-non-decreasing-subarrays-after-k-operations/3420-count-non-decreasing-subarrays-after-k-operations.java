class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        int[] stack = new int[n];
        int top = -1;

        long[] tree = new long[4 * n];
        long[] lazy = new long[4 * n];

        int left = n - 1;
        long ans = 0;

        for (int right = n - 1; right >= 0; right--) {
            while (top >= 0 && nums[stack[top]] < nums[right]) {
                int idx = stack[top--];
                int end = top >= 0 ? stack[top] : n;

                update(tree, lazy, 1, 0, n - 1,
                       idx, end - 1, nums[right] - nums[idx]);
            }

            stack[++top] = right;

            while (query(tree, lazy, 1, 0, n - 1,
                         right, left) > k) {
                left--;
            }

            ans += left - right + 1;
        }

        return ans;
    }

    private void update(long[] tree, long[] lazy, int node,
                        int start, int end, int l, int r, long val) {
        if (l > end || r < start || l > r) return;

        if (l <= start && end <= r) {
            tree[node] += val * (end - start + 1);
            lazy[node] += val;
            return;
        }

        push(tree, lazy, node, start, end);

        int mid = (start + end) / 2;

        update(tree, lazy, node * 2, start, mid, l, r, val);
        update(tree, lazy, node * 2 + 1, mid + 1, end, l, r, val);

        tree[node] = tree[node * 2] + tree[node * 2 + 1];
    }

    private long query(long[] tree, long[] lazy, int node,
                       int start, int end, int l, int r) {
        if (l > end || r < start || l > r) return 0;

        if (l <= start && end <= r) return tree[node];

        push(tree, lazy, node, start, end);

        int mid = (start + end) / 2;

        return query(tree, lazy, node * 2, start, mid, l, r)
             + query(tree, lazy, node * 2 + 1, mid + 1, end, l, r);
    }

    private void push(long[] tree, long[] lazy, int node,
                      int start, int end) {
        if (lazy[node] == 0 || start == end) return;

        int mid = (start + end) / 2;
        long val = lazy[node];

        tree[node * 2] += val * (mid - start + 1);
        tree[node * 2 + 1] += val * (end - mid);

        lazy[node * 2] += val;
        lazy[node * 2 + 1] += val;

        lazy[node] = 0;
    }
}
