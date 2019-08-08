package Week1Percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;


public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private final double[] trials;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        this.trials = new double[trials];

        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                percolation.open(row, col);
            }

            this.trials[i] = (double) percolation.numberOfOpenSites() / (double) (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.trials);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.trials);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(this.trials.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(this.trials.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        Stopwatch sw = new Stopwatch();

        PercolationStats perStat = new PercolationStats(n, t);

        System.out.printf("mean                    = %f\n", perStat.mean());
        System.out.printf("stddev                  = %f\n", perStat.stddev());
        System.out.printf("95%% confidence interval = %f, %f\n",
                perStat.confidenceLo(), perStat.confidenceHi());

        System.out.println("------");
        System.out.printf("Total time: %f secs. (for N=%d, T=%d)",
                sw.elapsedTime(), n, t);
    }

}