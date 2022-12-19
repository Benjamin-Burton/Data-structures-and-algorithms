public class Heap {
    public static void sort(Comparable[] pq) {
        int N = pq.length;
        // construct as max heap
        for (int k = N/2; k > 0; k--) {
            sink(pq, k, N);
        }
        // in-place sort
        // get maximum from heap and put at end of array
        while (N > 0) {
            exch(pq, 1, N);
            sink(pq, 1, --N);
        }
    }

    private static void sink(Comparable[] pq, int k, int N) {
        while (2*k <= N) {
            int j = 2*k;
            if (j < N && less(pq, j, j+1)) j++;
            exch(pq, k, j);
            k = j;
        }
    }

    private static void exch(Comparable[] pq, int i, int j) {
        Comparable t = pq[i];
        pq[i] = pq[j];
        pq[j] = pq[i];
    }

    private boolean less(Comparable[] pq, int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }
}
