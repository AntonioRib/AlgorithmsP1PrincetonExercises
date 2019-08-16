package Week48Puzzle;

import java.util.ArrayDeque;
import java.util.Deque;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	
	private Move lastMove;
	
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	MinPQ<Move> moves = new MinPQ<Move>();
    	moves.insert(new Move(initial));
    	
    	MinPQ<Move> twinMoves = new MinPQ<Move>();
    	twinMoves.insert(new Move(initial.twin()));
    	
    	while(true) {
    		lastMove = expand(moves);
    		Move lastMoveTwin = expand(twinMoves);
    		// If either the lastMove or the lastMoveTwin are not null
    		// It means they found a solution
    		if (lastMove != null || lastMoveTwin != null)
    			return;
    	}
    }
    
    private Move expand(MinPQ<Move> moves) {
    	// Returns the value if its goal
    	// Returns null when its empty or put more moves on the queue
    	if(moves.isEmpty())
    		return null;
    	Move nextMove = moves.delMin();
    	if(nextMove.board.isGoal())
    		return nextMove;
    	for(Board n : nextMove.board.neighbors()) {
    		if(nextMove.previous == null || !n.equals(nextMove.previous.board))
    			moves.insert(new Move(n, nextMove));
    	}
    	return null;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
		return (lastMove != null);
    	
    }

    // min number of moves to solve initial board
    public int moves() {
    	return isSolvable() ? lastMove.numMoves : -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
    	 if (!isSolvable()) 
    		 return null;
    	
    	Deque<Board> moves = new ArrayDeque<Board>();
		while(lastMove != null) {
			moves.push(lastMove.board);
			lastMove = lastMove.previous;
		}
		
		return moves;
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
    
    private class Move implements Comparable<Move> {
    	private Move previous;
    	private Board board;
    	private int numMoves;
    	
    	public Move(Board board) {
    		this.board = board;
    		this.previous = null;
    		this.numMoves = 0;
    	}
    	
    	public Move(Board board, Move previous) {
    		this.board = board;
    		this.previous = previous;
    		this.numMoves = previous.numMoves + 1;
    	}
    	
    	@Override
    	public int compareTo(Move o) {
    		return (this.board.manhattan() - o.board.manhattan()) + (this.numMoves - o.numMoves);
    	}
    }
}
