import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P02SpecificElement {
    private int n;
    private UnionFind uf;

    public P02SpecificElement(int n) {
        this.n = n;
        uf = new UnionFind(n);
    }

    public static void main(String[] args) {
        P02SpecificElement el = new P02SpecificElement(10);
        el.uf.union(1, 2);
        el.uf.union(1, 6);
        el.uf.union(6, 9);
        StdOut.println("findMax(5) = " + el.uf.findMax(5));
    }
}