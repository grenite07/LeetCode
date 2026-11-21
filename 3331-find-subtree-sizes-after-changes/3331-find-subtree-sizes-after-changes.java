
class Solution {
    int[] last = new int[26];
    int[] newParent;
    int[] ans;
    List<Integer>[] tree;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        tree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        Arrays.fill(last, -1);

        newParent = new int[n];
        ans = new int[n];

        dfs(0, parent, s);

        List<Integer>[] updated = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            updated[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            updated[newParent[i]].add(i);
        }

        calculate(0, updated);

        return ans;
    }

    void dfs(int node, int[] parent, String s) {
        int c = s.charAt(node) - 'a';

        int previous = last[c];

        newParent[node] = previous == -1 ? parent[node] : previous;

        last[c] = node;

        for (int child : tree[node]) {
            dfs(child, parent, s);
        }

        last[c] = previous;
    }

    int calculate(int node, List<Integer>[] tree) {
        int size = 1;

        for (int child : tree[node]) {
            size += calculate(child, tree);
        }

        ans[node] = size;

        return size;
    }
}
