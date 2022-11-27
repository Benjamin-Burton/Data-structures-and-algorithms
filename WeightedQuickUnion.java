public class WeightedQuickUnion {
    // quick-union (lazy approach)
    // union was too expensive - now we want to speed it up. 
    // we can do this by way of a new data structure interpretation - a tree.

    // Interpretation if id[]: id[i] references the parent of index of i.
    // Root of i is id[id[id[id[...]]]] until it doesn't change. (is its own parent.)
    // Interpretation of sz
    // This keeps track of the NUMBER OF OBJECTS in each component.

    // this is also too slow in the worst case - because the trees can get really tall,
    // find is too expensive

    // This is the first improvement:
    // We track the depth of the trees on union, and keep them flat by attached the smaller one
    // to the root of the bigger one

    // algorithm initialize union find
    // quick-find N N 1
    // quick-union N N † N
    // weighted-QU N lg(N) lg(N)

    // This improves both union and find to log(N), which is an incredible improvement.
    // But we can improve further!
    // We can use path compression as well
    // "just after computing the root of p, set the id of each examined node to point to that root"

    // this has fascinating mathematical properties
    // in principle (theory), it is not a linear operation, but in practice, it is linear

    // Fredman-saks has proven (how??) that *no* linear time implementation exists. 


    private int[] id;
    private int[] sz;

    public WeightedQuickUnion(int N) {
        // instantiates the data structure of size N
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    // set the ID of p's root to the id of Q's root
    // modified to:
    // link the root of the smaller tree to the bigger one
    // update the sz[] array
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) return;

        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i]; 
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }

    public boolean connected(int p, int q) {
        // are p and q in the same component?
        // in this structure, do they have the same root?
        return (root(p) == root(q));
    }

    private int root(int i) {
        while (id[i] != i) {
            id[i] = id[id[i]]; // this is the implementation of path compression, keeping the tree very flat
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
