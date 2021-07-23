import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    private int numOfSegments;
    private LineSegment[] seg;
    private Point[] colPoints = new Point[4];

    private class ComparePoints implements Comparator<Point> {
        public int compare(Point i, Point j) {
            return i.compareTo(j);
        }
    }

    public BruteCollinearPoints(Point[] points) {
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
        seg = new LineSegment[points.length];
        for (int p = 0; p < points.length; p++) {
            colPoints[0] = points[p];

            for (int q = p + 1; q < points.length; q++) {
                double slope = points[p].slopeTo(points[q]);
                colPoints[1] = points[q];

                for (int r = q + 1; r < points.length; r++) {
                    if (slope == points[p].slopeTo(points[r])) {
                        colPoints[2] = points[r];

                        for (int s = r + 1; s < points.length; s++) {
                            if (slope == points[p].slopeTo(points[s])) {
                                colPoints[3] = points[s];
                                Arrays.sort(colPoints, comparePoints);
                                seg[numOfSegments++] = new LineSegment(colPoints[0], colPoints[3]);
                            }
                        }
                    }
                }
            }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        //StdOut.println("num = " + collinear.numberOfSegments());
    }
}
