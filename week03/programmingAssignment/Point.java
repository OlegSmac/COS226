import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {
        if (this.y == that.y && this.x == that.x) {
            return 0;
        }
        else if (this.y < that.y || this.y == that.y && this.x < that.x) {
            return -1;
        }
        return 1;
    }

    public double slopeTo(Point that) {
        if (this.y == that.y && this.x == that.x) {
            return Double.NEGATIVE_INFINITY;
        }
        else if (this.y == that.y) { //horizontal
            return +0.0;
        }
        else if (this.x == that.x) { // vertical
            return Double.POSITIVE_INFINITY;
        }
        return 1.0 * (that.y - this.y) / (that.x - this.x);
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            if (slopeTo(a) > slopeTo(b)) {
                return 1;
            }
            else if (slopeTo(a) == slopeTo(b)) {
                return 0;
            }
            else {
                return -1;
            }
        }
    }

    public static void main(String[] args) {
        Point p = new Point(5, 6);
        Point q = new Point(5, 6);
        StdOut.println(p.slopeTo(q));
    }
}
