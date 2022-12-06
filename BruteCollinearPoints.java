import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] points;
    private LineSegment[] segments;
    private int numberOfSegments = 0;

    public BruteCollinearPoints(Point[] ps) {
        // finds all line segments containing 4 points and put them in segments array
        if (ps == null) { 
            throw new IllegalArgumentException();
        }
        // check for invalid inputs
        for (int i = 0; i < ps.length; i++) {
            if (ps[i] == null) { throw new IllegalArgumentException(); }
        }
        // copy input into new array
        points = Arrays.copyOf(ps, ps.length);
        
        // brute-force check for duplicates - n**2 order of magnitude -- why don't we implement a sorting algoirthm instead
        // mergesort
        Arrays.sort(points);

        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        Point p, q, r, s;
        LineSegment solution;
        segments = new LineSegment[points.length];
        numberOfSegments = 0;
        
        // the main search n**4 (4 for-loops)
        for (int i = 0; i < points.length - 3; i++) {
            p = points[i];
            for (int j = i + 1; j < points.length - 2; j++) {
                q = points[j];
                for (int k = j + 1; k < points.length - 1; k++) {
                    r = points[k];
                    for (int m = k + 1; m < points.length; m++) {
                        s = points[m];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {
                            // solution found
                            // find the smallest x and largest ex
                            Point smallest = p;
                            if (q.compareTo(smallest) < 0) { smallest = q; }
                            if (r.compareTo(smallest) < 0) { smallest = r; }
                            if (s.compareTo(smallest) < 0) { smallest = s; }
                            Point largest = p;
                            if (q.compareTo(largest) > 0) { largest = q; }
                            if (r.compareTo(largest) > 0) { largest = r; }
                            if (s.compareTo(largest) > 0) { largest = s; }

                            solution = new LineSegment(smallest, largest);
                            segments[numberOfSegments] = solution;
                            numberOfSegments++;
                        }
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
        return Arrays.copyOf(segments, numberOfSegments); // should just return the non-null entries
    }
    public static void main(String[] args) {
        Point[] points = new Point[5];
        points[0] = new Point(4, 4);
        points[1] = new Point(3, 3);
        points[2] = new Point(2, 2);
        points[3] = new Point(1, 1);
        points[4] = new Point(8, 4);
        BruteCollinearPoints test = new BruteCollinearPoints(points);
        StdOut.println(test.numberOfSegments());
        LineSegment[] segs = test.segments();
        StdOut.println(segs.length);
    }
}
