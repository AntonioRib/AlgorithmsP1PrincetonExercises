package Week5KDTrees;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
	
	private Node root;
	private int size;
	
	// construct an empty set of points 
	public KdTree() {
		this.root = null;
		this.size = 0;
	}
   
	// is the set empty? 
	public boolean isEmpty() {
	   return root == null;
	}
   
	// number of points in the set 
	public int size() {
		return size;
	}
   
	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if(p == null)
			throw new IllegalArgumentException();
		if(root == null || !contains(p)) {
			size++;
			root = insert(root, p, VERTICAL);
		}
	}
	
	private Node insert(Node n, Point2D p, boolean split) {
		if(n == null)
			return new Node(p, split, null, null);
		// if separator is vertical compare x
		// if separator is horizontal compare y
		if(n.separator == VERTICAL)
			if(p.x() < n.point.x())
				n.left = insert(n.left, p, n.nextSeparator());
			else 
				n.right = insert(n.right, p, n.nextSeparator());
		if(n.separator == HORIZONTAL)
			if(p.y() < n.point.y())
				n.left = insert(n.left, p, n.nextSeparator());
			else 
				n.right = insert(n.right, p, n.nextSeparator());
		return n;
	}
   
	// does the set contain point p? 
	public boolean contains(Point2D p) {
        if (p == null) 
            throw new IllegalArgumentException();
		return contains(root, p);
	}
	
	private boolean contains(Node n, Point2D p) {
		if(n == null)
			return false;
		int cmp = n.point.compareTo(p);
		if(cmp == 0)
			return true;
		// if separator is vertical check if its on the left
		// if separator is horizontal check if its on top
		// if any of those go through left if neither go through right
		if(n.separator == VERTICAL && p.x() < n.point.x() ||
				n.separator == HORIZONTAL && p.y() < n.point.y())
			return contains(n.left, p);
		else
			return contains(n.right, p);
	}
   
	// draw all points to standard draw 
	public void draw() {
        if (!isEmpty()) {
            root.draw();
        }
	}
   
	// all points that are inside the rectangle (or on the boundary) 
	public Iterable<Point2D> range(RectHV rect) {
		if(rect == null)
			throw new IllegalArgumentException();
		List<Point2D> inRange = new ArrayList<Point2D>();
		if(isEmpty())
			return inRange;
		range(root, rect, inRange);
		return inRange;
	}
   
	private void range(Node n, RectHV rect, List<Point2D> inRange) {
		if(n == null)
			return;
		
		Point2D p = n.point;
		if (rect.contains(p)) {
			inRange.add(p);
			range(n.left, rect, inRange);
			range(n.right, rect, inRange);
			return;
		} 
		
		if(n.isRightOrTopOf(new Point2D(rect.xmin(), rect.ymin())))
			range(n.left, rect, inRange);
		
		if(!n.isRightOrTopOf(new Point2D(rect.xmax(), rect.ymax())))
			range(n.right, rect, inRange);
	}
	
	// a nearest neighbor in the set to point p; null if the set is empty 
	public Point2D nearest(Point2D p) {
		if(p == null)
			throw new IllegalArgumentException();
		return isEmpty() ? null : nearest(root, p, root).point;
	}
	
	private Node nearest(Node current, Point2D p, Node nearest) {
		if (current == null)
			return nearest;
		
		double nearestDistance = nearest.point.distanceTo(p);
		double currentDistance = current.point.distanceTo(p);

		if (currentDistance < nearestDistance)
			nearest = current;
		
		if (current.isRightOrTopOf(p)) {
			nearest = nearest(current.left, p,  nearest);
			nearest = nearest(current.right, p, nearest);
		} else {
			nearest = nearest(current.right, p, nearest);
			nearest = nearest(current.left, p,  nearest);
		}
		
		return nearest;
	}

	private static final boolean VERTICAL = true;
	private static final boolean HORIZONTAL = false;
	
	private static class Node {
		private final boolean separator;
		private final Point2D point;
		private Node left;
		private Node right;
		
		public Node(Point2D p, boolean separator, Node left, Node right) {
			point = p;
			this.separator = separator;
			this.left = left;
			this.right = right;
		}
		
		public boolean nextSeparator() {
			return separator == VERTICAL ? HORIZONTAL : VERTICAL;
		}
		
        private void draw() {
            if (left != null)
                left.draw();
            point.draw();
            if (right != null)
                right.draw();
        }
        
        public boolean isRightOrTopOf(Point2D q) {
            return (separator == HORIZONTAL && point.y() > q.y())
                    || (separator == VERTICAL && point.x() > q.x());
        }
	}
	
	
	public static void main(String[] args) {
		// unit testing of the methods (optional) 
	}
}