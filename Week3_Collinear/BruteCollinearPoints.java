/* *****************************************************************************
 *  Name: Chu-Cheng Fu
 *  Date: 08-03-2020
 *  Description: Queue algorithm assignment - BruteCollinearPoints
 **************************************************************************** */


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] seg;
    private int count = 0;
    private Point[] im_points;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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

        // initialization of local constrcutor fields
        seg = new LineSegment[4];
        // collinear points no more than 4 in assignment discription
        Point[] temp = new Point[4];
        im_points = Arrays.copyOf(points, points.length);
        // n^4 worst case searching
        for (int i = 0; i < im_points.length; i++) {

            for (int j = i + 1; j < im_points.length; j++) {

                for (int k = j + 1; k < im_points.length; k++) {
                    // point[k] should have matching slopes otherwise to point[k+1]
                    if (im_points[i].slopeTo(im_points[j]) != im_points[j].slopeTo(im_points[k]))
                        continue;
                    for (int l = k + 1; l < im_points.length; l++) {
                        if (im_points[i].slopeTo(im_points[j]) == im_points[i]
                                .slopeTo(im_points[l])) {
                            this.count++;
                            temp[0] = im_points[i];
                            temp[1] = im_points[j];
                            temp[2] = im_points[k];
                            temp[3] = im_points[l];
                            Arrays.sort(temp);
                            //dynamic array expansion
                            if (this.count >= this.seg.length)
                                this.seg = Arrays.copyOf(this.seg, count);
                            seg[this.count - 1] = new LineSegment(temp[0], temp[3]);

                        }

                    }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
