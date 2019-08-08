package Week1Percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF nw;
    private final int n;
    private boolean[][] open;
    private int openSitesCount;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        // Initialize grid with N * N.
        // The +2 is for the virtual nodes at the top and bottom
        // Create actual grid to keep track of points
        // WeightedQuickUnionUF to keep track of unions
        uf = new WeightedQuickUnionUF(n * n + 2);
        nw = new WeightedQuickUnionUF(n * n + 2);
        this.n = n;
        openSitesCount  = 0;
        open = new boolean[n][n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // If its already open do nothing
        if (isOpen(row, col))
            return;

        // Open the place
        open[row-1][col-1] = true;
        openSitesCount++;

        // Check positions around it
        // If they are open and possible, connect them
        int pos = xyToArrayPosition(row, col);
        checkAndOpen(row - 1, col, pos);
        checkAndOpen(row + 1, col, pos);
        checkAndOpen(row, col - 1, pos);
        checkAndOpen(row, col + 1, pos);

        // If its at the top connect with the top virtual node
        if (row == 1) {
            checkAndOpen(1, col, 0);
        }

        // If its at the bottom connect with the bottom virtual node
        if (row == n) {
            uf.union(n * n +1, xyToArrayPosition(row, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBoundaries(row - 1, col - 1);
        return open[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkBoundaries(row - 1, col - 1);
       // Check if there is a connection from the virtual top node
       // To the asked node. If yes, it is open
       return nw.connected(0, xyToArrayPosition(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        if (n == 1)
            return open[0][0];
        // Check if there is a connection from virtual node to virtual node
        return uf.connected(0, n * n + 1);
    }

    private void checkAndOpen(int x, int y, int pos) {
        // If the point is open
        // connect with the one in the position is asked
        try{
            if (isOpen(x, y)) {
                nw.union(pos, xyToArrayPosition(x, y));
                uf.union(pos, xyToArrayPosition(x, y));
            }
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    private int xyToArrayPosition(int x, int y) {
        return n * (x - 1) + y;
    }

    private void checkBoundaries(int x, int y) {
        if (x >= n || y >= n || x < 0 || y < 0)
            throw new IllegalArgumentException();
    }
}