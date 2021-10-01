import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    // hash between synset synonym and synset id
    private final Hashtable<String, List<Integer>> wordGraphIdHash;
    private final Hashtable<Integer, String> grahIdSynset;
    private final Digraph g;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null)
            throw new NullPointerException("synsets");
        if (hypernyms == null)
            throw new NullPointerException("hypernyms");

        wordGraphIdHash = new Hashtable<>();
        grahIdSynset = new Hashtable<>();
        List<Synset> synsetList = readSynsets(synsets);
        List<Hypernym> hypernymList = readHypernyms(hypernyms);

        for (Synset s : synsetList) {
            for (String word : s.synonyms) {
                if (!wordGraphIdHash.containsKey(word))
                    wordGraphIdHash.put(word, new ArrayList<>());

                wordGraphIdHash.get(word).add(s.id);
            }
            grahIdSynset.put(s.id, s.value);
        }

        g = new Digraph(synsetList.size());
        for (Hypernym h : hypernymList) {
            int v = h.id;
            for (int hypernym : h.hypernyms) {
                g.addEdge(v, hypernym);
            }
        }

        if (new DirectedCycle(g).hasCycle())
            throw new IllegalArgumentException("graph is not acyclic graph");

        if (!isRooted())
            throw new IllegalArgumentException("graph is not rooted");

        sap = new SAP(g);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordGraphIdHash.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return wordGraphIdHash.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null) throw new IllegalArgumentException();
        if (nounB == null) throw new IllegalArgumentException();

        List<Integer> v = wordGraphIdHash.get(nounA);
        if (v == null) throw new IllegalArgumentException("nounA is not a WordNet noun");

        List<Integer> w = wordGraphIdHash.get(nounB);
        if (w == null) throw new IllegalArgumentException("nounB is not a WordNet noun");

        return sap.length(v, w);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of
    // nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null) throw new IllegalArgumentException();
        if (nounB == null) throw new IllegalArgumentException();

        List<Integer> v = wordGraphIdHash.get(nounA);
        if (v == null) throw new IllegalArgumentException("nounA is not a WordNet noun");

        List<Integer> w = wordGraphIdHash.get(nounB);
        if (w == null) throw new IllegalArgumentException("nounB is not a WordNet noun");

        return grahIdSynset.get(sap.ancestor(v, w));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        while (!StdIn.isEmpty()) {
            String nounA = StdIn.readString();
            String nounB = StdIn.readString();
            String ancestor = wordnet.sap(nounA, nounB);
            int distance = wordnet.distance(nounA, nounB);
            StdOut.printf("ancestor = %s, distance = %d\n", ancestor, distance);
        }
    }

    private boolean isRooted() {
        int root = -1;
        for (int i = 0; i < g.V(); i++) {
            if (g.outdegree(i) == 0) {
                if (root != -1)
                    return false;

                root = i;
            }
        }

        return true;
    }

    private List<Synset> readSynsets(String fileName) {
        List<Synset> res = new ArrayList<>();
        In in = new In(fileName);
        while (in.hasNextLine()) {
            res.add(new Synset(in.readLine()));
        }

        return res;
    }

    private List<Hypernym> readHypernyms(String fileName) {
        List<Hypernym> res = new ArrayList<>();
        In in = new In(fileName);
        while (in.hasNextLine()) {
            res.add(new Hypernym(in.readLine()));
        }

        return res;
    }

    private class Synset {
        private final int id;
        private final String[] synonyms;
        private final String value;

        private Synset(String line) {
            String[] fields = line.split(",");
            this.id = Integer.parseInt(fields[0]);
            this.value = fields[1];
            this.synonyms = fields[1].split(" ");
        }
    }

    private class Hypernym {
        private final int id;
        private final int[] hypernyms;

        private Hypernym(String line) {
            String[] fields = line.split(",");
            id = Integer.parseInt(fields[0]);
            hypernyms = new int[fields.length - 1];
            for (int i = 1; i < fields.length; i++) {
                hypernyms[i - 1] = Integer.parseInt(fields[i]);
            }
        }
    }

    private class DirectedCycle {
        private boolean[] onStack;
        private boolean[] marked;
        private boolean hasCycle;

        public DirectedCycle(Digraph g) {
            marked = new boolean[g.V()];
            onStack = new boolean[g.V()];
            for (int i = 0; i < g.V(); i++) {
                dfs(g, i);
            }
        }

        private void dfs(Digraph graph, int i) {
            if (hasCycle)
                return;

            marked[i] = true;
            onStack[i] = true;
            for (int w : graph.adj(i)) {
                if (onStack[w]) {
                    hasCycle = true;
                } else if (!marked[w]) {
                    dfs(graph, w);
                }
            }

            onStack[i] = false;
        }

        public boolean hasCycle() {
            return hasCycle;
        }
    }
}