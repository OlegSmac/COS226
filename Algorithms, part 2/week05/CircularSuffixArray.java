import java.util.Arrays;

public class CircularSuffixArray {
    private CircularSuffix[] suffixes;
    private String s;

    private class CircularSuffix implements Comparable<CircularSuffix> {
        int id;

        public CircularSuffix(int id) {
            this.id = id;
        }

        public int compareTo(CircularSuffix that) {
            int i = id;
            int j = that.id;
            int way = 0;
            while (way < s.length()) {
                if (s.charAt(i) < s.charAt(j)) {
                    return -1;
                }
                else if (s.charAt(i) > s.charAt(j)) {
                    return 1;
                }
                i++;
                j++;
                way++; 
                if (i == s.length()) {
                    i = 0;
                }
                if (j == s.length()) {
                    j = 0;
                }
            }
            return 0;
        }

        public int getId() {
            return id;
        }
    }

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        this.s = s;
        suffixes = new CircularSuffix[s.length()];
        for (int i = 0; i < s.length(); i++) {
            suffixes[i] = new CircularSuffix(i);
        }
        Arrays.sort(suffixes);

    }

    // length of s
    public int length() {
        return s.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length()) {
            throw new IllegalArgumentException();
        }

        return suffixes[i].getId();
    }

    // unit testing (required)
    public static void main(String[] args) {
        String s = "zebra";
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(s);
        for (int i = 0; i < s.length(); i++) {
            System.out.println(i + " str = " + circularSuffixArray.index(i));
        }
    }

}