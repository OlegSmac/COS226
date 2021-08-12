import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;
import java.util.Stack;

public class Board {
    private final int[][] t;
    private final int N;

    public Board(int[][] tiles) {
        t = copy(tiles);
        N = tiles.length;
    }

    public String toString() {
        String s = String.valueOf(N) + " ";
        for (int i = 0; i < this.t.length; i++) {
            s += "\n";
            for (int j = 0; j < this.t[i].length; j++) {
                s += String.valueOf(this.t[i][j]) + " ";
            }
        }
        s += "\n";
        return s;
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        int num = 1;
        int ham = 0;
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                if (t[i][j] != 0 && t[i][j] != num) ham++;
                num++;
            }
        }
        return ham;
    }

    public int manhattan() {
        int man = 0;
        for (int i = 0; i < this.t.length; i++) {
            for (int j = 0; j < this.t[i].length; j++) {
                int row = (t[i][j] - 1) / N;
                int col = (t[i][j] - 1) % N;
                if (t[i][j] != 0) {
                    man += Math.abs(row - i);
                    man += Math.abs(col - j);
                }
            }
        }
        return man;
    }

    public boolean isGoal() {
        if (this.manhattan() == 0) return true;
        return false;
    }

    public boolean equals(Object y) {
        if (y == null) return false;
        String s1 = y.toString();
        String s2 = this.toString();
        return s1.equals(s2);
    }

    public Iterable<Board> neighbors() {
        Stack<Board> neig = new Stack<>();
        int row = 0;
        int col = 0;
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                if (t[i][j] == 0) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }
        if (row != 0) {
            Board copy = new Board(copy(this.t));
            findNeighbor(copy, row, col, row - 1, col);
            neig.add(copy);
        }
        if (col != 0) {
            Board copy = new Board(copy(this.t));
            findNeighbor(copy, row, col, row, col - 1);
            neig.add(copy);
        }
        if (row != N - 1) {
            Board copy = new Board(copy(this.t));
            findNeighbor(copy, row, col, row + 1, col);
            neig.add(copy);
        }
        if (col != N - 1) {
            Board copy = new Board(copy(this.t));
            findNeighbor(copy, row, col, row, col + 1);
            neig.add(copy);
        }
        return neig;
    }

    private int[][] copy(int[][] b) {
        int[][] res = new int[b.length][b[0].length];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                res[i][j] = b[i][j];
            }
        }
        return res;
    }

    private Board findNeighbor(Board b, int row, int col, int nRow, int nCol) {
        b.t[row][col] = b.t[nRow][nCol];
        b.t[nRow][nCol] = 0;
        return b;
    }

    public Board twin() {
        int[][] twin = copy(this.t);
        if (twin[0][0] != 0 && twin[0][1] != 0)
            return new Board(swap(0, 0, 0, 1));
        else
            return new Board(swap(1, 0, 1, 1));
    }

    private int[][] swap(int r1, int c1, int r2, int c2) {
        int[][] copy = copy(this.t);
        int tmp = copy[r1][c1];
        copy[r1][c1] = copy[r2][c2];
        copy[r2][c2] = tmp;
        return copy;
    }

    public static void main(String[] args) {
        int[][] nums = {{1, 0}, {2, 3}};
        Board b = new Board(nums);
        Board g = new Board(nums);
        System.out.println(b.toString());
        System.out.println("dim = " + b.dimension());
        System.out.println("ham = " + b.hamming());
        System.out.println("man = " + b.manhattan());
        System.out.println("isGoal = " + b.isGoal());
        System.out.println("equal = " + b.equals(g));
        System.out.println("neighbors = " + b.neighbors());
        System.out.println("twin = " + b.twin());
    }
}
