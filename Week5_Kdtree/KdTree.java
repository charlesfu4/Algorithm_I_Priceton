/* *****************************************************************************
 *  Name: Chu-Cheng Fu
 *  Date: 04-09-2020
 *  Description: KdTree - 2d implementation
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class KdTree {

    private Node root;
    private int size;
    private boolean contained;
    private Queue<Point2D> p_inrec;
    private Point2D p_nearest;
    private double dis_nearest;


    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean orient; // true = x-split, false = y-split

        //constructor for Node
        public Node(Point2D p, RectHV rect, Node lb, Node rt, boolean orient) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
            this.orient = orient;
        }
    }


    // construct an empty set of points
    public KdTree() {
        root = new Node(null, new RectHV(0, 0, 1, 1), null, null, true);
        size = 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }


    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("input cannot be null");
        insert(root, p);
    }

    private void insert(Node n, Point2D p) {
        // if the point does not exist
        if (n.p == null) {

            size++;
            n.p = p;
            if (n.orient) {

                n.lb = new Node(null, new RectHV(n.rect.xmin(), n.rect.ymin(),
                                                 n.p.x(), n.rect.ymax()),
                                null, null, false);
                n.rt = new Node(null, new RectHV(n.p.x(), n.rect.ymin(),
                                                 n.rect.xmax(), n.rect.ymax()),
                                null, null, false);
                /*StdOut.println(n.rect.xmin() + ", " + p.x());
                StdOut.println(p.x() + ", " + n.rect.xmax());
                StdOut.println("x " + size);*/
            }
            else {
                n.lb = new Node(null, new RectHV(n.rect.xmin(), n.rect.ymin(),
                                                 n.rect.xmax(), n.p.y()),
                                null, null, true);
                n.rt = new Node(null, new RectHV(n.rect.xmin(), n.p.y(),
                                                 n.rect.xmax(), n.rect.ymax()),
                                null, null, true);
                /*StdOut.println(n.rect.ymin() + ", " + p.y());
                StdOut.println(p.y() + ", " + n.rect.ymax());
                StdOut.println("y " + size);*/
            }
            return;
        }

        // if the point has already existed
        else if (n.p.equals(p)) {
            return;
        }

        if (n.orient) {
            if (p.x() < n.p.x()) insert(n.lb, p);
            else insert(n.rt, p);
        }
        else {
            if (p.y() < n.p.y()) insert(n.lb, p);
            else insert(n.rt, p);

        }


    }


    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("input cannot be null");
        contained = false;
        contains(root, p);
        return contained;
    }

    private void contains(Node n, Point2D p) {

        if (n.p == null) return;

        if (n.p.distanceSquaredTo(p) == 0) {
            contained = true;
            return;
        }
        if (n.orient) {
            if (p.x() < n.p.x()) contains(n.lb, p);
            else if (p.x() > n.p.x()) contains(n.rt, p);
            else {
                contains(n.rt, p);
                contains(n.lb, p);
            }
        }
        if (!n.orient) {
            if (p.y() < n.p.y()) contains(n.lb, p);
            else if (p.y() > n.p.y()) contains(n.rt, p);
            else {
                contains(n.rt, p);
                contains(n.lb, p);
            }
        }

    }


    // draw all points to standard draw
    public void draw() {
        draw(root);
    }

    private void draw(KdTree.Node n) {
        if (n.lb.p != null && n.rt.p != null) {
            // draw the dot
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            n.p.draw();
            //draw the lines
            if (n.orient) {
                // red for vertical
                StdDraw.setPenRadius(0.001);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());

            }
            else {
                // blue for vertical
                StdDraw.setPenRadius(0.001);
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());

            }

            draw(n.lb);
            draw(n.rt);

        }
        else if (n.lb.p == null && n.rt.p == null) {
            if (n.orient) {
                // red for vertical
                StdDraw.setPenRadius(0.001);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());

            }
            else {
                // blue for vertical
                StdDraw.setPenRadius(0.001);
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());

            }
            // draw the dot
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            n.p.draw();
        }
        else if (n.rt.p != null) {
            if (n.orient) {
                // red for vertical
                StdDraw.setPenRadius(0.001);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());

            }
            else {

                // red for vertical
                StdDraw.setPenRadius(0.001);
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());

            }
            // draw the dot
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            n.p.draw();
            draw(n.rt);
        }
        else {
            if (n.orient) {
                // red for vertical
                StdDraw.setPenRadius(0.001);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());

            }
            else {

                // red for vertical
                StdDraw.setPenRadius(0.001);
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());

            }
            // draw the dot
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            n.p.draw();
            draw(n.lb);
        }

    }


    // range search
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("input cannot be null");
        p_inrec = new Queue<Point2D>();
        range(rect, root);
        return p_inrec;
    }

    // range search helper
    private void range(RectHV rect, Node n) {
        if (n.lb != null && n.rt != null && n.p != null) {
            if (rect.contains(n.p)) p_inrec.enqueue(n.p);
            if (rect.intersects(n.lb.rect)) range(rect, n.lb);
            if (rect.intersects(n.rt.rect)) range(rect, n.rt);

        }
        else if (n.lb == null && n.rt == null) {
            return;
        }


    }

    // a nearest neighbor in the set to point p;
    // null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("input cannot be null");
        // Initialize of the point and distance
        dis_nearest = Double.POSITIVE_INFINITY;
        p_nearest = root.p;
        nearest(root, p);
        return p_nearest;

    }


    private void nearest(Node n, Point2D p) {
        if (n.p == null) return;
        // search the subtrees based on location
        if (n.p.distanceSquaredTo(p) < dis_nearest) {
            dis_nearest = n.p.distanceSquaredTo(p);
            p_nearest = n.p;
        }

        if (n.orient) {
            // go left

            if (p.x() < n.p.x()) {
                nearest(n.lb, p);
                if (n.rt.rect.distanceSquaredTo(p) > dis_nearest)
                    return;
                else nearest(n.rt, p);

            }
            // go right
            else {
                nearest(n.rt, p);
                if (n.lb.rect.distanceSquaredTo(p) > dis_nearest)
                    return;
                else nearest(n.lb, p);
            }

        }
        else {
            if (p.y() < n.p.y()) {
                nearest(n.lb, p);
                if (n.rt.rect.distanceSquaredTo(p) > dis_nearest)
                    return;
                else nearest(n.rt, p);
            }
            // go right
            else {
                nearest(n.rt, p);
                if (n.lb.rect.distanceSquaredTo(p) > dis_nearest)
                    return;
                else nearest(n.lb, p);
            }

        }

    }


    public static void main(String[] args) {
        KdTree testset = new KdTree();
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
        StdOut.println("Does the set contain 0.206107 0.904508? " +
                               testset.contains(new Point2D(0.206107, 0.904508)));
        // draw all points to standard draw
        testset.draw();

        // all points that are inside the rectangle
        RectHV test_rec = new RectHV(0.5, 0.5, 1, 1);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.GREEN);
        test_rec.draw();
        //for (Point2D p : testset.range(test_rec))
        //    StdOut.println("point: " + p.toString());
        // a nearest neighbor in the set to point p;null is empty

        //StdOut.println(
        //       "The nearest point to (0.81,0.3) is: " + testset.nearest(new Point2D(0.81, 0.3)));
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.02);
        new Point2D(0.81, 0.3).draw();
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setPenRadius(0.02);
        testset.nearest(new Point2D(0.81, 0.3)).draw();
    }
}
