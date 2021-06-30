import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P02SpecificElement {
    public static void main(String[] args) {
        UnionFind uf = new UnionFind(10);
        uf.union(1, 2);
        uf.union(1, 6);
        uf.union(6, 9);
        StdOut.println("findMax(5) = " + uf.findMax(5));
    }
}