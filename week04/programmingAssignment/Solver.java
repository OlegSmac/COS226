import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Stack;

public class Solver {
    private Stack<Board> solution;
    private boolean isSolvable;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        solution = new Stack<>();
        MinPQ<SNode> search = new MinPQ<>();
        search.insert(new SNode(initial, null));
        search.insert(new SNode(initial.twin(), null));
        while (!search.min().b.isGoal()) {
            SNode min = search.delMin();
            for (Board board : min.b.neighbors()) {
                if (min.prev == null || !min.prev.b.equals(board)) {
                    search.insert(new SNode(board, min));
                }
            }
        }
        SNode node = search.min();
        while (node != null) {
            solution.push(node.b);
            node = node.prev;
        }
        Board last = solution.peek();
        if (last.equals(initial)) isSolvable = true;
        Stack<Board> res = new Stack<>();
        while (!solution.isEmpty()) res.push(solution.pop());
        solution = res;
    }

    private class SNode implements Comparable<SNode> {
        private final Board b;
        private final SNode prev;
        private final int manhattan;
        private int moves;

        public SNode(Board board, SNode previous) {
            this.b = board;
            this.prev = previous;
            if (previous != null) this.moves = previous.moves + 1;
            else this.moves = 0;
            this.manhattan = board.manhattan();
        }

        public int compareTo(SNode node) {
            int pr1 = priority(this);
            int pr2 = priority(node);
            if (pr1 > pr2) return 1;
            else if (pr1 < pr2) return -1;
            else return this.manhattan - node.manhattan;
        }
    }

    private int priority(SNode node) {
        return node.manhattan + node.moves;
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        if (!this.isSolvable()) return -1;
        return solution.size() - 1;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
