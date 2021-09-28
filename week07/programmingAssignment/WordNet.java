import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

public class WordNet {
    private List<String> sets;
    private Hashtable<String, Integer> nouns;
    private SAP sap;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();
        this.sets = new ArrayList<>();
        this.nouns = new Hashtable<>();

        In in = new In(synsets);
        while (!in.isEmpty()) {
            String[] line = in.readLine().split(",");
            int v = Integer.parseInt(line[0]);
            sets.add(line[1]);
            String[] s = line[1].split(" ");
            for (int i = 0; i < s.length; i++) {
                if (!nouns.contains(s[i])) {
                    nouns.put(s[i], v);
                }
            }
        }

        Digraph d = new Digraph(nouns.size());
        in = new In(hypernyms);
        while (!in.isEmpty()) {
            String[] line = in.readLine().split(",");
            int v = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                int w = Integer.parseInt(line[i]);
                d.addEdge(v, w);
            }
        }
        sap = new SAP(d);
    }

    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    public boolean isNoun(String word) {
        return nouns.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        int a = nouns.get(nounA);
        int b = nouns.get(nounB);
        return sap.length(a, b);
    }

    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        int a = nouns.get(nounA);
        int b = nouns.get(nounB);
        int res = sap.ancestor(a, b);
        if (res == -1) return "-1";
        return sets.get(res);
    }

    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        System.out.println(wn.distance("a", "c"));
        System.out.println(wn.sap("a", "c"));
    }
}
