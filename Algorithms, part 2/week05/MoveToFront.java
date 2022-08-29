import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.HexDump;
import edu.princeton.cs.algs4.In;

public class MoveToFront {
    private static int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] chars = new char[R];
        for (int i = 0; i < R; i++) {
            chars[i] = (char) i;
        }

        String s = BinaryStdIn.readString();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            int j = 0;
            for (; j < chars.length; j++) {
                if (chars[j] == c) {
                    break;
                }
            }
            for (int k = j; k > 0; k--) {
                chars[k] = chars[k - 1];
            }
            chars[0] = c;

            BinaryStdOut.write(j, 8);
        }

        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] chars = new char[R];
        for (int i = 0; i < R; i++) {
            chars[i] = (char) i;
        }

        String s = BinaryStdIn.readString();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i); //it is index
            char ch = chars[c];
            BinaryStdOut.write(ch);

            for (int j = c - 1; j >= 0; j--) {
                chars[j + 1] = chars[j];
            }
            chars[0] = ch;
        }
        
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        }
        if (args[0].equals("+")) {
            decode();
        }
    }
}