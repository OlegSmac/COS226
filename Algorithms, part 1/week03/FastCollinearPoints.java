import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class FastCollinearPoints {
    private static final double EPS = 1E-12;
    private ArrayList<LineSegment> seg = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        if (dublicates(points)) {
            throw new IllegalArgumentException();
        }

        for (int p = 0; p < points.length; p++) {
            Point[] copy = new Point[points.length - 1];
            for (int q = 0; q < points.length; q++) {
                if (p != q) {
                    if (q < p) {
                        copy[q] = points[q];
                    }
                    else if (q > p) {
                        copy[q - 1] = points[q];
                    }
                }
            }
            Arrays.sort(copy, points[p].slopeOrder());
            for (int i = 0; i < copy.length;) {
                int start = i;
                Point minPoint = points[p];
                Point maxPoint = points[p];
                double slope = copy[i].slopeTo(points[p]);
                for (; i < copy.length; i++) {
                    if (!doubleEquals(slope, copy[i].slopeTo(points[p]), EPS)) break;

                    if (copy[i].compareTo(minPoint) < 0) {
                        minPoint = copy[i];
                    }
                    if (copy[i].compareTo(maxPoint) > 0) {
                        maxPoint = copy[i];
                    }
                }
                if (minPoint == points[p] && i - start > 2) {
                    seg.add(new LineSegment(minPoint, maxPoint));
                }
            }
        }
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

    private boolean dublicates(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) return true;
            }
        }
        return false;
    }

    private boolean doubleEquals(double d1, double d2, double eps) {
        return d1 == d2 || Math.abs(d1 - d2) < eps;
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