import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    // perform trials independent experiments on an n-by-n grid
    private double[] percolationThresholds;
    private int noOfTrials;
    public PercolationStats(int n, int trials) {
            if (n <= 0 || trials <= 0) {
                throw new IllegalArgumentException(); 
            }
            percolationThresholds = new double[trials];
            noOfTrials = trials;
            for (int i = 0; i < trials; i++) {
                Percolation sample=new Percolation(n);
                while (sample.percolates() != true) {
                    int random = StdRandom.uniform(1, n*n+1);
                    int row = (random%n == 0) ? (random/n) : (random/n+1);
                    int col = (random%n == 0) ? n : random%n;                    
                    if (sample.isOpen(row, col) != true) {
                        sample.open(row, col);                        
                    }
                }
                percolationThresholds[i] = (double) sample.numberOfOpenSites() / (double) (n*n);
            }
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationThresholds);
    }  
    
    // sample standard deviation of percolation threshold   
    public double stddev() {
        return StdStats.stddev(percolationThresholds);        
    }   
    
    // low  endpoint of 95% confidence interval   
    public double confidenceLo() {
        return mean()-((1.96*stddev())/Math.sqrt((double)noOfTrials));
    }      
    
    // high endpoint of 95% confidence interval   
    public double confidenceHi() {
        return mean()+((1.96*stddev())/Math.sqrt((double)noOfTrials));        
    }  
    
    // test client (described below)
    public static void main(String[] args) {
        int gridCount = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        Stopwatch stopwatch = new Stopwatch();
        PercolationStats result = new PercolationStats(gridCount, trials);
        StdOut.println(stopwatch.elapsedTime());
        StdOut.println("mean                    = "+result.mean());
        StdOut.println("stddev                  = "+result.stddev());
        StdOut.println("95% confidence interval = ["+result.confidenceLo()+", "+result.confidenceHi()+"]");
    }        
}