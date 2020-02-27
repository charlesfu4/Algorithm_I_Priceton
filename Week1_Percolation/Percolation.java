/* *****************************************************************************
 *  Name: Chu-Cheng Fu
 *  Date: 24-02-2020
 *  Description:Percolation algorithm assignment main part
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int open_count = 0;
    private boolean[][] grid;
    private WeightedQuickUnionUF quf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        int l = n + 2;
        grid = new boolean[l][l];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                grid[i][j] = false;
            }
        }
        // Quick Union Find with top and bottom extra nodes
        quf = new WeightedQuickUnionUF(l * l + 2);
        // union the top row and bottom row first
        for (int j = 1; j < l - 1; j++) {
            quf.union(l * l, l + j);
            quf.union(l * l + 1, l * n + j);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int n = grid[0].length;
        if (row < 0 || col < 0 || row > n - 2 || col > n - 2)
            throw new IllegalArgumentException();
        else {
            if (!this.isOpen(row, col)) {
                open_count++;
                grid[row][col] = true;
                if (this.isOpen(row, col - 1))
                    quf.union(n * row + col, n * row + col - 1);

                if (this.isOpen(row, col + 1))
                    quf.union(n * row + col, n * row + col + 1);

                if (this.isOpen(row - 1, col))
                    quf.union(n * row + col, n * (row - 1) + col);

                if (this.isOpen(row + 1, col))
                    quf.union(n * row + col, n * (row + 1) + col);

            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int n = grid[0].length;
        if (row < 0 || col < 0 || row > n - 2 || col > n - 2)
            throw new IllegalArgumentException();
        else
            return this.grid[row][col];
    }

    // is the site (row, col) full?
    // full means an open site that can be connected to the top row
    public boolean isFull(int row, int col) {
        int n = grid[0].length;
        if (row < 0 || col < 0 || row > n - 2 || col > n - 2)
            throw new IllegalArgumentException();
        else {
            if (quf.find(n * row + col) == quf.find(n * n) && this.isOpen(row, col))
                return true;
            return false;
        }

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return open_count;
    }

    // does the system percolate?
    public boolean percolates() {
        int n = grid[0].length;
        if (n == 3) {
            if (quf.find(n * n) == quf.find(n * n + 1) && this.isOpen(1, 1))
                return true;
            return false;
        }
        if (quf.find(n * n) == quf.find(n * n + 1))
            return true;
        return false;
    }

    // test client (optional)

    public static void main(String[] args) {

        In in = new In(args[0]);      // input file
        int n = in.readInt();
        StdOut.println(n);
        Percolation p = new Percolation(n);
        while (!in.isEmpty()) {
            int row = in.readInt();
            int col = in.readInt();
            p.open(row, col);
        }
        // testing
        StdOut.println("There are " + p.numberOfOpenSites() + " opened blocks.");
        StdOut.println("Percolated? " + p.percolates());


    }
}
