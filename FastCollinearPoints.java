import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] segments;
    private Point[] points;
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] ps) {
        // finds all line segments containing 4+ points and put them in segments array
        // find the maximal set in the segment e.g. if p->q->r->s->t are collinear, will not include p->s and q->t
        if (ps == null) { 
            throw new IllegalArgumentException();
        }
        // check for invalid inputs
        for (int i = 0; i < ps.length; i++) {
            if (ps[i] == null) { throw new IllegalArgumentException(); }
        }
        // copy input into new array so we don't mutate old array
        points = Arrays.copyOf(ps, ps.length);
        Arrays.sort(points); // now our points are in order, so when we sort them by slope, their order will be maintained. 
        // that means the when we take the first -> last guys with the same slope, we will get a maximal line segment.
        // check for duplicates
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i+1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        segments = new LineSegment[1];
        // implement the algorithm
        // 1. take a point to be the origin (do so for each point)
        for (int i = 0; i < points.length - 2; i++) { // minus 2 because a shorter array won't have enough points
            Point origin = points[i];
            
            // copy all the points we are interested in
            Point[] compPoints = new Point[points.length - 1];
            int count = 0;
            for (int j = 0; j < points.length; j++) {
                if (j == i) continue;
                compPoints[count] = points[j];
                count++;
            }

            // sort them by slope
            Arrays.sort(compPoints, origin.slopeOrder());
            count = 0;
            // now we can get the first and last point with the same slope order and add to segments
            for (int k = 0; k < compPoints.length - 2; k += count + 1) { // minus 1 otherwise we get too close to the end
                
                count = 0;
                while (origin.slopeTo(compPoints[k]) == origin.slopeTo(compPoints[k + 1 + count])) {
                    count++;
                    if (k + 1 + count == compPoints.length) break;
                }
                if (count >= 2) {
                    // found a collinear set! save the answer - but is the origin the smallest? that's the answer we will take
                    // check from k to k + count
                    boolean isAnswer = true;
                    for (int m = k; m < k + count + 1; m++) {
                        if (compPoints[m].compareTo(origin) < 0) {
                            // not answer
                            isAnswer = false;
                            break;
                        }
                    }
                    if (isAnswer) {
                        LineSegment ans = new LineSegment(origin, compPoints[k + count]);
                        // array resizing for the segments array - we don't want it to overflow
                        if (numberOfSegments >= segments.length) {
                            LineSegment[] newSegments = new LineSegment[segments.length * 2];
                            for (int n = 0; n < segments.length; n++) {
                                newSegments[n] = segments[n];
                            }
                            segments = newSegments;
                        }
                        segments[numberOfSegments] = ans;
                        numberOfSegments++;
                    }
                }
            }
        }
    }   
    public int numberOfSegments() {
        // the number of line segments
        return numberOfSegments;
        
    }

    public LineSegment[] segments() {
        // the line segments
        return Arrays.copyOf(segments, numberOfSegments);
    }

    public static void main(String[] args) {
        Point[] points = new Point[15];
        points[0] = new Point(4, 4);
        points[1] = new Point(3, 3);
        points[2] = new Point(2, 2);
        points[3] = new Point(1, 1);
        points[4] = new Point(5, 5);
        points[5] = new Point(6, 6);
        points[6] = new Point(7, 7);
        points[7] = new Point(10, 10);
        points[8] = new Point(9, 9);
        points[9] = new Point(8, 8);
        points[10] = new Point(0,1);
        points[11] = new Point(0,2);
        points[12] = new Point(0,3);
        points[13] = new Point(0,4);
        points[14] = new Point(0,5);
        FastCollinearPoints test = new FastCollinearPoints(points);
        StdOut.println(test.numberOfSegments());
        LineSegment[] segs = test.segments();
        StdOut.println(segs.length);
    }
 }