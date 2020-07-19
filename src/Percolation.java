import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] sites;
    private WeightedQuickUnionUF uf;
    private int dim;
    private int uf_nodes;
    private int openSitesCount;

        // creates n-by-n grid, with all sites initially blocked
        public Percolation(int n){
            if(n<=0){
                throw new IllegalArgumentException();
            }
            dim = n;
            uf_nodes = n*n+2;
            uf = new WeightedQuickUnionUF(uf_nodes);
            sites = new boolean[n][n];

            // connect top row sites to virtual site
            for(int i=0;i<n;i++){
                uf.union(0, 1+i);
            }

            // connect bottom row sites to virtual site
            for(int i=(n-1)*n;i<(n*n);i++){
                uf.union(n*n+1, 1+i);
            }
        }

        // opens the site (row, col) if it is not open already
        public void open(int row, int col){
            idxCheck(row, col);
            if (!isOpen(row, col)){
                sites[row-1][col-1] = true;
                openSitesCount += 1;
                connectSite(row, col);
            }
        }
    
        // is the site (row, col) open?
        public boolean isOpen(int row, int col){
            idxCheck(row, col);
            return (sites[row-1][col-1] == true);
        }
    
        // is the site (row, col) full?
        public boolean isFull(int row, int col){
            idxCheck(row, col);
            return (isOpen(row, col) && (uf.find(ufIdx(row, col)) == uf.find(0)));
        }
    
        // returns the number of open sites
        public int numberOfOpenSites(){
            return openSitesCount;
        }
    
        // does the system percolate?
        public boolean percolates(){
            return ((openSitesCount > 0) && (uf.find(0) == uf.find(uf_nodes-1)));
        }

        // connect newly opened site to neighbours
        private void connectSite(int row, int col) {
            
            // connect site above if open
            if(((row-1) >= 1) && isOpen(row-1, col)){
                uf.union(ufIdx(row, col), ufIdx(row-1, col));
            }

            // connect site below if open
            if(((row+1) <= dim) && isOpen(row+1, col)){
                uf.union(ufIdx(row, col), ufIdx(row+1, col));
            }

            // connect site to the left if open
            if(((col-1) >= 1) && isOpen(row, col-1)){
                uf.union(ufIdx(row, col), ufIdx(row, col-1));
            }

            // connect site to the right if open
            if(((col+1) <= dim) && isOpen(row, col+1)){
                uf.union(ufIdx(row, col), ufIdx(row, col+1));
            }
        }

        private int ufIdx(int row, int col){
            return (row-1)*dim+(col-1)+1;
        }

        private void idxCheck(int row, int col){
            if((row < 1)||(row>dim)||(col<1)||(col)>dim){
                throw new IllegalArgumentException();
            }
        }
}