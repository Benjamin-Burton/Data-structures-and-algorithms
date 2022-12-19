import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private SearchNode solution;

    // find a solution to the initial board (using the A* algorithm)
    // we run TWO searches, to find out about the board's solvability
    // these must run in lockstep
    public Solver(Board initial) {
        if (initial == null) { throw new IllegalArgumentException(); }

        SearchNode init = new SearchNode(initial, 0, null);
        SearchNode initTwin = new SearchNode(initial.twin(), 0, null);

        MinPQ<SearchNode> solTree = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinSolTree = new MinPQ<SearchNode>();

        solTree.insert(init);
        twinSolTree.insert(initTwin);

        while (true) {
            SearchNode min = solTree.delMin();
            SearchNode twinMin = twinSolTree.delMin();
            if (min.board.isGoal()) {
                this.solution = min;
                break;
            }
            if (twinMin.board.isGoal()) {
                // twin board solved - actual board has no solution
                this.solution = null;
                break;
            }
            Iterable<Board> neighbors = min.board.neighbors();
            Iterable<Board> twinNeighbors = twinMin.board.neighbors();
            for (Board b : neighbors) {
                if (min.previous != null && b.equals(min.previous.board)) { continue; }
                solTree.insert(new SearchNode(b, min.numMoves + 1, min));
            }
            for (Board b : twinNeighbors) {
                if (twinMin.previous != null && b.equals(twinMin.previous.board)) { continue; }
                twinSolTree.insert(new SearchNode(b, twinMin.numMoves + 1, twinMin));
            }
        }   
    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int numMoves;
        private SearchNode previous;
        // we compute these once here so they aren't recomputed during comparisons
        private int manhattan;
        private SearchNode(Board board, int numMoves, SearchNode previous) {
            this.board = board;
            this.numMoves = numMoves;
            this.previous = previous;
            this.manhattan = board.manhattan();
        }

        public int compareTo(SearchNode that) {
            if ((this.manhattan + this.numMoves) < (that.manhattan + that.numMoves)) {
                return -1;
            } else if ((this.manhattan + this.numMoves) > (that.manhattan + that.numMoves)) {
                return 1;
            }
            return 0;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return !(solution == null);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (solution == null) { return -1; }
        return solution.numMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solution == null) return null;
        ArrayList<Board> sols = new ArrayList<>();
        SearchNode recurse = solution;
        while (recurse != null) {
            sols.add(0, recurse.board);
            recurse = recurse.previous;
        }
        return sols;
    }

    // test client (see below) 
    public static void main(String[] args) {

        int[] row1 = { 1, 2, 3 };
        int[] row2 = { 4, 5, 6 };
        int[] row3 = { 7, 8, 0 };

        int[][] testArr = { row1, row2, row3 };

        Board testBoard = new Board(testArr);

        Solver solver = new Solver(testBoard);
            // create initial board from file
        // In in = new In(args[0]);
        // int n = in.readInt();
        // int[][] tiles = new int[n][n];
        // for (int i = 0; i < n; i++)
        //     for (int j = 0; j < n; j++)
        //         tiles[i][j] = in.readInt();
        // Board initial = new Board(tiles);

        // // solve the puzzle
        // Solver solver = new Solver(initial);

        // // print solution to standard output
        // if (!solver.isSolvable())
        //     StdOut.println("No solution possible");
        // else {
        //     StdOut.println("Minimum number of moves = " + solver.moves());
        //     for (Board board : solver.solution())
        //         StdOut.println(board);
        // }
        StdOut.println(solver.isSolvable());
        // StdOut.println(solver.moves());
        // Iterable<Board> solPath = solver.solution();
        // for (Board b : solPath) {
        //     StdOut.println(b.toString());
        // }
    }
}