import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private WeightedQuickUnionUF w;
    private int[] id;
    private boolean[] op;

    public Percolation(int n) {
        this.n = n;
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        w = new WeightedQuickUnionUF(n * n + 2);

        id = new int[n * n + 2];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }

        op = new boolean[n * n + 2];
        for (int i = 0; i < op.length; i++) {
            op[i] = false;
        }

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
        }

        if (row - 2 >= 0 && op[place(row - 2, col - 1)]) { // top
            w.union(place(row - 2, col - 1), pl);
        }

        if (row < n && op[place(row, col - 1)]) { // low
            w.union(place(row,col - 1), pl);
        }

        if (col - 2 >= 0 && op[place(row - 1, col - 2)]) { // left
            w.union(place(row - 1, col - 2), pl);
        }

        if (col < n && op[place(row - 1, col)]) { // right
            w.union(place(row - 1, col), pl);
        }

        if (row == 1) {
            w.union(id[id.length - 2], place(0,col - 1)); // high row
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

        if (isOpen(row, col) && row == n) {
            w.union(pl, id[id.length - 1]);
        }

        if (isOpen(row, col) && w.find(pl) == id.length - 2) {
            StdOut.println("find(" + row + ", " + col + ") = " + w.find(pl));
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < op.length - 2; i++) {
            if (op[i]) {
                count++;
            }
        }
        return count;
    }

    public boolean percolates() {
        for (int i = 0; i < n; i++) {
            if (isOpen(n, i + 1) && isFull(n, i + 1)) {
                //return true;
            }
        }
        return w.connected(id[id.length - 2], id[id.length - 1]);
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
