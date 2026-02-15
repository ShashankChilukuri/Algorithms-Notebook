import java.util.*;

class Hash {
    private Map<String, Integer> hash_table;

    public Hash() {
        hash_table = new HashMap<>();
    }

    private String key(int x, int y, int z) {
        return x + "#" + y + "#" + z;
    }

    public int hash(int x) {
        return hash(x, 0, 0);
    }

    public int hash(int x, int y) {
        return hash(x, y, 0);
    }

    public int hash(int x, int y, int z) {
        String k = key(x, y, z);
        if (hash_table.containsKey(k))
            return hash_table.get(k);

        int new_hash = hash_table.size();
        hash_table.put(k, new_hash);
        return new_hash;
    }
}

class GraphX {

    boolean is_directed;
    public ArrayList<ArrayList<Pair>> adj;
    int n;
    int N = 5000000;
    Hash h;

    class Pair {
        int node;
        int weight;

        Pair(int n, int w) {
            node = n;
            weight = w;
        }
    }

    GraphX(int n_, boolean is_directed_) {
        n = n_;
        is_directed = is_directed_;
        h = new Hash();

        adj = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<>());
        }
    }

    int hash(int u, int v) {
        return h.hash(u, v);
    }

    int hash(int u, int v, int k) {
        return h.hash(u, v, k);
    }

    void add_edge(int uR, int vR, int c) {
        int u = h.hash(uR);
        int v = h.hash(vR);
        add_edge_internal(u, v, c);
    }

    void add_edge(int uR, int vR) {
        add_edge(uR, vR, 0);
    }

    void add_edge(int u1, int u2, int v1, int v2, int c) {
        int u = h.hash(u1, u2);
        int v = h.hash(v1, v2);
        add_edge_internal(u, v, c);
    }

    void add_edge(int u1, int u2, int u3, int v1, int v2, int v3, int c) {
        int u = h.hash(u1, u2, u3);
        int v = h.hash(v1, v2, v3);
        add_edge_internal(u, v, c);
    }

    private void add_edge_internal(int u, int v, int c) {
        add_edge_weighted(u, v, c);
        if (!is_directed)
            add_edge_weighted(v, u, c);
    }

    private void add_edge_weighted(int u, int v, int c) {
        adj.get(u).add(new Pair(v, c));
    }
}

class BFS {

    ArrayList<Integer> min_dist_from_source;
    ArrayList<Boolean> visited;
    GraphX g;

    BFS(GraphX g_) {
        g = g_;
        clear();
    }

    void clear() {
        min_dist_from_source = new ArrayList<>(Collections.nCopies(g.N, -1));
        visited = new ArrayList<>(Collections.nCopies(g.N, false));
    }

    void run(int sourceR) {
        int source = g.h.hash(sourceR);
        run_internal(source);
    }

    void run(int x, int y) {
        int source = g.h.hash(x, y);
        run_internal(source);
    }

    void run(int x, int y, int z) {
        int source = g.h.hash(x, y, z);
        run_internal(source);
    }

    int min_dist(int targetR) {
        int target = g.h.hash(targetR);
        return min_dist_from_source.get(target);
    }

    int min_dist(int x, int y) {
        int target = g.h.hash(x, y);
        return min_dist_from_source.get(target);
    }

    int min_dist(int x, int y, int z) {
        int target = g.h.hash(x, y, z);
        return min_dist_from_source.get(target);
    }

    boolean is_visited(int targetR) {
        int target = g.h.hash(targetR);
        return visited.get(target);
    }

    boolean is_visited(int x, int y) {
        int target = g.h.hash(x, y);
        return visited.get(target);
    }

    boolean is_visited(int x, int y, int z) {
        int target = g.h.hash(x, y, z);
        return visited.get(target);
    }

    private void run_internal(int source) {
        Queue<Integer> q = new LinkedList<>();
        q.add(source);

        visited.set(source, true);
        min_dist_from_source.set(source, 0);

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (GraphX.Pair p : g.adj.get(cur)) {
                int adj_node = p.node;

                if (!visited.get(adj_node)) {
                    visited.set(adj_node, true);
                    min_dist_from_source.set(adj_node,
                            min_dist_from_source.get(cur) + 1);
                    q.add(adj_node);
                }
            }
        }
    }
}
