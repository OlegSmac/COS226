import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Hashtable;

public class SAP {
    private Digraph g;
    private int minAnc;

    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        this.g = G;
        this.minAnc = g.V();
    }

    public int length(int v, int w) {
        if (v > g.V() + 1 || w > g.V() + 1) throw new IllegalArgumentException();
        if (v == w) return 0;
        Queue<Integer> q = new Queue<>();
        Hashtable<Integer, Integer> listV = new Hashtable<>();
        listV.put(v, 0);
        q.enqueue(v);
        while (!q.isEmpty()) {
            int node = q.dequeue();
            int len = listV.get(node);
            for (int i : g.adj(node)) {
                if (!listV.containsKey(i)) {
                    q.enqueue(i);
                    listV.put(i, len + 1);
                }
            }
        }
        Hashtable<Integer, Integer> listW = new Hashtable<>();
        listW.put(w, 0);
        q.enqueue(w);
        while (!q.isEmpty()) {
            int node = q.dequeue();
            int len = listW.get(node);
            for (int i : g.adj(node)) {
                if (!listW.containsKey(i)) {
                    q.enqueue(i);
                    listW.put(i, len + 1);
                }
            }
        }
        int minLen = g.E();
        boolean is = false;
        for (int i : listV.keySet()) {
            for (int j : listW.keySet()) {
                if (i == j) {
                    int path = listV.get(i) + listW.get(j);
                    if (path <= minLen) {
                        is = true;
                        minLen = path;
                        minAnc = i;
                    }
                }
            }
        }
        if (is) return minLen;
        return -1;
    }

    public int ancestor(int v, int w) {
        if (v > g.V() + 1 || w > g.V() + 1) throw new IllegalArgumentException();
        if (v == w) return v;
        int len = length(v, w);
        if (len != - 1) return minAnc;
        return -1;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        int minLen = g.E() + 1;
        for (Integer i : v) {
            for (Integer j : w) {
                if (i == null || j == null) throw new IllegalArgumentException();
                int len = length(i, j);
                if (len < minLen) minLen = len;
            }
        }
        if (minLen < g.E() + 1 && minLen != -1) return minLen;
        return -1;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        int minLen = g.E() + 1;
        for (Integer i : v) {
            for (Integer j : w) {
                if (i == null || j == null) throw new IllegalArgumentException();
                int len = length(i, j);
                if (len < minLen) minLen = len;
            }
        }
        if (minLen < g.E() + 1 && minLen != -1) return minAnc;
        return -1;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
