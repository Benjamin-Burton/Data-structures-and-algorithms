import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    
    private double[] threshholds;
    private int numTrials;
    private int size;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        // set up the trials
        numTrials = trials;
        threshholds = new double[numTrials];
        size = n * n;

        // run the trials
        for (int i = 0; i < trials; i++) {
            Percolation trial = new Percolation(n);
            while (!trial.percolates()) {
                int row = StdRandom.uniformInt(1, n+1);
                int col = StdRandom.uniformInt(1, n+1);
                // System.out.println(row + " , " + col);
                trial.open(row, col);
            }
            // System.out.println(trial.numberOfOpenSites());
            threshholds[i] = (double) trial.numberOfOpenSites() / size;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        // collect the thresholds
        return StdStats.mean(threshholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = StdStats.mean(threshholds);
        double stddev = StdStats.stddev(threshholds);
        return mean - (CONFIDENCE_95*stddev / Math.sqrt(numTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = StdStats.mean(threshholds);
        double stddev = StdStats.stddev(threshholds);
        return mean + (CONFIDENCE_95*stddev / Math.sqrt(numTrials));
    }

   // test client (see below)
   public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);

    PercolationStats experiment = new PercolationStats(n, trials);
    StdOut.println("mean                     = " + experiment.mean());
    StdOut.println("stddev                   = " + experiment.stddev());
    StdOut.println("95% condfidence internal = [" + experiment.confidenceLo() + ", " + experiment.confidenceHi() + "]");
   }
}