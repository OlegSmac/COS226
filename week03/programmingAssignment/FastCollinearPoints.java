import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private int numOfSegments;
    private LineSegment[] seg;
    private double[] slopes;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) { throw new IllegalArgumentException(); }
            for (int j = 0; j < i; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        ComparePoints comparePoints = new ComparePoints();
        ArrayList<Point> colPoints = new ArrayList<>();
        ArrayList<ArrayList<Point>> allColPoints = new ArrayList<ArrayList<Point>>();
        seg = new LineSegment[points.length];
        for (int p = 0; p < points.length; p++) {
            slopes = new double[points.length];
            Point[] copyPoints = new Point[points.length];
            for (int q = 0; q < points.length; q++) {
                slopes[q] = points[p].slopeTo(points[q]);
                copyPoints[q] = points[q];
            }
            sorting(slopes, copyPoints);
            int n = 0;
            for (int i = 1; i < slopes.length; i++) {
                if (slopes[i] == slopes[i - 1]) {
                    if (n == 0) {
                        colPoints.add(points[p]);
                        colPoints.add(copyPoints[i - 1]);
                    }
                    colPoints.add(copyPoints[i]);
                    n++;
                    if (n >= 2) {
                        colPoints.sort(comparePoints);
                        if (allColPoints.contains(colPoints)) {
                            break;
                        }
                        else {
                            allColPoints.add(colPoints);
                            seg[numOfSegments++] = new LineSegment(colPoints.get(0), colPoints.get(n + 1));
                        }
                    }
                }
                else {
                    n = 0;
                    colPoints = new ArrayList<>();
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

    public int numberOfSegments() {
        return numOfSegments;
    }

    public LineSegment[] segments() {
        LineSegment[] res = new LineSegment[numOfSegments];
        for (int i = 0; i < numOfSegments; i++) {
            res[i] = seg[i];
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
