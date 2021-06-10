import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private static int n, T;
    private double[] x = new double[T]; // fraction of open sites

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < trials; i++) {
            Percolation grid = new Percolation(n);
            int row = StdRandom.uniform(n);
            int col = StdRandom.uniform(n);
            //StdOut.println("row = " + row);
            //StdOut.println("col = " + col);
            while (!grid.percolates()) {
                while(grid.isOpen(row, col)) {
                    row = StdRandom.uniform(n);
                    col = StdRandom.uniform(n);
                }
                grid.open(col, row);
            }

            int openSites = grid.numberOfOpenSites();
            x[i] = openSites * 1.0 / (n * n);
        }
    }

    public double mean() {
        int sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i];
        }
        return sum * 1.0 / T;
    }

    private double sqr(double n) {
        return n * n;
    }

    public double stddev() {
        double qsum = 0;
        for (int i = 0; i < x.length; i++) {
            qsum += sqr(x[i] - mean());
        }
        return Math.sqrt(qsum / (T - 1));
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(T));
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(T));
    }

    public static void main(String[] args) {
        n = Integer.parseInt(args[0]);
        T = Integer.parseInt(args[1]);
        PercolationStats a = new PercolationStats(n, T);
        StdOut.println("mean                    = " + a.mean());
        StdOut.println("stddev                  = " + a.stddev());
        StdOut.println("95% confidence interval = [" + a.confidenceLo() + ", " + a.confidenceHi() + "]");
    }
}

/*
        id = new int[n * n];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }

        sz = new int[n * n];
        for (int i = 0; i < sz.length; i++) {
            sz[i] = 1;
        }

        op = new boolean[n][n];
        for (int i = 0; i < op.length; i++) {
            for (int j = 0; i < op[0].length; j++) {
                op[i][j] = false;
            }
        }
 */