import java.util.Arrays;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private int segmentCount;
    private Bag<LineSegment> segments;
    private Point[] pointArray;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points){

        if(points == null){
            throw new IllegalArgumentException();
        }

        int n = points.length;
        for(int i = 0; i < n; i++){
            if(points[i] == null){
                throw new IllegalArgumentException();
            }
        }

        for(int i = 0; i < n; i++){
            for(int j = (i+1); j < n; j++){
                if(points[i].compareTo(points[j]) == 0){
                    throw new IllegalArgumentException();
                }
            }
        }

        segments = new Bag<>();
        segmentCount = 0;
        pointArray = points.clone();
        Arrays.sort(pointArray);

        for(int i = 0; i < n; i++){
            for(int j = i+1; j < n; j++){
                for(int k = j+1; k < n; k++){
                    if(pointArray[i].slopeTo(pointArray[j]) != pointArray[i].slopeTo(pointArray[k])){
                        continue;
                    }
                    for(int l = k+1; l < n; l++){
                        if(pointArray[i].slopeTo(pointArray[k]) != pointArray[i].slopeTo(pointArray[l])){
                            continue;
                        }
                        LineSegment seg = new LineSegment(pointArray[i], pointArray[l]);
                        segments.add(seg);
                        segmentCount++;
                    }
                }
            }
        }

    }

     // the number of line segments
    public int numberOfSegments(){
        return segmentCount;
    }
    
    // the line segments
    public LineSegment[] segments(){
        LineSegment[] segArray = new LineSegment[segmentCount];
        int i = 0;
        for(LineSegment seg : segments){
            segArray[i] = seg;
            i++;
        }
        return segArray;
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