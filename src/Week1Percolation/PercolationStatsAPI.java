package Week1Percolation;

public interface PercolationStatsAPI {

    // sample mean of percolation threshold
    public double mean();

    // sample standard deviation of percolation threshold
    public double stddev();

    // low endpoint of 95% confidence interval
    public double confidenceLo();

    // high endpoint of 95% confidence interval
    public double confidenceHi();
}