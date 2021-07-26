import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private ArrayList<LineSegment> seg = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        ComparePoints comparePoints = new ComparePoints();
        ArrayList<Point> allColPoints = new ArrayList<Point>();
        for (int p = 0; p < points.length; p++) {
            if (points[p] == null) {
                throw new IllegalArgumentException();
            }
            double[] slopes = new double[points.length];
            Point[] copyPoints = points.clone();
            for (int q = 0; q < points.length; q++) {
                slopes[q] = points[q].slopeTo(points[p]);
            }
            sorting(slopes, copyPoints);
            int n = 0;
            ArrayList<Point> colPoints = new ArrayList<>();
            for (int i = 1; i < slopes.length; i++) {
                if (slopes[i] == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
                if (slopes[i] == slopes[i - 1]) {
                    if (colPoints.isEmpty()) {
                        colPoints.add(points[p]);
                        colPoints.add(copyPoints[i - 1]);
                    }
                    colPoints.add(copyPoints[i]);
                    n++;
                }
                else if (slopes[i] != slopes[i - 1] && n < 2) {
                    colPoints.clear();
                    n = 0;
                }
                if ((slopes[i] != slopes[i - 1] && n >= 2) ||
                        (i == slopes.length - 1 && n >= 2)) {
                    colPoints.sort(comparePoints);
                    if (!contain(allColPoints, colPoints)) {
                        seg.add(new LineSegment(colPoints.get(0), colPoints.get(n + 1)));
                        allColPoints.add(colPoints.get(0));
                        allColPoints.add(colPoints.get(n + 1));
                    }
                    colPoints.clear();
                    n = 0;
                }
            }
        }
    }

    private void sorting(double[] sl, Point[] points) {
        for (int i = 1; i < sl.length; i++) {
            for (int j = i; j > 0 && sl[j] < sl[j - 1]; j--) {
                double swap = sl[j];
                sl[j] = sl[j - 1];
                sl[j - 1] = swap;
                Point s = points[j];
                points[j] = points[j - 1];
                points[j - 1] = s;
            }
        }
    }

    private class ComparePoints implements Comparator<Point> {
        public int compare(Point i, Point j) {
            return i.compareTo(j);
        }
    }

    private boolean contain(ArrayList<Point> all, ArrayList<Point> colPoints) {
        for (int i = 0; i < all.size(); i += 2) {
            if (all.get(i) == colPoints.get(0) && all.get(i + 1) == colPoints.get(colPoints.size() - 1)) {
                return true;
            }
        }
        return false;
    }

    public int numberOfSegments() {
        return seg.size();
    }

    public LineSegment[] segments() {
        LineSegment[] res = new LineSegment[seg.size()];
        for (int i = 0; i < seg.size(); i++) {
            res[i] = seg.get(i);
        }
        return res;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
