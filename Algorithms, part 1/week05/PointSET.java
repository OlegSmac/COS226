import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> treeSet;

    public PointSET() {
        treeSet = new TreeSet<>();
    }

    public boolean isEmpty() {
        return treeSet.isEmpty();
    }

    public int size() {
        return treeSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        treeSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return treeSet.contains(p);
    }

    public void draw() {
        for (Iterator<Point2D> it = treeSet.iterator(); it.hasNext(); ) {
            Point2D p = it.next();
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        TreeSet<Point2D> copy = new TreeSet<>();
        copy.addAll(treeSet);
        List<Point2D> points = new ArrayList<>();
        while (!copy.isEmpty()) {
            if (rect.contains(copy.first())) points.add(copy.first());
            copy.remove(copy.first());
        }
        return points;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Point2D near = null;
        double distance = 0.0;
        for (Iterator<Point2D> it = treeSet.iterator(); it.hasNext(); ) {
            Point2D point = it.next();
            if (near == null || point.distanceTo(p) < distance) {
                near = point;
                distance = near.distanceTo(p);
            }
        }
        return near;
    }

    public static void main(String[] args) {
        PointSET set = new PointSET();
        set.insert(new Point2D(0,0));
        set.insert(new Point2D(0.4,0.4));
        set.insert(new Point2D(0.5,0.5));
        StdOut.println("isEmpty = " + set.isEmpty());
        StdOut.println("size = " + set.size());
        StdOut.println("contains = " + set.contains(new Point2D(0,0)));
        RectHV rect = new RectHV(0.0,0.0,1.0,1.0);
        StdOut.println("range = " + set.range(rect));
        StdOut.println("nearest = " + set.nearest(new Point2D(0.3,0.3)));
    }
}
