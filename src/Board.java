import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;

public class Board {

    private int[][] tiles;
    private int dim;
    private int blankRow;
    private int blankCol;
    private int manhattan;
    private int hamming;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        dim = tiles.length;
        this.tiles = new int[dim][dim];

        for(int row = 0; row < dim; row++){
            for(int col = 0; col < dim; col++){
                this.tiles[row][col] = tiles[row][col];
                if(tiles[row][col] == 0){
                    blankRow = row;
                    blankCol = col;
                }
            }
        }

        this.hamming = calcHamming();
        this.manhattan = calcManhattan();
    }
                                           
    // string representation of this board
    public String toString(){
        String boardString = Integer.toString(dim) + "\n";
        
        for(int[] row : tiles){
            for(int tile : row){
                boardString += Integer.toString(tile) + " ";
            }
            boardString += "\n";
        }

        return boardString;
    }

    // board dimension n
    public int dimension(){
        return dim;
    }

    // number of tiles out of place
    public int hamming(){
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return hamming == 0;
    }

    // does this board equal y?
    public boolean equals(Object y){

        if(y == this){
            return true;
        } else if(y == null) {
            return false;
        } else if(y.getClass() != this.getClass()){
            return false;
        }

        Board that = (Board) y;
        if(that.dimension() != this.dimension()){
            return false;
        } else {
            return Arrays.deepEquals(this.tiles, that.tiles);
        }
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){

        Stack<Board> neighbors = new Stack<>();
        if((blankRow - 1) >= 0){
            Board up = new Board(swapTiles(blankRow, blankCol, blankRow - 1, blankCol));
            neighbors.push(up);
        }
        if((blankRow + 1) <= (dim - 1)){
            Board down = new Board(swapTiles(blankRow, blankCol, blankRow + 1, blankCol));
            neighbors.push(down);
        }
        if((blankCol - 1) >= 0){
            Board left = new Board(swapTiles(blankRow, blankCol, blankRow, blankCol - 1));
            neighbors.push(left);
        }
        if((blankCol + 1) <= (dim - 1)){
            Board right = new Board(swapTiles(blankRow, blankCol, blankRow, blankCol + 1));
            neighbors.push(right);
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        
        // just exchange first two elements in a row not containting the blank tile
        int sourceRow = (blankRow + 1) % dim;
        Board twinBoard = new Board(swapTiles(sourceRow, 0, sourceRow, 1));

        return twinBoard;
    }
    
    private int[][] swapTiles(int sourceRow, int sourceCol, int destRow, int destCol) {
        int[][] newTiles = new int[dim][dim];
        
        for(int i = 0; i<dim; i++){
            for(int j = 0; j<dim; j++){
                newTiles[i][j] = tiles[i][j];
            }
        }
        
        newTiles[destRow][destCol] = tiles[sourceRow][sourceCol];
        newTiles[sourceRow][sourceCol] = tiles[destRow][destCol];
        
        return newTiles;
    }

    // calculate hamming distance
    private int calcHamming() {
        int hammingDistance = 0;
        
        for(int row = 0; row < dim; row++){
            for(int col = 0; col < dim; col++){
                if((row == blankRow) && (col == blankCol)){
                    continue;
                }

                if(this.tiles[row][col] != (row*dim + col+1)){
                        hammingDistance++;
                }
            }
        }

        return hammingDistance;
    }

    // calculate manhattan distance
    private int calcManhattan() {
        int manhattanDistance = 0;
        int targetRow = 0;
        int targetCol = 0;

        for(int row = 0; row < dim; row++){
            for(int col = 0; col < dim; col++){
                if((row == blankRow) && (col == blankCol)){
                    continue;
                }

                targetRow = (this.tiles[row][col] - 1)/dim;
                targetCol = (this.tiles[row][col] - 1) - dim*targetRow;
                manhattanDistance += Math.abs(row-targetRow) + 
                    Math.abs(col-targetCol);      
            }
        }

        return manhattanDistance;           
    }

    // unit testing (not graded)
    public static void main(String[] args){
        int[][] tiles1 = new int[2][2];
        tiles1[0][0] = 0;
        tiles1[0][1] = 2;
        tiles1[1][0] = 1;
        tiles1[1][1] = 3;

        Board board1 = new Board(tiles1);
        System.out.print(board1.toString());
        System.out.printf("hamming distance %d \n", board1.hamming());
        System.out.printf("manhattan distance %d \n", board1.manhattan());
        System.out.printf("solved? : %s \n", board1.isGoal());
        System.out.println("==================================================");

        int[][] tiles2 = new int[2][2];
        tiles2[0][0] = 1;
        tiles2[0][1] = 2;
        tiles2[1][0] = 3;
        tiles2[1][1] = 0;

        Board board2 = new Board(tiles2);
        System.out.print(board2.toString());
        System.out.printf("hamming distance %d \n",board2.hamming());
        System.out.printf("manhattan distance %d \n",board2.manhattan());
        System.out.printf("solved? : %s \n", board2.isGoal());
        System.out.println("==================================================");


        Board board3 = new Board(tiles2);
        System.out.println(board1.equals(board2));
        System.out.println(board2.equals(board3));
        System.out.println("==================================================");

        Iterable<Board> neighbors = board3.neighbors();
        for(Board board : neighbors){
            System.out.print(board.toString());
        }
        System.out.println("==================================================");

        System.out.println(board1.twin().toString());
    }

}