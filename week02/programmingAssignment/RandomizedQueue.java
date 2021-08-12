import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a = (Item[]) new Object[1];
    private int n;

    public RandomizedQueue() {

    }

    private void resize(int s) {
        Item[] copy = (Item[]) new Object[s];
        for (int i = 0; i < n; i++) {
            copy[i] = a[i];
        }
        a = copy;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n == a.length) {
            resize(2 * a.length);
        }
        a[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int idx = (int) (Math.random() * n);
        Item item = a[idx];
        a[idx] = a[n - 1];
        a[n - 1] = null;
        n--;
        if (n > 0 && n == a.length / 4) {
            resize(a.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int idx = (int) (Math.random() * n);
        Item item = a[idx];
        return item;
    }


    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Item[] aCopy = (Item[]) new Object[a.length];
        private int i = n;
        private ListIterator() {
            for (int j = 0; j < a.length; j++) {
                aCopy[j] = a[j];
            }
        }
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
        q.enqueue(8);
        q.enqueue(11);
        q.enqueue(48);
        q.enqueue(21);
        q.enqueue(37);
        q.enqueue(1);
    }
}
