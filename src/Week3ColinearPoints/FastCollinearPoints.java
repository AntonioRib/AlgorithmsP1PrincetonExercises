package Week3ColinearPoints;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

	private final LineSegment[] lineSegments;
	
	public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 points
		// Check for duplicate points
		checkNull(points);
	    Point[] sortedPoints = points.clone();
	    Arrays.sort(sortedPoints);
	    checkDuplicate(sortedPoints);
	    
	    final int N = points.length;
	    final List<LineSegment> maxLineSegments = new LinkedList<LineSegment>();

	    for(int i = 0; i < N; i++) {
	    	Point p = sortedPoints[i];
	    	Point[] pointsBySlope = sortedPoints.clone();
	    	//Sort arrays based on the slope done with p;
	    	Arrays.sort(pointsBySlope, p.slopeOrder());
	    	
	    	int y = 1;
	    	while(y < N) {
	    		LinkedList<Point> candidates = new LinkedList<Point>();
	    		//Gets the closes slope (it will be the one after the 1st, the 0 its itself)
	    		final double REF = p.slopeTo(pointsBySlope[y]);
                do {
                	//Add the ones that have the same slope
                    candidates.add(pointsBySlope[y++]);
                } while (y < N && p.slopeTo(pointsBySlope[y]) == REF);
	    	
                //if there are enough get the 1st and the last;
                //And create that segment
	            if (candidates.size() >= 3
	                    && p.compareTo(candidates.peek()) < 0) {
	                Point min = p;
	                Point max = candidates.removeLast();
	                maxLineSegments.add(new LineSegment(min, max));
	            }
	    	}
	    }
	    lineSegments = maxLineSegments.toArray(new LineSegment[0]);
	}

	public int numberOfSegments() { // the number of line segments
		return lineSegments.length;
	}

	public LineSegment[] segments() {
		return Arrays.copyOf(lineSegments, numberOfSegments());
	}
	
    private void checkNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
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