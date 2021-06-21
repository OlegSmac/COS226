import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P01SocialConnectivity {
    private int n;
    private UnionFind uf;

    public P01SocialConnectivity(int n) {
        this.n = n;
        uf = new UnionFind(n);
    }

    public boolean con() {
        return uf.count() == 1;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int m = StdIn.readInt();
        int early = n;
        P01SocialConnectivity net = new P01SocialConnectivity(n);
        for (int i = 0; i < m; i++) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            net.uf.union(p - 1, q - 1);
            StdOut.println("p = " + p + " q = " + q);

            if (net.con() == true && i < early) {
                early = i;
                StdOut.println("Early time = " + (i + 1));
                break;
            }
            StdOut.println();
        }
        StdOut.println("Yes, n = " + n + ", m = " + m);
    }
}