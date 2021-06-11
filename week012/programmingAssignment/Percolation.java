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
    }

    private int place(int row, int col) {
        return row * n + col;
    }

    public void open(int row, int col) {
        int pl = place(row - 1, col - 1);
        if (pl < 0 || pl >= n * n) {
            //throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            return;
        }
        else {
            op[pl] = true;
        }

        if (row - 2 >= 0 && col - 1 >= 0 && op[place(row - 2, col - 1)]) { // top
            w.union(place(row - 2, col - 1), pl);
        }

        if (row < n && col - 1 < n && op[place(row, col - 1)]) { // low
            w.union(place(row,col - 1), pl);
        }

        if (row - 1 >= 0 && col - 2 >= 0 && op[place(row - 1, col - 2)]) { // left
            w.union(place(row - 1, col - 2), pl);
        }

        if (row - 1 < n && col < n && op[place(row - 1, col)]) { // right
            w.union(place(row - 1, col), pl);
        }
    }

    public boolean isOpen(int row, int col) {
        int pl = place(row - 1, col - 1);
        if (pl < 0 || pl >= n * n) {
            //throw new IllegalArgumentException();
        }
        return op[pl];
    }

    public boolean isFull(int row, int col) {
        int pl = place(row - 1, col - 1);
        if (pl < 0 || pl >= n * n) {
            //throw new IllegalArgumentException();
        }
        if (op[pl] && w.find(pl) == w.find(id.length - 2)) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < op.length; i++) {
            if (op[i]) {
                count++;
            }
        }
        return count;
    }

    public boolean percolates() {
        return w.find(id[id.length - 2]) == w.find(id[id.length - 1]);
    }

    public static void main(String[] main) {
        int n = StdIn.readInt();
        Percolation per = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            per.open(6, 7);
            //per.open(6, 8);
            if (p == 1) {
                per.w.union(per.id[per.id.length - 2], per.place(0, q - 1)); // high row
            }
            if (p == n) {
                per.w.union(per.id[per.id.length - 1], per.place(p - 1, q - 1)); // low row
            }
            //StdOut.println("isFull(p, q) = " + per.isFull(p, q));
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
