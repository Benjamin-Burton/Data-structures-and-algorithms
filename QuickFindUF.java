public class QuickFindUF {
    // quick-find (eager approach)
    // note: union is too expensive - it takes N^2 array accesses to process a sequence
    // of N union commands on N objects
    // if we have 1000 entries, each time we must touch every other entry. this is 1000 x 1000

    // Interpretation: p and q are connected iff they have the same id
    private int[] id;

    public QuickFindUF(int N) {
        // instantiates the data structure of size N
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        // connects p and q
        for (int i = 0; i < count(); i++) {
            if (id[i] == pid) { id[i] = qid; };
        }
    }

    public boolean connected(int p, int q) {
        // are p and q in the same component?
        // in this structure, simply:
        return (id[p] == id[q]);
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
