import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class UFClient {
    public static void main(String[] args) {
        int N = StdIn.readInt();
        WeightedQuickUnion uf = new WeightedQuickUnion(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                StdOut.println(p + " " + q);
            }
            StdOut.println(uf.connected(1, 7));
        }

    }
}
