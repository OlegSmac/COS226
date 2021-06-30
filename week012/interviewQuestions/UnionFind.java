import edu.princeton.cs.algs4.StdOut;

public class UnionFind {
    private final int[] id;
    private final int[] sz; //sz[i] - число ячеек для i-ого корня
    private final int[] max; // max[i] - максимальный элемент для i-ого корня
    private int count;

    public UnionFind(int n) {
        this.count = n;
        id = new int[n];
        max = new int[n];
        sz = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            max[i] = i;
            sz[i] = 1;
        }
    }

    public int size(int i) {
        return sz[find(i)];
    }

    public int findMax(int i) {
        return max[find(i)];
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
            max[j] = Math.max(max[i], max[j]);
        }
        else {
            id[j] = i;
            sz[i] += sz[j];
            max[i] = Math.max(max[i], max[j]);
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
