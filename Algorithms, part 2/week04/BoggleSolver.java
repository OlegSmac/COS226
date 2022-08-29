import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

import java.util.TreeSet;

public class BoggleSolver {
    private TreeSet<String> dic = new TreeSet<>();
    private SET<String> res = new SET();
    private final int[][] dir = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    private final int[] lengths = {1, 2, 3, 5, 11};
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (String s : dictionary) {
            dic.add(s);
        }
    }

    private boolean isPrefix(String prefix) {
        String s = dic.ceiling(prefix);
        if (s == null) {
            return false;
        }
        for (int i = 0; i < prefix.length(); i++) {
            if (s.charAt(i) != prefix.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private void find(int i, int j, BoggleBoard board, boolean[][] visit, String word) {
        visit[i][j] = true;
        word += board.getLetter(i, j);
        if (board.getLetter(i, j) == 'Q') {
            word += 'U';
        }
        /*
        System.out.println("i = " + i + " j = " + j + " word = " + word);
        for (int x = 0; x < visit.length; x++) {
            for (int y = 0; y < visit[0].length; y++) {
                System.out.print(visit[x][y] + " ");
            }
            System.out.println();
        }
        System.out.println();
        */
        if (word.length() >= 3 && dic.contains(word)) {
            res.add(word);
        }
        for (int k = 0; k < dir.length; k++) {
            if (i + dir[k][0] >= 0 &&
                i + dir[k][0] < board.rows() &&
                j + dir[k][1] >= 0 &&
                j + dir[k][1] < board.cols() &&
                !visit[i + dir[k][0]][j + dir[k][1]] &&
                isPrefix(word + board.getLetter(i + dir[k][0], j + dir[k][1]))) {
                    find(i + dir[k][0], j + dir[k][1], board, visit, word);
                    //System.out.println("next ->");
            }
        }
        visit[i][j] = false;
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        res = new SET();
        //System.out.println("H = " + isPrefix("H") + ", ceiling = " + dic.ceiling("H") + ", contains = " + dic.contains("H"));
        boolean[][] visit = new boolean[board.rows()][board.cols()];
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                find(i, j, board, visit, "");
            }
        }
        return res;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!dic.contains(word) || word.length() < 3) {
            return 0;
        }
        if (word.length() <= 4) {
            return lengths[0];
        }
        else if (word.length() == 5) {
            return lengths[1];
        }
        else if (word.length() == 6) {
            return lengths[2];
        }
        else if (word.length() == 7) {
            return lengths[3];
        }
        return lengths[4];
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
