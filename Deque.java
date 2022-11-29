import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

/**
 * An implementation of a deque (double-ended queue) as per assignment requirements
 * We will try using a linked list with an inner class for nodes
 * Notes:
 * 1. inner Node class for implementation if LL
 * 2. we do not accept null values (this is how we test for emptiness)
 * Performance requirements:
 * 
 */

public class Deque<Item> implements Iterable<Item> {

    private int n;      // number of elements in the deque
    private Node first; // the first item on the deque
    private Node last;  // the last item on the deque
    
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    // construct an empty deque
    public Deque() {
        n = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (first == null);
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }

        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.next = first;
        newFirst.previous = null;
        first = newFirst;
        n++;

        // handle n = 1
        if (n == 1) {
            last = first;
        }

        if (n >= 2) {
            first.next.previous = first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        
        if (n == 0) {
            addFirst(item);
            return;
        }

        Node newLast = new Node();
        newLast.item = item;
        newLast.next = null;

        if (n == 1) {
            last = newLast;
            first.next = last;
            last.previous = first;
            n++;
            return;
        }

        // n > 1
        last.next = newLast;
        newLast.previous = last;
        last = newLast;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) { throw new NoSuchElementException(); }

        // handle if last item
        if (n == 1) {
            Item res = first.item;
            first = null;
            last = null;
            n--;
            return res;
        }

        Item firstItem = first.item;
        first = first.next;
        first.previous = null;
        n--;

        // handle if it was the last item
        // now the deque is empty
        return firstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        
        if (n == 1) {
            Item res = first.item;
            first = null;
            last = null;
            n--;
            return res;
        }

        Item lastItem = last.item;
        last = last.previous;
        last.next = null;
        n--;

        return lastItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    private class LinkedIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        StdOut.println("TESTS");
        Deque<String> deque = new Deque<>();
        StdOut.println(deque.isEmpty());
        deque.addLast("Butt");
        StdOut.println(deque.isEmpty());
        StdOut.println(deque.size());
        deque.addLast("Tree");
        deque.addLast("King");
        deque.addLast("Queen");
        StdOut.println(deque.isEmpty());
        StdOut.println(deque.size());
        String firstOut = deque.removeFirst();
        StdOut.println(firstOut);
        deque.removeFirst();
        deque.removeFirst();
        StdOut.println(deque.size());
        String secondOut = deque.removeFirst();
        StdOut.println(secondOut);
        StdOut.println(deque.isEmpty());
        StdOut.println(deque.size());

        Deque<Integer> deque2 = new Deque<>();
        for (int i = 0; i < 10; i++) {
            deque2.addFirst(i);
        }

        StdOut.println(deque2.size());
        for (int num : deque2) {
            StdOut.println(num);
        }

        for (int i = 0; i < 10; i++) {
            int y = deque2.removeFirst();
            StdOut.println(Boolean.toString(deque2.isEmpty()) + ' ' + y);
        }

        deque2 = new Deque<>();
        deque2.addFirst(5);
        deque2.addFirst(6);
        Iterator<Integer> i = deque2.iterator();
        deque2.removeLast();
        i = deque2.iterator();
        deque2.addLast(7);
        i = deque2.iterator();
        deque2.removeFirst();
        i = deque2.iterator();
        i.remove();
    }
}