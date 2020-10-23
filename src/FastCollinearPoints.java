import java.util.Arrays;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private int segmentCount;
    private Point[] pointArray;
    private Bag<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points){

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

        pointArray = points.clone();
        segments = new Bag<>();
        segmentCount = 0;

        for(Point point : points){
            Arrays.sort(pointArray, point.slopeOrder());
            extractSegments(point);
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

    private void extractSegments(Point point) {
        int start, end;
        int i = 1; // first element of array sorted by slope order is the argument point
        while(i<pointArray.length){
            start = i;
            end = i+1;
            while((end < pointArray.length) && (point.slopeTo(pointArray[start]) == point.slopeTo(pointArray[end]))){
                end++;
            }
            if((end - start) >= 3){
                Arrays.sort(pointArray, start, end);
                if(point.compareTo(pointArray[start]) < 0){
                    segments.add(new LineSegment(point, pointArray[end-1]));
                    segmentCount++;
                }
            }
            i = end;
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