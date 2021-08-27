import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KdTree {
    private static final boolean vertical = true;
    private static final boolean horizontal = false;

    private class Node {
        Point2D p;
        Node left;
        Node right;
        boolean position;
        int N;
        public Node(Point2D p, boolean position, int N) {
            this.p = p;
            this.position = position;
            this.N = N;
        }
    }

    private Node root;
    private List<Point2D> points;
    private Point2D neighbor;
    private boolean cont;

    public KdTree() {
        root = null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        if (root == null) return 0;
        return root.N;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = put(root, p, vertical);
    }

    private Node put(Node n, Point2D p, boolean pos) {
        if (n == null) return new Node(p, pos, 1);
        int cmp = comparePoints(p, n);
        if (cmp == -1) n.left = put(n.left, p, !pos);
        else if (cmp == 1) n.right = put(n.right, p, !pos);// чередовать проверку с x и y
        n.N = size(n.left) + size(n.right) + 1;
        return n;
    }

    private int comparePoints(Point2D p, Node n) {
        if (n.position == vertical) {
            if (p.x() < n.p.x()) return -1;
            else if (p.x() > n.p.x()) return 1;
            else {
                if (p.y() < n.p.y()) return -1;
                else if (p.y() > n.p.y()) return 1;
            }
        }
        else if (n.position == horizontal) {
            if (p.y() < n.p.y()) return -1;
            else if (p.y() > n.p.y()) return 1;
            else {
                if (p.x() < n.p.x()) return -1;
                else if (p.x() > n.p.x()) return 1;
            }
        }
        if (p.equals(n.p)) return 0;
        return Integer.MAX_VALUE;
    }

    private int size(Node n) {
        if (n == null) return 0;
        return n.N;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        cont = false;
        get(root, p);
        return cont;
    }

    private Node get(Node n, Point2D p) {
        if (n == null) return null;
        int cmp = comparePoints(p, n);
        if (cmp == -1) return get(n.left, p);
        else if (cmp == 1) return get(n.right, p);
        else if (cmp == 0) cont = true; return null;
    }

    public void draw() {
        List<Node> points = new ArrayList<>();
        points.add(root);
        while (!points.isEmpty()) {
            List<Node> copy = new ArrayList<>();
            for (int i = 0; i < points.size(); i++) {
                if (points.get(i).left != null) copy.add(points.get(i).left);
                if (points.get(i).right != null) copy.add(points.get(i).right);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.point(points.get(i).p.x(), points.get(i).p.y());
                if (points.get(i).position == vertical) {
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.line(points.get(i).p.x(), 0.0, points.get(i).p.x(), 1.0);
                }
                if (points.get(i).position == horizontal) {
                    StdDraw.setPenColor(StdDraw.BLUE);
                    StdDraw.line(0.0, points.get(i).p.y(),1.0, points.get(i).p.y());
                }
            }
            points = copy;
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        points = new ArrayList<>();
        pointsInRect(root, rect);
        return points;
    }

    private Node pointsInRect(Node n, RectHV rect) {
        if (n == null) return null;
        if (n.position == vertical) {
            if (rect.contains(n.p)) points.add(n.p);
            RectHV r1 = new RectHV(0.0,0.0, n.p.x(),1.0);
            RectHV r2 = new RectHV(n.p.x(),0.0,1.0,1.0);
            if (rect.intersects(r1)) pointsInRect(n.left, rect);
            if (rect.intersects(r2)) pointsInRect(n.right, rect);
        }
        else if (n.position == horizontal) {
            if (rect.contains(n.p)) points.add(n.p);
            RectHV r1 = new RectHV(0.0,0.0,1.0, n.p.y());
            RectHV r2 = new RectHV(0.0, n.p.y(),1.0,1.0);
            if (rect.intersects(r1)) pointsInRect(n.left, rect);
            if (rect.intersects(r2)) pointsInRect(n.right, rect);
        }
        return null;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;
        neighbor = root.p;
        findNearestNeighbor(root, p);
        return neighbor;
    }

    private Node findNearestNeighbor(Node n, Point2D p) {
        if (n == null) return null;
        if (n.p.distanceSquaredTo(p) < neighbor.distanceSquaredTo(p)) neighbor = n.p;
        RectHV r1 = null;
        RectHV r2 = null;
        if (n.position == vertical) {
            r1 = new RectHV(0.0,0.0, n.p.x(),1.0);
            r2 = new RectHV(n.p.x(),0.0,1.0,1.0);
        }
        else {
            r1 = new RectHV(0.0,0.0,1.0, n.p.y());
            r2 = new RectHV(0.0, n.p.y(),1.0,1.0);
        }
        if (r1.contains(p)) {
            if (p.distanceSquaredTo(neighbor) > r1.distanceSquaredTo(p)) findNearestNeighbor(n.left, p);
            if (p.distanceSquaredTo(neighbor) > r2.distanceSquaredTo(p)) findNearestNeighbor(n.right, p);
        }
        else {
            if (p.distanceSquaredTo(neighbor) > r2.distanceSquaredTo(p)) findNearestNeighbor(n.right, p);
            if (p.distanceSquaredTo(neighbor) > r1.distanceSquaredTo(p)) findNearestNeighbor(n.left, p);
        }
        return null;
    }

    public static void main(String[] args) {
        StdOut.println("I will write solution for this problem");
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        KdTree kdtree = new KdTree();
        StdOut.println("size = " + kdtree.size());
        StdOut.println("isEmpty = " + kdtree.isEmpty());
        kdtree.insert(new Point2D(0.1,0.1));
        kdtree.insert(new Point2D(0.2,0.2));
        StdOut.println("size = " + kdtree.size());
        StdOut.println("isEmpty = " + kdtree.isEmpty());
        StdOut.println("contains (0.4, 0.4) = " + kdtree.contains(new Point2D(0.4,0.4)));
        StdOut.println("nearest to (0.4, 0.4) = " + kdtree.nearest(new Point2D(0.4,0.4)));
        StdOut.println("range to fullRect = " + kdtree.range(rect));
        kdtree.draw();
    }
}
