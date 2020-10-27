import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET {
    private SET<Point2D> points;

    // construct an empty set of points 
    public PointSET(){
        points = new SET<Point2D>();
    }

    // is the set empty? 
    public boolean isEmpty(){
        return points.isEmpty();
    }

    // number of points in the set 
    public int size(){
        return  points.size();
    }         

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p){
        if(p == null){
            throw new IllegalArgumentException();
        }
        points.add(p);
    }

    // does the set contain point p? 
    public boolean contains(Point2D p){
        return points.contains(p);
    }  

    // draw all points to standard draw 
    public void draw(){
        for(Point2D p : points){
            p.draw();
        }
    }  

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect){
        if(rect == null){
            throw new IllegalArgumentException();
        }

        Stack<Point2D> containedPoints = new Stack<Point2D>();
        for(Point2D p : points){
            if(rect.contains(p)){
                containedPoints.push(p);
            }
        }
        return containedPoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p){
        if(p == null){
            throw new IllegalArgumentException();
        }

        Point2D nearestNeighbour = null;
        double minDistanceSquared = Double.POSITIVE_INFINITY;
        for(Point2D q : points){
            if(p.distanceSquaredTo(q) < minDistanceSquared){
                minDistanceSquared = p.distanceSquaredTo(q);
                nearestNeighbour = q;
            }
        } 
        return nearestNeighbour;
    }             
 
    // unit testing of the methods (optional) 
    public static void main(String[] args){
        PointSET ps = new PointSET();
        ps.insert(new Point2D(0.2, 0.2));
        ps.insert(new Point2D(0.8, 0.7));
        ps.insert(new Point2D(0.8, 0.75));
        ps.draw();
        System.out.println(ps.nearest(new Point2D(0.8, 0.7)));
        System.out.println(ps.nearest(new Point2D(0.2, 0.2)));
        System.out.println("========================================");
        for(Point2D p : ps.range(new RectHV(0, 0, 0.8, 0.71))){
            System.out.println(p);
        }
    }                  
 }