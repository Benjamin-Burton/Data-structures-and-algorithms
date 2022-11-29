import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;
    private int n = 0; // number of items in queue
    private int capacity = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[1];
        capacity = 1;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        // handle overflow
        if (n >= capacity) {
            // double the size of the array
            Item[] newq = (Item[]) new Object[capacity * 2];
            capacity = capacity * 2;

            // copy it across
            for (int i = 0; i < n; i++) {
                newq[i] = q[i];
            }

            q = newq;
        }

        // handle adding to queue
        q[n] = item;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) { throw new NoSuchElementException(); }

        // handle one object in queue
        if (n == 1) {
            Item res = q[0];
            n--;
            q[0] = null;
            return res;
        }

        // TODO fix this bit
        int idx = StdRandom.uniformInt(0, n);
        Item res = q[idx];

        // swap idx and last element
        Item oldLast = q[n-1];
        q[n-1] = q[idx];
        q[idx] = oldLast;

        // delete last element
        q[n-1] = null;
        n--;
        // resizing operation - check
        if (n < capacity / 4) {
            Item[] newq = (Item[]) new Object[capacity/2];
            // copy across
            for (int i = 0; i < n; i++) {
                newq[i] = q[i];
            }
            capacity = capacity / 2;
            q = newq;
        }

        return res;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) { throw new NoSuchElementException(); }
        if (n == 1) { return q[0]; }
        return q[StdRandom.uniformInt(0, n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int current = 0;
        private int size = n;
        
        private int[] set;

        public RandomIterator() {
            set = new int[size];
            // create set of indexes and randomize then
            // copy them across
            for (int i = 0; i < n; i++) {
                set[i] = i;
            }
            // shuffle the array
            StdRandom.shuffle(set);
        }

        public boolean hasNext() { return current != size - 1; }
        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            Item res = q[set[current]];
            current++;
            return res;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        StdOut.println(q.size());
        q.enqueue(1);
        for (int i = 0; i < 200; i++) {
            q.enqueue(i);
        }
        // Iterator<Integer> i = q.iterator();
        // while (i.hasNext()) {
        //     StdOut.println(i.next());
        // }
        for (int j = 0; j < 200; j++) {
            StdOut.println(q.dequeue());
        }
        
    }
}