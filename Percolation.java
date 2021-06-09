import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static int n;
    private static int[] id;
    private static int[] sz;
    private static boolean[] op;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        id = new int[n * n + 3];
        for (int i = 1; i < id.length; i++) {
            id[i] = i;
        }

        sz = new int[n * n + 3];
        for (int i = 1; i < sz.length; i++) {
            sz[i] = 1;
        }

        op = new boolean[n * n + 3];
        for (int i = 1; i < op.length; i++) {
            op[i] = false;
        }
    }

    private int place(int row, int col) {
        return row * n + col - n;
    }

    private int find(int p) {
        while (p != id[p]) {
            p = id[p];
        }
        return p;
    }

    private void union(int p, int q) {
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
    }

    public void open(int row, int col) {
        int pl = place(row, col);
        if (pl < 0 || pl >= n * n) {
            //throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            return;
        }
        else {
            op[pl] = true;
        }

        if (row - 1 > 0 && col > 0 && op[place(row - 1, col)]) { // top
            union(place(row - 1, col), pl);
        }

        if (place(row + 1, col) < op.length && op[place(row + 1, col)]) { // low
            union(place(row + 1,col), pl);
        }

        if (row > 0 && col - 1 > 0 && op[place(row, col - 1)]) { // left
            union(place(row, col - 1), pl);
        }

        if (place(row, col + 1) < op.length && op[place(row, col + 1)]) { // right
            union(place(row, col + 1), pl);
        }
    }

    public boolean isOpen(int row, int col) {
        int pl = place(row, col);
        if (pl < 0 || pl >= n * n) {
            //throw new IllegalArgumentException();
        }
        return op[pl];
    }

    public boolean isFull(int row, int col) {
        int pl = place(row, col);
        if (pl < 0 || pl >= n * n) {
            //throw new IllegalArgumentException();
        }
        if (op[pl] && find(pl) == id[id.length - 2]) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 1; i < op.length; i++) {
            if (op[i]) {
                count++;
            }
        }
        return count;
    }

    public boolean percolates() {
        return find(id[id.length - 2]) == find(id[id.length - 1]);
    }

    public static void main(String[] main) {
        n = StdIn.readInt();
        Percolation per = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            per.open(p, q);
            if (p == 1) {
                per.union(id[id.length - 2], per.place(p, q)); // high row
            }
            if (p == n) {
                per.union(id[id.length - 1], per.place(p, q)); // low row
            }
            StdOut.println("p = " + p + " q = " + q);
            StdOut.println("place(p, q) = " + per.place(p, q));
            //StdOut.println("isOpen(2, 6) = " + per.isOpen(2, 6));
            StdOut.println("isOpen(p, q) = " + per.isOpen(p, q));
            StdOut.println("percolates = " + per.percolates());
            StdOut.println("Number open sites = " + per.numberOfOpenSites());
            StdOut.println("isFull(p, q) = " + per.isFull(p, q));
            StdOut.println("find() = " + per.find(per.place(p, q)) + " id.len = " + id.length);
            StdOut.println();
        }

        StdOut.println("System is " + per.percolates());
        StdOut.println("Number open sites = " + per.numberOfOpenSites());
    }
}
