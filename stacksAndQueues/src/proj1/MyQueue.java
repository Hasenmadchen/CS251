package proj1;

/**
 * proj1.MyQueue.java
 * <p>
 * Project 1, part 1. Queue use link-list.
 */

public class MyQueue<E> {

    private Node first;
    private Node last;
    private int count;

    class Node {
        private E e;
        private Node next;
    }

    /**
     * Creates an empty queue and initializes variables.
     */
    public MyQueue() {
        count = 0;
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return the number pf elements in the stack
     */
    public int size() {
        return count;
    }

    /**
     * Returns {@code true} if this queue contains no elements.
     *
     * @return {@code true} if this queue contains no elements; {@code false} otherwise
     */
    public boolean isEmpty() {
        return (count == 0);
    }

    /**
     * Adds the element to this queue.
     *
     * @param e the element to add
     */
    public void enqueue(E e) {
        Node node = new Node();
        node.e = e;
        if (last != null) {
            last.next = node;
        } else {
            first = node;
        }
        last = node;
        count++;
    }

    /**
     * Removes and returns the element on this queue that was least recently added.
     *
     * @return the element on this queue that was least recently added, or {@code null}
     * if this queue is empty
     */
    public E dequeue() {
        if (isEmpty()) {
            return null;
        }
        Node node = first;
        first = first.next;
        if (first == null) {
            last = null;
            assert count == 1;
        }
        count--;
        return node.e;
    }

    E getLast() {
        if (isEmpty()) {
            return null;
        }
        return last.e;
    }

    E dequeueLast() {
        if (isEmpty()) {
            return null;
        }
        if (first.next == null) {
            return dequeue();
        } else if (first.next.next == null) {
            Node placeholder = last;
            first.next = null;
            last = first;
            count--;
            return placeholder.e;
        }
        Node node = first;
        while (node.next != null && node.next.next != null) {
            node = node.next;
        }
        last = node;
        Node placeholder = last.next;
        last.next = null;
        count--;
        return placeholder.e;
    }

    /**
     * Returns the item least recently added to this queue.
     *
     * @return the item least recently added to this queue, or {@code null} if
     * this queue is empty
     */
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return first.e;
    }

    boolean contains(Object item) {
        Node current = first;
        while (current != null) {
            if (current.e.equals(item)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}