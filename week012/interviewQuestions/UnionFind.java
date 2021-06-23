import edu.princeton.cs.algs4.StdOut;

public class UnionFind {
    private final int[] id;
    private final int[] sz;
    private final int[] m;
    private int count;

    public UnionFind(int n) {
        this.count = n;
        id = new int[n];
        m = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            m[i] = i;
        }

        sz = new int[n];
        for (int i = 0; i < n; i++) {
            sz[i] = 1;
        }
    }

    public int count() {
        return count;
    }

    public int findMax(int i) {
        while (i != m[i]) {
            i = m[i];
        }
        return i; // m[i] - max in connected component
    }

    public int find(int i) {
        while (i != id[i]) {
            i = id[i];
        }
        return i;
    }

    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) {
            return;
        }
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        }
        else {
            id[j] = i;
            sz[i] += sz[j];
        }
        count--;

        if (findMax(p) < findMax(q)) {
            m[findMax(p)] = m[findMax(q)];
        }
        else if (findMax(q) < findMax(p)) {
            m[findMax(q)] = m[findMax(p)];
        }
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        UnionFind UF = new UnionFind(n);
        UF.union(1, 2);
        StdOut.println("max(1) = " + UF.findMax(1));
    }
}