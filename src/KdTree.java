import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int size;

    // inner node class
    private class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, RectHV rect, Node lb, Node rt){
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
        }
     }

    // construct an empty set of points 
    public KdTree(){
        size = 0;
    }

    // is the set empty? 
    public boolean isEmpty(){
        return (size  == 0);
    }

    // number of points in the set 
    public int size(){
        return  size;
    }         

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p){
        if(p == null){
            throw new IllegalArgumentException();
        }

        root = insert(root, p, true, 0, 0, 1, 1); // true = vertical line split;
    }

    private Node insert(Node x, Point2D p, boolean vline, double xmin, double ymin,
        double xmax, double ymax){
        if(x == null){
            size ++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax), null, null);
        }
        if(x.p.equals(p)){
            return x;
        }
        
        int cmp;
        if(vline){
            cmp = Point2D.X_ORDER.compare(p, x.p);
            if(cmp < 0){
                x.lb = insert(x.lb, p, !vline, xmin, ymin, x.p.x(), ymax);
            } else {
                x.rt = insert(x.rt, p, !vline, x.p.x(), ymin, xmax, ymax);
            }
        } else {
            cmp = Point2D.Y_ORDER.compare(p, x.p);
            if(cmp < 0){
                x.lb = insert(x.lb, p, !vline, xmin, ymin, xmax, x.p.y());
            } else {
                x.rt = insert(x.rt, p, !vline, xmin, x.p.y(), xmax, ymax);
            }
    
        }

        return x;

    }

    // does the set contain point p? 
    public boolean contains(Point2D p){
        if(p == null){
            throw new IllegalArgumentException();
        }
        
        return get(root,p,true) != null;
    }
    
    private Node get(Node x, Point2D p, boolean vline){
        if(x == null){
            return null;
        } else if (x.p.equals(p)){
            return x;
        }

        int cmp;
        if(vline){
            cmp = Point2D.X_ORDER.compare(p, x.p);
        } else {
            cmp = Point2D.Y_ORDER.compare(p, x.p);
        }

        if(cmp < 0){
            return get(x.lb, p, !vline);
        } else {
            return get(x.rt, p, !vline);
        }

    }

    // draw all points to standard draw 
    public void draw(){
        draw(root, true);
    }
    
    private void draw(Node x, boolean vline){
        if(x !=null){
            draw(x.lb, !vline);
            
            // draw point
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            x.p.draw();

            // draw line
            StdDraw.setPenRadius();
            if(vline){
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
            }

            draw(x.rt, !vline);
        }
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect){
        if(rect == null){
            throw new IllegalArgumentException();
        }

        Stack<Point2D> containedPoints = new Stack<Point2D>();
        range(root, rect, containedPoints);

        return containedPoints;
    }

    private void range(Node x, RectHV rect, Stack<Point2D> stack){
        if(x == null){
            return;
        }
        if(rect.intersects(x.rect)){
            range(x.lb, rect, stack);
            if(rect.contains(x.p)){
                stack.push(x.p);
            }
            range(x.rt, rect, stack);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p){
        if(p == null){
            throw new IllegalArgumentException();
        }

        if(isEmpty()){
            return null;
        } else {
            return nearest(root,p,root.p,true);
        }

    }

    private Point2D nearest(Node x, Point2D p, Point2D nearest, boolean vline){
        if(x.p.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)){
            nearest = x.p;
        }
        if((x.lb != null) && (x.rt != null)){
            int cmp;
            if(vline){
                cmp = Point2D.X_ORDER.compare(p, x.p);
            } else {
                cmp = Point2D.Y_ORDER.compare(p, x.p);
            }
            if(cmp < 0){
                nearest = nearest(x.lb, p, nearest, !vline);
                if(x.rt.rect.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)){
                    nearest = nearest(x.rt, p, nearest, !vline);
                }
            } else {
                nearest = nearest(x.rt, p, nearest, !vline);
                if(x.lb.rect.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)){
                    nearest = nearest(x.lb, p, nearest, !vline);
                }
            }
        } else if((x.lb != null) && (x.rt == null)){
            if(x.lb.rect.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)){
                nearest = nearest(x.lb, p, nearest, !vline);
            }
        } else if((x.lb == null) && (x.rt != null)){
            if(x.rt.rect.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)){
                nearest = nearest(x.rt, p, nearest, !vline);
            }
        }
        return nearest;
    }
 
    // unit testing of the methods (optional) 
    public static void main(String[] args){
        KdTree kd = new KdTree();
        kd.insert(new Point2D(0.5, 0.5));
        kd.insert(new Point2D(0.4, 0.5));
        kd.insert(new Point2D(0.6, 0.4));
        kd.insert(new Point2D(0.4, 0.6));
        kd.draw();
        for(Point2D p : kd.range(new RectHV(0, 0, 0.45, 1))){
            System.out.println(p);
        }
        System.out.println(kd.nearest(new Point2D(0.6, 0.45)));
        System.out.println(kd.contains((new Point2D(0.4, 0.3))));
        System.out.println("test");
    }                  
 }