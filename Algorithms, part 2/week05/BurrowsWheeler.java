import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String str = BinaryStdIn.readString();
        CircularSuffixArray cir = new CircularSuffixArray(str);
        ArrayList<Character> res = new ArrayList<Character>();
        int idx = 0;
        for (int i = 0; i < cir.length(); i++) {
            if (cir.index(i) == 0) {
                idx = i;
            }
            res.add(str.charAt(cir.index(i) == 0 ? cir.length() - 1 : cir.index(i) - 1));
        }
        BinaryStdOut.write(idx);
        for (char c : res) {
            BinaryStdOut.write(c);
        }
        BinaryStdOut.close();
    }

    private static int[] forNext(char[] t, int first) {
        class Node implements Comparable<Node> {
            char value;
            int index;

            public Node(char value, int index) {
                this.index = index;
                this.value = value;
            }

            public int compareTo(Node that) {
                return Character.compare(value, that.value);
            }

            public int getIndex() {
                return index;
            }
        }

        int[] next = new int[t.length];
        Node[] nodes = new Node[t.length];

        for (int i = 0; i < next.length; i++) {
            nodes[i] = new Node(t[i], i);
        }
        Arrays.sort(nodes);
        for (int i = 0; i < next.length; i++) {
            next[i] = nodes[i].getIndex();
        }

        return next;
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String str = BinaryStdIn.readString();
        int len = str.length();
        char[] s = str.toCharArray();
        char[] firstCol = str.toCharArray();
        Arrays.sort(firstCol);
        int[] next = forNext(s, first);

        int idx = first;
        ArrayList<Character> res = new ArrayList<Character>();
        for (int i = 0; i < len; i++) {
            res.add(firstCol[idx]);
            idx = next[idx];
        }
        for (char c : res) {
            BinaryStdOut.write(c);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        }
        if (args[0].equals("+")) {
            inverseTransform();
        }
    }

}