import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a = (Item[]) new Object[1];
    private int N;

    private void resize(int s) {
        Item[] copy = (Item[]) new Object[s];
        for (int i = 0; i < N; i++) {
            copy[i] = a[i];
        }
        a = copy;
    }

    public RandomizedQueue() {

    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (N == a.length) {
            resize(2 * a.length);
        }
        a[N] = item;
        N++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int idx = (int) (Math.random() * N);
        Item item = a[idx];
        a[idx] = a[N - 1];
        a[N - 1] = null;
        N--;
        if (N > 0 && N == a.length / 4) {
            resize(a.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int idx = (int) (Math.random() * N);
        Item item = a[idx];
        if (N > 0 && N == a.length / 4) {
            resize(a.length / 2);
        }
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int i = N;
        public boolean hasNext() {
            return i > 0;
        }
        public Item next() {
            if (i == 0) {
                throw new java.util.NoSuchElementException();
            }
            int idx = (int) (Math.random() * i);
            Item item = a[--i];
            a[i] = a[idx];
            a[idx] = item;
            return a[i];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        /*
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            r.enqueue(item);
        }
         */

        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        q.enqueue(6);
        q.enqueue(7);
        q.enqueue(8);
        q.enqueue(9);
        q.enqueue(10);
        //StdOut.println("s = " + q.sample());
        StdOut.println("d = " + q.dequeue());

        // Iterator work
        StdOut.print("N = " + q.N + " it = ");
        Iterator iterator = q.iterator();
        while (iterator.hasNext()) {
            StdOut.print(iterator.next() + " ");
        }
    }
}
