import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P01SocialConnectivity {
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int m = StdIn.readInt();
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < m; i++) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            uf.union(p - 1, q - 1);
            StdOut.println("p = " + p + " q = " + q);

            if (uf.size(p - 1) == n) {
                StdOut.println("Early time = " + (i + 1));
                break;
            }
        }
        StdOut.println("Yes, n = " + n + ", m = " + m);
    }
}