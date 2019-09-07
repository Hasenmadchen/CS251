package proj1;

/**
 * proj1.MyStack.java
 *
 * Project 1, part 1. Stack use array.
 *
 */

public class MyStack<E> {
    public static final int DEFAULT_SIZE = 32;
    private static final int GROWTH_FACTOR = 2;
    private static final int INEFFICIENCY_COEFF = 4;

    private Object[] stack;
    private int count;

    /**
     * Creates an empty Stack with initial capacity of {@code DEFAULT_SIZE}.
     */
    public MyStack() {
        this(DEFAULT_SIZE);
    }

    /**
     * Creates an empty Stack with initial capacity.
     * @param initialCapacity the initial capacity.
     */
    public MyStack(int initialCapacity) {
        stack = new Object[initialCapacity];
        count = 0;
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return the number of elements in the stack
     */
    public int size() {
        return count;
    }

    /**
     * Returns {@code true} if this stack contains no elements.
     *
     * @return {@code true} if this stack contains no elements
     */
    public boolean isEmpty() {
        return (count == 0);
    }

    /* resize the underlying array holding the elements */
    private void resize(int capacity) {
        Object[] arr = new Object[capacity];
        System.arraycopy(stack, 0, arr, 0, count);
        stack = arr;
    }

    /**
     * Adds the element to this stack.
     * @param e the element to add
     */
    public void push(E e) {
        if (stack.length == count) {
            resize(stack.length * GROWTH_FACTOR);
        }
        stack[count] = e;
        count++;
    }

    /**
     * Removes and returns the element most recently added to this stack.
     * @return the element most recently added, or {@code null} if
     * this stack is empty
     *
     */
    public E pop() {
        if (isEmpty()) {
            return null;
        }
        Object obj = stack[count - 1];
        stack[count - 1] = null;
        count--;
        if (count <= stack.length / INEFFICIENCY_COEFF) {
            resize(stack.length / GROWTH_FACTOR + 1);
        }
        //noinspection unchecked
        return (E) obj;
    }

    /**
     * Returns (but does not remove) the item most recently added to this stack.
     * @return the item most recently added to this stack, or {@code null} if
     * this stack is empty
     */
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        //noinspection unchecked
        return (E) stack[count - 1];
    }

    /**
     * Returns true if the {@code item} is in the stack, false otherwise.
     * @param item an item to test for
     * @return true if the {@code item} is in the stack, false otherwise.
     */
    boolean contains(Object item) {
        for(int i = 0; i < count; i++) {
            if(item.equals(stack[i])) {
                return true;
            }
        }
        return false;
    }
}