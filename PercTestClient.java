public class PercTestClient {
    public static void main(String[] args) {
        // Percolation perc = new Percolation(10);

        // for (int i = 1; i < 11; i++) {
        //     perc.open(i, 10);
        // }

        // System.out.println(perc.numberOfOpenSites());
        // System.out.println(perc.percolates());

        PercolationStats experiment = new PercolationStats(20,300);
        System.out.println("Mean threshold: " + experiment.mean());
        System.out.println("Confidence interval: [" + experiment.confidenceLo() + ", " + experiment.confidenceHi() + "]");
    }
}
