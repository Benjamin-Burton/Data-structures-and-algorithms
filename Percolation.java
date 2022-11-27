import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // our grid
    // 0 = blocked
    // 1 = unblocked
    private boolean[][] grid;
    private int size;
    private int numOpenSites;

    // references to the top/bottom extra roots
    private int topRoot;
    private int bottomRoot;

    // our Dynammic Connectivity object
    private WeightedQuickUnionUF uf;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) { throw new IllegalArgumentException(); }
        grid = new boolean[n][n];
        size = n;
        numOpenSites = 0;
        for (int i = 0; i < n; i++) {
            boolean[] row = new boolean[n];
            for (int j = 0; j < n; j++) {
                row[j] = false;
            }
            grid[i] = row;
        }
        // n by n grid plus the two 'virtual' root nodes for the top and bottom row
        uf = new WeightedQuickUnionUF(n*n+2);
        
        // let's say the extra top root is n*n+2
        // the bottom is n*n+1
        topRoot = n*n+2 - 1;
        bottomRoot = n*n+1 - 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException("Row or Col must be > 0");
        }
        if (row > grid.length || col > grid.length) {
            throw new IllegalArgumentException();
        }
        if (grid[row-1][col-1]) {
            return;
        }
        grid[row-1][col-1] = true;
        numOpenSites += 1;

        // Handle opening in row 1 or final row - link to virtual site.
        if (row == 1) {
            uf.union(getIntFromRowCol(row, col), topRoot);
        }

        if (row == size) {
            uf.union(getIntFromRowCol(row, col), bottomRoot);
        }

        // open above
        if (row-1 > 0 && isOpen(row-1, col)) {
            uf.union(getIntFromRowCol(row, col), getIntFromRowCol(row-1, col));
        }
        // open below
        if (row + 1 <= size && isOpen(row+1, col)) {
            uf.union(getIntFromRowCol(row, col), getIntFromRowCol(row+1, col));
        }

        // open left
        if (col - 1 > 0 && isOpen(row, col-1)) {
            uf.union(getIntFromRowCol(row, col), getIntFromRowCol(row, col-1));
        }
        // open right
        if (col + 1 <= size && isOpen(row, col+1)) {
            uf.union(getIntFromRowCol(row, col), getIntFromRowCol(row, col+1));
        }
    }

    private int getIntFromRowCol(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }
        // reference starts at 1,1 - need to translate to starting at 0
        row -= 1;
        col -= 1;

        int res = row * size;
        res += col;
        return res;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException("Row or Col must be > 0");
        }
        if (row > grid.length || col > grid.length) {
            throw new IllegalArgumentException();
        }
        return grid[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException("Row or Col must be > 0");
        }
        if (row > grid.length || col > grid.length) {
            throw new IllegalArgumentException();
        }
        return uf.find(getIntFromRowCol(row, col)) == uf.find(topRoot);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(topRoot) == uf.find(bottomRoot);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}