import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class FastCollinearPoints {
    private static final double EPS = 1E-12;
    private LineSegment[] segmentsArray;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        if (containsDuplicate(points)) {
            throw new IllegalArgumentException();
        }
        List<LineSegment> segmentList = new ArrayList<LineSegment>();
        for (int j = 0; j < points.length; j++) {
            Point[] cands = new Point[points.length - 1];
            for (int k = 0; k < points.length; k++) {
                if (k != j) {
                    if (k < j)
                        cands[k] = points[k];
                    else
                        cands[k - 1] = points[k];
                }
            }

            Arrays.sort(cands, points[j].slopeOrder());
            for (int i = 0; i < cands.length;) {
                int start = i;
                Point minPoint = points[j];
                Point maxPoint = points[j];
                double slope = cands[start].slopeTo(points[j]);
                for (; i < cands.length; i++) {
                    if (!doubleEquals(slope, cands[i].slopeTo(points[j]), EPS))
                        break;

                    if (cands[i].compareTo(minPoint) < 0) {
                        minPoint = cands[i];
                    }

                    if (cands[i].compareTo(maxPoint) > 0) {
                        maxPoint = cands[i];
                    }
                }

                if (minPoint == points[j] && i - start > 2) {
                    segmentList.add(new LineSegment(minPoint, maxPoint));
                }
            }
        }

        this.segmentsArray = new LineSegment[segmentList.size()];
        segmentList.toArray(this.segmentsArray);
    }

    private boolean doubleEquals(double d1, double d2, double eps) {
        return d1 == d2 || Math.abs(d1 - d2) < eps;
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.segmentsArray.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return this.segmentsArray.clone();
    }

    private static boolean containsDuplicate(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    return true;
            }
        }

        return false;
    }
}