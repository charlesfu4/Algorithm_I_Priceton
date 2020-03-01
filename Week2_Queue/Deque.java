/* *****************************************************************************
 *  Name: Chu-Cheng Fu
 *  Date: 29-02-2020
 *  Description: Queue algorithm assignment - Deque
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Item[] deq;
    private int N = 0;
    private int head;
    private int tail;

    // construct an empty deque
    public Deque() {
        deq = (Item[]) new Object[4];
        head = 2;
        tail = 2;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // add the item to the front
    public void addFirst(Item item) {
        //corner case
        if (item == null)
            throw new IllegalArgumentException();
        if (head <= 1)
            expand(deq.length * 2);

        if (deq[head] == null)
            deq[head] = item;
        else {
            head--;
            deq[head] = item;
        }
        N++;

    }

    // add the item to the back
    public void addLast(Item item) {
        //corner case
        if (item == null)
            throw new IllegalArgumentException();
        if (tail >= deq.length - 1)
            expand(deq.length * 2);

        if (deq[tail] != null) {
            tail++;
            deq[tail] = item;
        }
        else
            deq[tail] = item;
        N++;
    }

    //expand the array
    private void expand(int cap) {
        if (cap <= 0)
            throw new IllegalArgumentException();
        Item[] copy = (Item[]) new Object[cap];
        for (int i = 0; i < N; i++) {
            copy[cap / 4 + i] = deq[head + i];
        }
        head = cap / 4;

        if (N >= 1)
            tail = cap / 4 + N - 1;
        else
            tail = head;
        deq = copy;

    }

    // shrink the array
    private void shrink(int cap) {
        if (cap <= 0)
            throw new IllegalArgumentException();
        Item[] copy = (Item[]) new Object[cap];
        for (int i = 0; i < N; i++) {
            copy[cap / 4 + i] = deq[head + i];
        }
        head = cap / 4;

        if (N >= 1)
            tail = cap / 4 + N - 1;
        else
            tail = head;
        deq = copy;

    }


    // remove and return the item from the front
    public Item removeFirst() {
        //corner case
        if (deq[head] == null)
            throw new NoSuchElementException();

        if ((deq.length >= 8) && (N <= deq.length / 4))
            shrink(deq.length / 2);
        N--;
        Item temp = deq[head];
        if (head == deq.length - 1) {
            deq[head] = null;
            return temp;
        }
        else {
            if (deq[head + 1] != null) {
                deq[head] = null;
                head++;
                return temp;
            }
            else {
                deq[head] = null;
                return temp;
            }
        }

    }


    // remove and return the item from the back
    public Item removeLast() {
        //corner case
        if (deq[tail] == null)
            throw new NoSuchElementException();
        if ((deq.length >= 8) && (N <= deq.length / 4))
            shrink(deq.length / 2);
        N--;
        Item temp = deq[tail];
        if (tail == 0) {
            deq[tail] = null;
            return temp;

        }
        else {
            if (deq[tail - 1] != null) {
                deq[tail] = null;
                tail--;
                return temp;
            }
            else {
                deq[tail] = null;
                return temp;
            }
        }


    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {

        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private int start = head;

        public boolean hasNext() {
            return (start <= tail) && (N > 0);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (deq[start] == null)
                throw new NoSuchElementException();
            return deq[start++];
        }


    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        StdOut.println("AddFirst: ");
        d.addFirst(StdIn.readInt());
        StdOut.println("Removing the last element: " + d.removeLast());



        StdOut.println("The size of deque is: " + d.size());
        StdOut.println("The deque is empty? " + d.isEmpty());
        StdOut.println("AddFirst: ");
        d.addFirst(StdIn.readInt());
        StdOut.println("The size of deque is: " + d.size());
        StdOut.println("The deque is empty? " + d.isEmpty());
        StdOut.println("Removing the last element: " + d.removeLast());
        StdOut.println("AddFirst: ");

        StdOut.println("AddLast: ");
        d.addLast(StdIn.readInt());
        StdOut.println("The size of deque is: " + d.size());
        StdOut.println("The deque is empty? " + d.isEmpty());


        StdOut.println("Removing the first element: " + d.removeFirst());

        StdOut.println("Iterate through elements from head to tail:");

        Iterator<Integer> i = d.iterator();
        while (i.hasNext()) {
            StdOut.println(i.next());
        }




    }

}
