import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;

public class PercolationStats {

    private double[] results;
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if ((n < 1)||(trials < 1)){
            throw new IllegalArgumentException();
        }
        results = new double[trials];
        this.trials = trials;

        for(int i = 0;i<trials;i++){
            Percolation perc = new Percolation(n);
            while(!perc.percolates()){
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                perc.open(row, col);
            }
            results[i] = ((double)perc.numberOfOpenSites())/(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean()-1.96*stddev()/Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean()+1.96*stddev()/Math.sqrt(trials);
    }

   // test client (see below)
   public static void main(String[] args){
       int n = Integer.parseInt(args[0]);
       int trials = Integer.parseInt(args[1]);
       PercolationStats experiment = new PercolationStats(n, trials);
       
       System.out.printf("mean = %f\n", experiment.mean());
       System.out.printf("stddev = %f\n", experiment.stddev());
       System.out.printf("95%% confidence interval = [%f,%f]\n", 
                         experiment.confidenceLo(),
                         experiment.confidenceHi());
   }
}
