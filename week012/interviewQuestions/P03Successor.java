import edu.princeton.cs.algs4.StdOut;

public class P03Successor {
    private int n;
    private UnionFind s;
    private int prevDel;

    public P03Successor(int n) {
        this.n = n;
        this.prevDel = -1;
        s = new UnionFind(n);
    }

    public void del(int x) {
        if (prevDel != -1) {
            s.union(x, prevDel);
        }
        prevDel = x;
    }

    public int findY(int x) {
        StdOut.println("findMax(" + x + ") = " + s.findMax(x));
        return s.findMax(x) + 1;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        P03Successor suc = new P03Successor(n);
        suc.del(2);
        suc.del(1);

        /*
        for(int i = 0; i < suc.length; i++) {
            StdOut.println("s[" + i + "] = " + suc.s[i]);
        }
         */

        int y = suc.findY(1);
        StdOut.println("y = " + y);
    }
}