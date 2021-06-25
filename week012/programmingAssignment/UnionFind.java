import edu.princeton.cs.algs4.StdOut;

public class UnionFind {
    private final int[] id;
    private final int[] sz;
    private int count;

    public UnionFind(int n) {
        this.count = n;
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }

        sz = new int[n];
        for (int i = 0; i < n; i++) {
            sz[i] = 1;
        }
    }

    public int count() {
        return count;
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
