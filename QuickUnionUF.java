public class QuickUnionUF {
    // quick-union (lazy approach)
    // union was too expensive - now we want to speed it up. 
    // we can do this by way of a new data structure interpretation - a tree.

    // Interpretation if id[]: id[i] references the parent of index of i.
    // Root of i is id[id[id[id[...]]]] until it doesn't change. (is its own parent.)

    // this is also too slow in the worst case - because the trees can get really tall,
    // find is too expensive

    // algorithm initialize union find
    // quick-find N N 1
    // quick-union N N † N

    private int[] id;

    public QuickUnionUF(int N) {
        // instantiates the data structure of size N
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    // set the ID of p's root to the id of Q's root
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        id[i] = j;
    }

    public boolean connected(int p, int q) {
        // are p and q in the same component?
        // in this structure, do they have the same root?
        return (root(p) == root(q));
    }

    private int root(int i) {
        while (id[i] != i) {
            i = id[i];
        }
        return i;
    }

    public int find(int p) {
        return id[p];
        // component identifier for p (0 to N - 1)
    }

    public int count() {
        // return number of components
        return id.length;
    }
}
