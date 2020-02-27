/* *****************************************************************************
 *  Name:Chu-Cheng Fu
 *  Date: 02-24-2020
 *  Description: Percolation algorithm assignment statistics part
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private double[] open_sites;
    private int n_trials;
    private int n_grid;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        n_grid = n;
        n_trials = trials;
        open_sites = new double[trials];

        for (int i = 0; i < trials; i++) {

            Percolation p = new Percolation(n);
            open_sites[i] = 0;

            while (!p.percolates()) {
                int site = StdRandom.uniform(n * n);
                p.open(site / n_grid + 1, site % n_grid + 1);
                // StdOut.println(site);
            }

            open_sites[i] = (double) p.numberOfOpenSites() / (n_grid * n_grid);


        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(open_sites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(open_sites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return StdStats.mean(open_sites) - 1.96 * StdStats.stddev(open_sites) / Math.sqrt(n_trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return StdStats.mean(open_sites) + 1.96 * StdStats.stddev(open_sites) / Math.sqrt(n_trials);
    }


    public static void main(String[] args) {
        // input args0 and args1
        int n = Integer.parseInt(args[0]);
        if (n <= 0)
            throw new IllegalArgumentException();
        int trial = Integer.parseInt(args[1]);
        if (trial <= 0)
            throw new IllegalArgumentException();
        PercolationStats ps = new PercolationStats(n, trial);
        StdOut.println("mean          = " + ps.mean());
        StdOut.println("stddev        = " + ps.stddev());
        StdOut.println("95% CI        = [" + ps.confidenceLo() + " ," + ps.confidenceHi() + "]");
    }
}
