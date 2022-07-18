import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowEdge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BaseballElimination {
    private int n;
    private HashMap<String, Integer> teams;
    private HashMap<Integer, String> teamsIdx;
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;
    private List<String> certificateOfElimination;

    // create a baseball division from given file name in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        this.n = in.readInt();
        this.teams = new HashMap<>();
        this.teamsIdx = new HashMap<>();
        this.w = new int[n];
        this.l = new int[n];
        this.r = new int[n];
        this.g = new int[n][n];

        for (int i = 0; i < n; i++) {
            String t = in.readString();
            teams.put(t, i);
            teamsIdx.put(i, t);
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for (int j = 0; j < n; j++) {
                g[i][j] = in.readInt();
            }
        }
    }

    // number of teams
    public int numberOfTeams() {
        return teams.size();
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return w[teams.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return l[teams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return r[teams.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (!teams.containsKey(team1) || !teams.containsKey(team2)) {
            throw new IllegalArgumentException();
        }
        int idx1 = teams.get(team1);
        int idx2 = teams.get(team2);
        return g[idx1][idx2];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        certificateOfElimination = new ArrayList<>();
        int idx = teams.get(team);
        for (int i = 0; i < teams.size(); i++) {
            if (w[idx] + r[idx] < w[i]) {
                certificateOfElimination.add(teamsIdx.get(i));
                return true;
            }
        }
        //0 - teams.size is indices for teams
        idx = teams.size();
        //teams.size - matches.size is indices for matches
        //(i * 10 + j, idx) i - team1, j - team2
        HashMap<int[], Integer> matches = new HashMap<>();
        for (int i = 0; i < numberOfTeams(); i++) {
            for (int j = i + 1; j < numberOfTeams(); j++) {
                if (g[i][j] > 0 && i != teams.get(team) && j != teams.get(team)) {
                    int[] a = {i, j};
                    matches.put(a, idx);
                    //System.out.println("i = " + i + " j = " + j + " idx = " + (i * 10 + j));
                    idx++;
                }
            }
        }

        int numVertices = idx + 2;
        FlowNetwork G = new FlowNetwork(numVertices);
        int s = G.V() - 2; //like 0, but for convenience it will be an index from the end, 0 is the first team
        int t = G.V() - 1;
        for (int[] a : matches.keySet()) {
            if (a[0] == teams.get(team) || a[1] == teams.get(team)) {
                continue;
            }
            //System.out.println("i = " + a[0] + " j = " + a[1]);
            FlowEdge e = new FlowEdge(s, matches.get(a), g[a[0]][a[1]]);
            //System.out.println("i = " + (i / 10) + " j = " + (i % 10) + " " + g[i / 10][i % 10] + " " + matches.get(i));
            G.addEdge(e);
            //from match to 1-st team
            G.addEdge(new FlowEdge(matches.get(a), a[0], Double.MAX_VALUE));
            //from match to 2-nd team
            G.addEdge(new FlowEdge(matches.get(a), a[1], Double.MAX_VALUE));
        }
        for (Integer i : teamsIdx.keySet()) {
            if (!Objects.equals(teamsIdx.get(i), team)) {
                G.addEdge(new FlowEdge(i, t, w[teams.get(team)] + r[teams.get(team)] - w[i]));
            }
        }
        //System.out.println(G.toString());//it works
        certificateOfElimination = new ArrayList<>();
        FordFulkerson ford = new FordFulkerson(G, s, t);
        for (Integer i : teamsIdx.keySet()) {
            if (ford.inCut(i)) {
                certificateOfElimination.add(teamsIdx.get(i));
            }
        }
        for (FlowEdge e : G.adj(s)) {
            if (e.flow() != e.capacity()) {
                return true;
            }
        }
        certificateOfElimination = null;
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return certificateOfElimination;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        //System.out.println(division.isEliminated("Detroit"));
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
