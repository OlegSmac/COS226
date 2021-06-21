import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class P02SpecificElement {
    private int n;
    private UnionFind w;

    public P02SpecificElement(int n) {
        this.n = n;
        w = new UnionFind(n);
    }

    public static void main(String[] args) {
        P02SpecificElement el = new P02SpecificElement(10);
        el.w.union(1, 2);
        el.w.union(1, 6);
        el.w.union(6, 9);
        StdOut.println("findMax(5) = " + el.w.findMax(5));
    }
}