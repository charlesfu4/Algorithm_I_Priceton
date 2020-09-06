/* *****************************************************************************
 *  Name: Chu-Cheng Fu
 *  Date: 08-30-2020
 *  Description: PointSET data type - Brute force
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> pointset;
    private TreeSet<Point2D> p_inrec;
    private Point2D p_nearest;

    // construct an empty set of points
    public PointSET() {
        pointset = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointset.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointset.size();

    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("input cannot be null");

        pointset.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("input cannot be null");

        return pointset.contains(p);
    }
    // draw all points to standard draw


    public void draw() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : pointset.descendingSet()) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("input cannot be null");

        p_inrec = new TreeSet<Point2D>();
        for (Point2D p : pointset.descendingSet()) {
            if (rect.contains(p))
                p_inrec.add(p);
        }
        return p_inrec;
    }

    // a nearest neighbor in the set to point p;
    // null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("input cannot be null");

        double min_dis = Double.MAX_VALUE;
        // empty set
        if (pointset.isEmpty())
            return null;
        // normal O(N) search
        for (Point2D p_inset : pointset.descendingSet()) {
            double cal_dis = p_inset.distanceSquaredTo(p);
            if (cal_dis <= min_dis) {
                min_dis = cal_dis;
                p_nearest = p_inset;
            }
        }
        return p_nearest;

    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // constructor
        // isEmpty
        PointSET testset = new PointSET();
        StdOut.printf("Is the contructed point set empty: %s.\n", testset.isEmpty());
        // add the point to the set
        String filename = args[0];
        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            testset.insert(p);
        }

        // number of points
        StdOut.printf("There are %d points in the set.\n", testset.size());

        // does the set contain point p
        StdOut.println("Does the set contain 0.372,0.497? " +
                               testset.contains(new Point2D(0.372, 0.497)));
        // draw all points to standard draw
        testset.draw();
        // all points that are inside the rectangle
        RectHV test_rec = new RectHV(0.5, 0.5, 1, 1);

        for (Point2D p : testset.range(test_rec))
            StdOut.println("point: " + p.toString());
        // a nearest neighbor in the set to point p;null is empty
        StdOut.println("The nearest point to (1,2) is: " + testset.nearest(new Point2D(1, 2)));
    }
}
