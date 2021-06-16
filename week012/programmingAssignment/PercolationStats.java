import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int n, T;
    private double[] x; // fractions of open sites

    public PercolationStats(int n, int trials) {
        this.n = n;
        this.T = trials;
        if (n <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        x = new double[T];

        for (int i = 0; i < T; i++) {
            //StdOut.println("i = " + i);
            Percolation grid = new Percolation(n);
            while (!grid.percolates()) {
                int place = StdRandom.uniform(n * n);
                int row = place / n;
                int col = place % n;
                //StdOut.println("place = " + place + " row = " + row + " col = " + col);
                if (!grid.isOpen(row + 1, col + 1)) {
                    //StdOut.println("open place(" + row + ", " + col + ")");
                    grid.open(row + 1, col + 1);
                }
            }

            x[i] = 1.0 * grid.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i];
        }
        return (sum / T);
    }

    private double sqr(double n) {
        return n * n;
    }

    public double stddev() {
        double s = 0;
        for (int i = 0; i < x.length; i++) {
            s += sqr(x[i] - mean());
        }
        return Math.sqrt(s / (T - 1));
    }

    public double confidenceLo() {
        return StdStats.mean(x) - (1.96 * StdStats.stddev(x) / Math.sqrt(T));
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(T));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats a = new PercolationStats(n, T);

        StdOut.println("mean                    = " + StdStats.mean(a.x));
        StdOut.println("stddev                  = " + StdStats.stddev(a.x));
        StdOut.println("95% confidence interval = [" + a.confidenceLo() + ", " + a.confidenceHi() + "]");
    }
}
