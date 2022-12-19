public class MaxPQ<Key extends Comparable<Key>> {
    // implementation of a priority queue with max as root
    // uses the binary heap data structure
    // do not use position 0
    // we are assuming, currently, that keys can't change while on the PQ
    // best practice: use keys that are immutable
    // an immutable implementation could use defensive copying

    // this ensures insert takes 1 + log n compares and del max takes 2 log n time (worst case)

    private Key[] pq;
    private int N;
    // parent = k/2
    // children = k*2 and k*2 + 1
    
    public MaxPQ(int capacity) {
        // +1 because our array starts at 1 for easier operations
        pq = (Key[]) new Comparable[capacity+1];
        N = 0;
    }

    public Boolean isEmpty() {
        return (N == 0);
    }

    public void insert(Key k) {
        if (k == null) { throw new IllegalArgumentException(); }
        pq[++N] = k;
        swim(N);
    }

    public void delMax() {  
        if (isEmpty()) { throw new IllegalArgumentException(); }
        exch(N--, 1);
        sink(1);
    }

    // helpers
    private void swim(int k) {
        while (k > 1 && less(k, k/2)) {
                exch(k, k/2);
                k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= N) {
            int j = 2*k;
            if (j < N && less(j, j+1)) j++;
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key t = pq[j];
        pq[j] = pq[i];
        pq[i] = t;
    }
}