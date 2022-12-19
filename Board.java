import java.util.ArrayList;

public class Board {
    private int[][] tiles;
    private int sz;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        // can assume 2 <= n <= 128
        this.tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
        sz = tiles.length;
    }
                                           
    // string representation of this board
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(sz);
        out.append('\n');
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                out.append(tiles[i][j]);
                out.append(" ");
            }
            out.append('\n');
        }
        return out.toString();
    }

    // board dimension n
    public int dimension() {
        return sz;
    }

    // number of tiles out of place
    public int hamming() {
        int res = 0;
        int expectedNum = 0;
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                expectedNum++;
                int num = tiles[i][j];
                if (num == 0) { continue; }
                if (num != expectedNum) { res++; }
            }
        }
        return res;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int total = 0;
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                int num = tiles[i][j];
                // 0 isn't a tile - don't include its distance
                if (num == 0) { continue; }

                int expectedRow;
                int expectedCol;
                expectedCol = (num - 1) % sz;
                expectedRow = (num - 1) / sz;
                
                int manhattan = Math.abs(expectedRow - i);
                manhattan += Math.abs(expectedCol - j);
                total += manhattan;
            }
        }
        return total;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) { return false; }
        if (y.getClass() != this.getClass()) { return false; }
        Board my = (Board) y;
        if (sz != my.dimension()) { return false; }
        // check all tiles
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                if (tiles[i][j] != my.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // locate 0
        int zeroRow = 0;
        int zeroCol = 0;
        ArrayList<Board> neighbours = new ArrayList<>();

        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                if (tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }

        // copy the array so we can permute
        int[][] newTiles = new int[sz][sz];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }

        // check for north, south, east, west
        if (zeroRow > 0) {
            // north exists - create neighbour
            // permute array
            newTiles[zeroRow][zeroCol] = newTiles[zeroRow-1][zeroCol];
            newTiles[zeroRow-1][zeroCol] = 0;
            // create north neighbour
            neighbours.add(new Board(newTiles));
            // permute back
            newTiles[zeroRow-1][zeroCol] = newTiles[zeroRow][zeroCol];
            newTiles[zeroRow][zeroCol] = 0;
        }

        if (zeroRow != sz - 1) {
            // south exists - create neighbour
            // permute array
            newTiles[zeroRow][zeroCol] = newTiles[zeroRow+1][zeroCol];
            newTiles[zeroRow+1][zeroCol] = 0;
            // create neighbour
            neighbours.add(new Board(newTiles));
            
            // permute back
            newTiles[zeroRow+1][zeroCol] = newTiles[zeroRow][zeroCol];
            newTiles[zeroRow][zeroCol] = 0;
        }

        if (zeroCol > 0) {
            // west exists - create neighbour
            // permute array
            newTiles[zeroRow][zeroCol] = newTiles[zeroRow][zeroCol-1];
            newTiles[zeroRow][zeroCol-1] = 0;
            // create eighbour
            neighbours.add(new Board(newTiles));
            // permute back
            newTiles[zeroRow][zeroCol-1] = newTiles[zeroRow][zeroCol];
            newTiles[zeroRow][zeroCol] = 0;
        }

        if (zeroCol != sz - 1) {
            // east exists - create neighbour
            // permute array
            newTiles[zeroRow][zeroCol] = newTiles[zeroRow][zeroCol+1];
            newTiles[zeroRow][zeroCol+1] = 0;
            // create neighbour
            neighbours.add(new Board(newTiles));
            // permute back
            newTiles[zeroRow][zeroCol+1] = newTiles[zeroRow][zeroCol];
            newTiles[zeroRow][zeroCol] = 0;
        }
        return neighbours;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] newTiles = new int[sz][sz];
        // makes copy of current state
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        // check for zeroes
        if (newTiles[0][0] == 0 || newTiles[0][1] == 0) {
            // found a zero in first two squares, permute somewhere else
            int temp = tiles[1][1];
            newTiles[1][1] = newTiles[1][0];
            newTiles[1][0] = temp;
            return new Board(newTiles);
        }
        // no zeroes - permute first squares
        int temp = tiles[0][1];
        newTiles[0][1] = newTiles[0][0];
        newTiles[0][0] = temp;
        return new Board(newTiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // constructor test
        int[][] testArr = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                testArr[i][j] = i*3 + j;
            }
        }

        // positive equality test
        Board test2 = new Board(testArr);
        Board test = new Board(testArr);

        System.out.print(test.toString());
        System.out.println(Boolean.toString(test.equals(test2)));

        // negative equality test (one permuted number)

        testArr[2][2] = 5;
        Board test3 = new Board(testArr);
        System.out.println(Boolean.toString(test.equals(test3)));

        // hamming/manhattan test - expected result 5.

        int[] row1 = { 8, 1, 3 };
        int[] row2 = { 4, 0, 2 };
        int[] row3 = { 7, 6, 5 };

        int[][] hamArr = new int[3][3];
        hamArr[0] = row1;
        hamArr[1] = row2;
        hamArr[2] = row3;

        Board hamtest = new Board(hamArr);
        System.out.println(hamtest.hamming());
        System.out.println(hamtest.manhattan());

        // test for neighbours
        System.out.println("------------------------");
        System.out.println(test.toString());
        Iterable<Board> neighs = test.neighbors();
        for (Board b : neighs) {
            System.out.println(b.toString());
        }

        System.out.println("------------------------");
        System.out.println(hamtest.toString());
        Iterable<Board> neighs2 = hamtest.neighbors();
        for (Board b : neighs2) {
            System.out.println(b.toString());
        }

        int[] trow1 = { 1, 2, 3 };
        int[] trow2 = { 4, 5, 6 };
        int[] trow3 = { 7, 8, 0 };

        int[][] thamArr = new int[3][3];
        thamArr[0] = trow1;
        thamArr[1] = trow2;
        thamArr[2] = trow3;

        Board thamtest = new Board(thamArr);

        System.out.println("-----> " + thamtest.hamming());
    }
}