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
        private Item[] aCopy = a;
        private int i = N;
        public boolean hasNext() {
            return i > 0;
        }
        public Item next() {
            if (i == 0) {
                throw new java.util.NoSuchElementException();
            }
            int idx = (int) (Math.random() * i);
            Item item = aCopy[--i];
            aCopy[i] =  aCopy[idx];
            aCopy[idx] = item;
            return aCopy[i];
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

        q.enqueue(8);
        q.enqueue(11);
        q.enqueue(48);
        q.enqueue(21);
        q.enqueue(37);
        q.enqueue(1);
        //StdOut.println("s = " + q.sample());
        //StdOut.println("d = " + q.dequeue());

        // Iterator work
        StdOut.print("it = ");
        Iterator iterator = q.iterator();
        while (iterator.hasNext()) {
            StdOut.print(iterator.next() + " ");
        }
    }
}
