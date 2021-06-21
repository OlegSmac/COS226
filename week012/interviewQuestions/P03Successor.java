import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class P03Successor {
    private int n;
    private int[] s;

    public P03Successor(int n) {
        this.n = n;
        s = new int[n];

        for (int i = 0; i < s.length; i++) {
            s[i] = i;
        }
    }

    public int del(int x) {

    }

    public int findY(int x) {
        
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        P03Successor suc = new P03Successor(n);
        suc.del(2);
        suc.del(3);

        StdOut.println("y = " + suc.findY(3) + " end = " + suc.end);
    }
}