import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    // Initializes a new point.
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Draws this point to standard draw.
    public void draw() {
        StdDraw.point(x, y);
    }

    // Draws the line segment between this point and the specified point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // Returns the slope between this point and the specified point.
    public double slopeTo(Point that) {
        if(that.x != this.x){
            if(that.y != this.y){
                return ((double)(that.y - this.y)/(double)(that.x - this.x));
            } else {
                return 0.0;
            }
        } else if(that.y != this.y){
            return Double.POSITIVE_INFINITY;
        } else {
            return Double.NEGATIVE_INFINITY;
        }
    }

    // Compares two points by y-coordinate, breaking ties by x-coordinate.
    public int compareTo(Point that) {
        if(that.y > this.y){
            return -1;
        } else if (that.y < this.y){
            return 1; 
        } else {
            if(that.x > this.x){
                return -1;
            } else if (that.x < this.x){
                return 1;
            } else {
                return 0;
            }
        }
    }

    // Compares two points by the slope they make with this point.
    public Comparator<Point> slopeOrder() {
        return new slopeOrderComparator();
    }

    // Returns a string representation of this point.
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    private class slopeOrderComparator implements Comparator<Point>{
        public int compare(Point p1, Point p2){
            double slopeP1 = slopeTo(p1);
            double slopeP2 = slopeTo(p2);
            
            if(slopeP1 < slopeP2){
                return -1;
            } else if (slopeP1 > slopeP2){
                return 1;
            } else {
                return 0;
            }
        }
    }

    // Basic tests
    public static void main(String[] args) {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(0, 1);
        Point p4 = new Point(1,1);
        Point p5 = new Point(-2,-7);

        // slope tests
        assert p1.slopeTo(p1) == Double.NEGATIVE_INFINITY;
        assert p1.slopeTo(p2) == 0;
        assert p1.slopeTo(p3) == Double.POSITIVE_INFINITY;
        assert p1.slopeTo(p4) == 1;
        assert p1.slopeTo(p5) == 3.5;

        // compareTo tests
        assert p1.compareTo(p1) == 0;
        assert p1.compareTo(p2) == -1;
        assert p1.compareTo(p3) == -1;
        assert p1.compareTo(p4) == -1;
        assert p1.compareTo(p5) == 1;

        // comparator tests
        Comparator<Point> c1 = p1.slopeOrder();
        assert c1.compare(p1, p1) ==  0;
        assert c1.compare(p4, p5) ==  -1;
        assert c1.compare(p5, p4) ==  1;
        assert c1.compare(p1, p2) ==  -1;
        assert c1.compare(p3, p3) ==  0;
    }
}
