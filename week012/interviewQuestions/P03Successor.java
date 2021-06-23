import edu.princeton.cs.algs4.StdOut;

public class P03Successor {
    private int n;
    private int[] s;

    public P03Successor(int n) {
        this.n = n;
        this.s = new int[n];

        for(int i = 0; i < s.length; i++) {
            s[i] = i;
        }
    }

    public int del(int x) {

    }

    public int findY(int x) {

    }

    public static void main(String[] var0) {
        int n = Integer.parseInt(var0[0]);
        P03Successor suc = new P03Successor(n);
        suc.del(2);
        suc.del(3);

        for(int i = 0; i < suc.s.length; i++) {
            StdOut.println("s[" + i + "] = " + suc.s[i]);
        }

        int y = suc.findY(3);
        StdOut.println("y = " + y);
    }
}