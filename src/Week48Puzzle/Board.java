package Week48Puzzle;

import java.util.LinkedList;

public class Board {

	private static final int SPACE = 0;
	private int[][] board;
	
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
    	board = tiles.clone();
    }
                                           
    // string representation of this board
    public String toString() {
		String res = this.dimension() + "\n";
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++)
				res += board[x][y] + " ";
			res += "\n";
		}
		return res;
    }    

    // board dimension n
    public int dimension() {
		return board.length;
    }

    // number of tiles out of place
    public int hamming() {
    	// If isnt 0 or the value that it should be count up
    	int count = 0;
		for (int x = 0; x < board.length; x++)
			for (int y = 0; y < board[x].length; y++)
				if(board[x][y] != SPACE && board[x][y] != ((x * board.length) + y + 1))
    				count++;
    	return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
    	// If isnt 0 or the value that it should be count up
    	int sum = 0;
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				int valueToAdd = 0;
				if(board[x][y] != SPACE)
					valueToAdd = Math.abs(x - ((x - 1) / dimension())) + Math.abs(y - ((y - 1) % dimension()));
				sum += valueToAdd;
			}
		}
    	return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
    	// If isnt 0 or the value that it should be return false
		for (int x = 0; x < board.length; x++)
			for (int y = 0; y < board[x].length; y++)
				if(board[x][y] != SPACE && board[x][y] != ((x * board.length) + y + 1))
    				return false;
    	return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
    	 if (y==this) return true;
         if (y==null || !(y instanceof Board) || ((Board)y).board.length != this.board.length) return false;
         for (int row = 0; row < board.length; row++)
             for (int col = 0; col < board.length; col++)
                 if (((Board) y).board[row][col] != this.board[row][col]) 
                	 return false;
         return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
		LinkedList<Board> neighbors = new LinkedList<Board>();
		int[] location = spaceLocation();
		
		//Check if the space is at any border and if it isnt create a new board with the swap
		if(location[0] > 0)
			neighbors.add(new Board(swap(location[0], location[1], location[0] - 1, location[1])));
		if(location[0] < this.dimension() - 1)
			neighbors.add(new Board(swap(location[0], location[1], location[0] + 1, location[1])));
		if(location[1] > 0)
			neighbors.add(new Board(swap(location[0], location[1], location[0], location[1] - 1)));
		if(location[1] < this.dimension() - 1)
			neighbors.add(new Board(swap(location[0], location[1], location[0], location[1] + 1)));
		
		return neighbors;
    }
    
    private int[] spaceLocation() {
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board.length; col++)
                if (board[row][col] == SPACE) {
                    int[] location = new int[2];
                    location[0] = row;
                    location[1] = col;
                    return location;
                }
        throw new RuntimeException();
    }
    
    private int[][] swap(int row1, int col1, int row2, int col2) {
    	// Clone only copies the 1st array and keeps the references
    	// So we also need to clone the references
        int[][] copy = board.clone();
        for(int i = 0; i < copy.length; i++)
        	copy[i] = board[i].clone();
        int tmp = copy[row1][col1];
        copy[row1][col1] = copy[row2][col2];
        copy[row2][col2] = tmp;

        return copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board.length - 1; col++)
                if (board[row][col] != SPACE && board[row][col + 1] != SPACE)
                    return new Board(swap(row, col, row, col + 1));
        throw new RuntimeException();
    }

    // unit testing (not graded)
    public static void main(String[] args) {
    	
    }
    
}
