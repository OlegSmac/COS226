import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private final int n;
    private int numOp;
    private final UnionFind w;
    private final UnionFind w_full;
    private final boolean[] op;

    public Percolation(int n) {
        this.n = n;

        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        w = new UnionFind(n * n + 2);
        w_full = new UnionFind(n * n + 2);

        op = new boolean[n * n + 2];
        op[op.length - 2] = true;
        op[op.length - 1] = true;
    }

    private int place(int row, int col) {
        return row * n + col;
    }

    public void open(int row, int col) {
        int pl = place(row - 1, col - 1);
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new java.lang.IllegalArgumentException();
        }

        if (isOpen(row, col)) {
            return;
        }
        else {
            op[pl] = true;
            numOp++;
        }

        int[][] dpp = {{-2, -1}, {0, -1}, {-1, -2}, {-1, 0}};
        for (int[] dp : dpp) {
            if (row + dp[0] >= 0
                    && col + dp[1] >= 0
                    && row + dp[0] < n
                    && col + dp[1] < n
                    && op[place(row + dp[0], col + dp[1])]) { // all
                w.union(place(row + dp[0], col + dp[1]), pl);
                w_full.union(place(row + dp[0], col + dp[1]), pl);
            }
        }

        if (row == 1) {
            w.union(n * n, place(0,col - 1)); // high row
            w_full.union(n * n, place(0,col - 1)); // high row
        }

        if (row == n) {
            w.union(n * n + 1, place(row - 1, col - 1)); // low row
        }
    }

    public boolean isOpen(int row, int col) {
        int pl = place(row - 1, col - 1);
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new java.lang.IllegalArgumentException();
        }
        return op[pl];
    }

    public boolean isFull(int row, int col) {
        int pl = place(row - 1, col - 1);
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new java.lang.IllegalArgumentException();
        }

        return isOpen(row, col) && w_full.find(pl) == w_full.find(n * n);
    }

    public int numberOfOpenSites() {
        return numOp;
    }

    public boolean percolates() {
        return w.find(n * n) == w.find(n * n + 1);
    }

    public static void main(String[] main) {
        int n = StdIn.readInt();
        Percolation per = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            per.open(p, q);

            StdOut.println("p = " + p + " q = " + q);
            StdOut.println("place(p, q) = " + per.place(p - 1, q - 1));
            StdOut.println("isOpen(p, q) = " + per.isOpen(p, q));
            StdOut.println("percolates = " + per.percolates());
            StdOut.println("Number open sites = " + per.numberOfOpenSites());
            StdOut.println("isFull(p, q) = " + per.isFull(p, q));
            StdOut.println("find() = " + per.w.find(per.place(p - 1, q - 1)));
            StdOut.println();
        }

        StdOut.println("System is " + per.percolates());
        StdOut.println("Number open sites = " + per.numberOfOpenSites());
    }
}
