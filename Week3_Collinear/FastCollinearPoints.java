import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


public class FastCollinearPoints {
    private int count = 0;
    private int head;
    private int tail;
    private LineSegment[] seg = new LineSegment[10];
    private Point[] compPoints;
    private Point[] slope_compPoints;


    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        //pre-check for constructor

        if (points == null || points.length == 0)
            throw new IllegalArgumentException("The array should not be null!");
        // N^2 checking repeative points
        for (int j = 0; j < points.length; j++) {
            if (points[j] == null)
                throw new IllegalArgumentException("null points are not valid");
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException(
                            "Points should not be repeatedly decalred!");
            }
        }

        slope_compPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(slope_compPoints);
        // create array compPoints for storing q
        for (int i = 0; i < slope_compPoints.length; i++) {
            compPoints = Arrays.copyOf(slope_compPoints, slope_compPoints.length);
            Point p = compPoints[i];
            Arrays.sort(compPoints, p.slopeOrder());
            //Sort compPoints by slopeOrder

            head = 0;
            tail = 1;

            // comparison of slope across p(points[i]) and q's
            while (head < compPoints.length - 1) {
                if (p.slopeTo(compPoints[head]) == p.slopeTo(compPoints[tail])) {
                    if (tail < compPoints.length - 1)
                        tail++;
                        // tail at the end
                    else {
                        if (tail - head >= 2 && p.compareTo(compPoints[head]) < 0) {
                            count++;
                            if (count >= seg.length)
                                this.seg = Arrays.copyOf(this.seg, seg.length * 2);
                            seg[count - 1] = new LineSegment(compPoints[tail], p);
                        }
                        head = tail;

                    }
                }
                // tail within middle of the array and met
                else if (p.slopeTo(compPoints[head]) != p.slopeTo(compPoints[tail])) {
                    if (tail - head >= 3 && p.compareTo(compPoints[head]) < 0) {
                        count++;
                        if (count >= seg.length)
                            this.seg = Arrays.copyOf(this.seg, seg.length * 2);
                        seg[count - 1] = new LineSegment(compPoints[tail - 1], p);
                    }

                    head = tail;
                    tail++;
                }
                else {
                    head = tail;
                    tail++;
                }

            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return count;
    }

    // the line segments

    public LineSegment[] segments() {
        LineSegment[] output_seg = Arrays.copyOf(seg, count);
        return output_seg;
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
        StdOut.println(collinear.numberOfSegments());
        StdDraw.show();
    }
}
