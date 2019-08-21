package Week5KDTrees;

import java.util.SortedSet;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
	
	private SortedSet<Point2D> treeSet;
	
	// construct an empty set of points 
	public PointSET() {
		treeSet = new TreeSet<Point2D>();
	}
   
	// is the set empty? 
	public boolean isEmpty() {
	   return treeSet.isEmpty();
	}
   
	// number of points in the set 
	public int size() {
		return treeSet.size();
	}
   
	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if(p == null)
			throw new IllegalArgumentException();
		treeSet.add(p);
	}
   
	// does the set contain point p? 
	public boolean contains(Point2D p) {
		if(p == null)
			throw new IllegalArgumentException();
	   return treeSet.contains(p);
	}
   
	// draw all points to standard draw 
	public void draw() {
		for(Point2D p : treeSet) {
			p.draw();
		}
	}
   
	// all points that are inside the rectangle (or on the boundary) 
	public Iterable<Point2D> range(RectHV rect) {
		if(rect == null)
			throw new IllegalArgumentException();
		Queue<Point2D> result = new Queue<Point2D>();
		for(Point2D p : treeSet) {
			if(p.x() >= rect.xmin() && p.x() <= rect.xmax())
				if(p.y() >= rect.ymin() && p.y() <= rect.ymax())
					result.enqueue(p);
		}
	   return result;
	}
   
	// a nearest neighbor in the set to point p; null if the set is empty 
	public Point2D nearest(Point2D p) {
		if(p == null)
			throw new IllegalArgumentException();
		Point2D nearest = null;
		double nearestDistance = Double.MAX_VALUE;
		for(Point2D x : treeSet) {
			double distance = x.distanceTo(p);
			if(distance < nearestDistance) {
				nearestDistance = distance;
				nearest = x;
			}
		}
	   return nearest;
	}

	public static void main(String[] args) {
		// unit testing of the methods (optional) 
	}
}