/* *****************************************************************************
 *  Name: Chu-Cheng Fu
 *  Date: 29-02-2020
 *  Description: Queue algorithm assignment - RandomizedQueue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rq;
    private int N = 0;
    private int idx;

    // construct an empty randomized queue
    public RandomizedQueue() {
        rq = (Item[]) new Object[4];
        idx = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (idx >= rq.length - 1)
            resize(rq.length * 2);
        if (rq[idx] == null)
            rq[idx] = item;
        else
            rq[++idx] = item;
        N++;
    }

    private void resize(int cap) {
        if (cap <= 0)
            throw new IllegalArgumentException();
        Item[] copy = (Item[]) new Object[cap];
        for (int i = 0; i < N; i++) {
            copy[i] = rq[i];
        }
        rq = copy;
    }


    // remove and return a random item
    public Item dequeue() {
        if (N <= 0)
            throw new NoSuchElementException();
        int ridx = StdRandom.uniform(N);
        if (rq[ridx] == null)
            throw new NoSuchElementException();
        if (idx <= rq.length / 4)
            resize(rq.length / 2);
        N--;
        Item temp = rq[ridx];
        // replace the vacancy
        if (ridx != idx) {
            rq[ridx] = rq[idx];
            rq[idx] = null;
            if (idx > 0)
                idx--;
        }

        else {
            rq[idx] = null;
            if (idx > 0)
                idx--;
        }
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (N <= 0)
            throw new NoSuchElementException();
        int ridx = StdRandom.uniform(N);
        return rq[ridx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int[] walklist = StdRandom.permutation(N);
        private int pidx = 0;

        public boolean hasNext() {
            return pidx < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if(N <= 0 || pidx >= N)
                throw new NoSuchElementException();
            return rq[walklist[pidx++]];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<Integer> r = new RandomizedQueue<>();
        StdOut.println("Enqueue: ");
        r.enqueue(StdIn.readInt());
        StdOut.println("The randomizedqueue is empty? " + r.isEmpty());
        StdOut.println("The size of randomizedqueue is: " + r.size());



        StdOut.println("Iterate through elements randomly:");
        Iterator<Integer> i = r.iterator();
        while (i.hasNext()) {
            StdOut.println(i.next());
        }
        StdOut.println("sample= " + r.sample());
        StdOut.println("Dequeue: " + r.dequeue());
        StdOut.println("The size of randomizedqueue is: " + r.size());



    }

}
