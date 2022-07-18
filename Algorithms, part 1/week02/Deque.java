import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n;

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
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (n > 0) {
            oldFirst.prev = first;
        }
        else if (isEmpty()) {
            last = first;
        }
        n++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        if (n > 0) {
            oldLast.next = last;
        }
        else if (isEmpty()) {
            first = last;
        }
        n++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = first.item;
        if (n == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        n--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = last.item;
        if (n == 1) {
            last = null;
            first = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        n--;
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
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);
        StdOut.println("rl = " + d.removeLast());
        d.addLast(4);
    }
}
