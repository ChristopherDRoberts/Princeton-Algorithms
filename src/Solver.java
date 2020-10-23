import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
 
public class Solver {

    private SearchNode node;
    private boolean solved = false;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if(initial == null){
            throw new IllegalArgumentException("Null initial board");
        }

        Board twin = initial.twin();
        Boolean twinSolved = false;

        SearchNode twinNode = new SearchNode(twin, 0, null);
        node = new SearchNode(initial, 0, null);

        if(node.board.isGoal()){
            solved = true;
        } else if(twinNode.board.isGoal()){
            twinSolved = true;
        } else {
            MinPQ<SearchNode> pq = new MinPQ<>();
            MinPQ<SearchNode> twinPq = new MinPQ<>();
            
            while(!(solved || twinSolved)){
                insertNeighbors(pq, node);
                insertNeighbors(twinPq, twinNode);
                node = pq.delMin();
                twinNode = twinPq.delMin();
                
                if(node.board.isGoal()){
                    solved = true;
                } else if(twinNode.board.isGoal()){
                    twinSolved = true;
                }
                
            }

        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return solved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        if(isSolvable()){
            return node.moves;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        
        if(isSolvable()){
            Stack<Board> solution = new Stack<>();
            SearchNode tmpNode = node;
            
            while(tmpNode != null){
                solution.push(tmpNode.board);
                tmpNode = tmpNode.previousNode;
            }

            return solution;
        } else {
            return null;
        }
    }

    // search node inner class
    private class SearchNode implements Comparable<SearchNode> {

        private Board board;
        private int moves;
        private int manhattanPriority;
        private int manhattanDistance;
        private SearchNode previousNode;

        public SearchNode(Board board, int moves, SearchNode previousNode) {
            this.board = board;
            this.moves = moves;
            this.manhattanDistance = board.manhattan();
            this.manhattanPriority = this.manhattanDistance + this.moves;
            this.previousNode = previousNode;
        }

        public int compareTo(SearchNode that) {
            
            if(this.manhattanPriority < that.manhattanPriority){
                return -1;
            } else if (this.manhattanPriority > that.manhattanPriority){
                return 1;
            } else {
                if(this.manhattanDistance < that.manhattanDistance){
                    return -1;
                } else if (this.manhattanDistance > that.manhattanDistance){
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    private void insertNeighbors(MinPQ<SearchNode> pq, SearchNode node) {
        for(Board board : node.board.neighbors()){
            if((node.previousNode != null) && board.equals(node.previousNode.board)){
                continue;
            } else {
                pq.insert(new SearchNode(board, node.moves + 1, node));
            }
        }
    }

    // test client (see below) 
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}