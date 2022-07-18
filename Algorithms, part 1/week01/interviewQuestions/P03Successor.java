import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class P03Successor {
    public static void main(String[] args) {
        int n = StdIn.readInt();
        UnionFind s = new UnionFind(n);
        while (!StdIn.isEmpty()) {
            int x = StdIn.readInt();
            if (x + 1 < n) {
                s.union(x, x + 1);
                StdOut.println("successor(" + x + ") = " + s.findMax(x));
            }
            else {
                StdOut.println("There is no successor for x = " + x);
            }
        }
    }
}