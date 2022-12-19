import edu.princeton.cs.algs4.StdOut;
public class QS {
    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, 0, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        while (true) {
            while (less(a, ++i, lo)) {  // find item on left to swap
                if (i == hi) break;
            }
            while (less(a, lo, --j)) { // find item on right to swap
                if (j == lo) break;
            }
            if (i >= j) { // check if pointers cross
                break;
            }
            exch(a, i, j);
        }
        exch(a, lo, j); // put the pivot item in place
        return j;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static boolean less(Comparable[] a, int i, int j) {
        return (a[i].compareTo(a[j]) < 0);
    }

    public static void main(String[] args) {
        Integer[] testA = { 5, 8, 7, 0, 3, 2, 4, 1 };
        int j;
        for (int i = 0; i < testA.length; i++) {
            StdOut.print(testA[i] + ", ");
        }
        StdOut.println("----------");
        // j = partition(testA, 0, testA.length);
        sort(testA);
        // StdOut.println("j = " + j);
        StdOut.println("----------");
        for (int i = 0; i < testA.length; i++) {
            StdOut.print(testA[i] + ", ");
        }
    }
}
