import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int N;

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    public Deque() {
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (N > 0) {
            oldFirst.prev = first;
        }
        else if (isEmpty()) {
            last = first;
        }
        N++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        if (N > 0) {
            oldLast.next = last;
        }
        else if (isEmpty()) {
            first = last;
        }
        N++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = first.item;
        if (N == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        N--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = last.item;
        if (N == 1) {
            last = null;
            first = null;
        }
        else {
            //StdOut.println("rlast = " + last.prev.item);
            last = last.prev;
            last.next = null;
        }
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        /*
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            //StdOut.println("str = " + item);
            d.addLast(item);
        }
        */
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);
        StdOut.println("rl = " + d.removeLast());
        d.addLast(4);

        //StdOut.println("rl = " + d.removeFirst());
        //StdOut.println("rl = " + d.removeLast());
        //StdOut.println("N = " + d.N);

        // Iterator work
        StdOut.print("it = ");
        Iterator iterator = d.iterator();
        while (iterator.hasNext()) {
            StdOut.print(iterator.next() + " ");
        }
    }
}
