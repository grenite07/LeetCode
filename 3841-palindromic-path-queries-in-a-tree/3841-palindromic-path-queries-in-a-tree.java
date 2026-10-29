import java.util.*;

class Solution {
    int[] tin, tout, depth;
    int[][] up;
    int[] bit;
    int timer, LOG;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        up = new int[LOG][n];
        bit = new int[n + 2];
        timer = 0;

        dfs(0, -1, graph);

        char[] chars = s.toCharArray();

        for (int i = 0; i < n; i++) {
            int mask = 1 << (chars[i] - 'a');
            update(tin[i], mask);
            update(tout[i] + 1, mask);
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                if (chars[u] != c) {
                    int mask = (1 << (chars[u] - 'a'))
                             ^ (1 << (c - 'a'));

                    update(tin[u], mask);
                    update(tout[u] + 1, mask);
                    chars[u] = c;
                }
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int l = lca(u, v);
                int mask = query(tin[u])
                         ^ query(tin[v])
                         ^ (1 << (chars[l] - 'a'));

                ans.add((mask & (mask - 1)) == 0);
            }
        }

        return ans;
    }

    void dfs(int u, int parent, List<Integer>[] graph) {
        tin[u] = ++timer;
        up[0][u] = parent == -1 ? u : parent;

        for (int j = 1; j < LOG; j++) {
            up[j][u] = up[j - 1][up[j - 1][u]];
        }

        for (int v : graph[u]) {
            if (v == parent) continue;

            depth[v] = depth[u] + 1;
            dfs(v, u, graph);
        }

        tout[u] = timer;
    }

    int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++) {
            if (((diff >> j) & 1) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) return u;

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }

    void update(int i, int val) {
        for (; i < bit.length; i += i & -i) {
            bit[i] ^= val;
        }
    }

    int query(int i) {
        int result = 0;

        for (; i > 0; i -= i & -i) {
            result ^= bit[i];
        }

        return result;
    }
}