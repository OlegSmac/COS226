import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Hashtable;

public class Outcast {
    private WordNet WN;
    private Hashtable<String, Integer> nounDist;

    public Outcast(WordNet wordNet) {
        this.WN = wordNet;
        this.nounDist = new Hashtable<>();
    }

    public String outcast(String[] nouns) {
        int maxDist = -1;
        String res = "";
        for (String i : nouns) {
            int dist = 0;
            for (String j : nouns) {
                if (i != j) dist += WN.distance(i, j);
            }
            if (maxDist == -1 || dist > maxDist) {
                maxDist = dist;
                res = i;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
