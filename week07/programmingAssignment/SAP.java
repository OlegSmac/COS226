import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Hashtable;

public class SAP {
    private Digraph g;

    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        this.g = G;
    }

    public int length(int v, int w) {
        if (v >= g.V() || w >= g.V()) throw new IllegalArgumentException();

        Ancestor a = findAnc(v, w);
        if (a == null) return -1;
        else return a.distance;
    }

    public int ancestor(int v, int w) {
        if (v >= g.V() || w >= g.V()) throw new IllegalArgumentException();

        Ancestor a = findAnc(v, w);
        if (a == null) return -1;
        else return a.id;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        Ancestor res = null;
        for (Integer i : v) {
            for (Integer j : w) {
                if (i == null || j == null) throw new IllegalArgumentException();
                Ancestor a = findAnc(i, j);
                if (res == null) res = a;
                else if (a.distance < res.distance) res = a;
            }
        }
        if (res == null) return -1;
        return res.distance;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        Ancestor res = null;
        for (Integer i : v) {
            for (Integer j : w) {
                if (i == null || j == null) throw new IllegalArgumentException();
                Ancestor a = findAnc(i, j);
                if (res == null) res = a;
                else if (a.distance < res.distance) res = a;
            }
        }
        if (res == null) return -1;
        return res.id;
    }

    private Ancestor findAnc(int v, int w) {
        if (v >= g.V() || w >= g.V()) throw new IllegalArgumentException();
        Hashtable<Integer, Integer> listV = distance(v); //(key, len to key)
        Hashtable<Integer, Integer> listW = distance(w);
        Ancestor minAnc = null;
        for (int node : listV.keySet()) {
            if (listW.containsKey(node)) { //find min way
                int len = listV.get(node) + listW.get(node);
                if (minAnc == null) minAnc = new Ancestor(node, len);
                else if (len < minAnc.distance) {
                    minAnc.id = node;
                    minAnc.distance = len;
                }
            }
        }
        return minAnc;
    }

    private Hashtable<Integer, Integer> distance(int v) {
        Queue<Integer> q = new Queue<>();
        Hashtable<Integer, Integer> listV = new Hashtable<>(); //(key, len to key)
        listV.put(v, 0);
        q.enqueue(v);
        while (!q.isEmpty()) { //breadth-first search
            int node = q.dequeue();
            int len = listV.get(node);
            for (int i : g.adj(node)) {
                if (!listV.containsKey(i)) {
                    q.enqueue(i);
                    listV.put(i, len + 1);
                }
            }
        }
        return listV;
    }

    private class Ancestor {
        private int id;
        private int distance;

        private Ancestor(int id, int distance) {
            this.id = id;
            this.distance = distance;
        }
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
