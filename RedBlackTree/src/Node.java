import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * A node in a left-leaning red-black tree.
 *
 * <p>Purdue University -- CS25100 -- Fall 2019 -- Trees</p>
 *
 * @param <K> the type of the key of this node
 * @param <V> the type of the value of this node
 */
public final class Node<K extends Comparable<? super K>, V> {
    /**
     * The color of this node.
     */
    private Color color;

    /**
     * The key of this node.
     */
    private K key;

    /**
     * The value of this node.
     */
    private V value;

    /**
     * The parent of this node.
     */
    private Node<K, V> parent;

    /**
     * The left child of this node.
     */
    private Node<K, V> leftChild;

    /**
     * The right child of this node.
     */
    private Node<K, V> rightChild;

    /**
     * Constructs a newly allocated {@code Node} object with the specified color, key, value, parent, left child, and
     * right child.
     *
     * @param color      the color to be used in construction
     * @param key        the key to be used in construction
     * @param value      the value to be used in construction
     * @param parent     the parent to be used in construction
     * @param leftChild  the left child to be used in construction
     * @param rightChild the right child to be used in construction
     * @throws NullPointerException if the specified color or key is {@code null}
     */
    public Node(Color color, K key, V value, Node<K, V> parent, Node<K, V> leftChild, Node<K, V> rightChild)
        throws NullPointerException {
        Objects.requireNonNull(color, "the specified color is null");

        Objects.requireNonNull(key, "the specified key is null");

        this.color = color;
        this.key = key;
        this.value = value;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    } //Node

    /**
     * Returns the color of this node.
     *
     * @return the color of this node
     */
    public Color getColor() {
        return this.color;
    } //getColor

    /**
     * Returns the key of this node.
     *
     * @return the key of this node
     */
    public K getKey() {
        return this.key;
    } //getKey

    /**
     * Returns the value of this node.
     *
     * @return the value of this node
     */
    public V getValue() {
        return this.value;
    } //getValue

    /**
     * Returns the parent of this node.
     *
     * @return the parent of this node
     */
    public Node<K, V> getParent() {
        return this.parent;
    } //getParent

    /**
     * Returns the left child of this node.
     *
     * @return the left child of this node
     */
    public Node<K, V> getLeftChild() {
        return this.leftChild;
    } //getLeftChild

    /**
     * Returns the right child of this node.
     *
     * @return the right child of this node
     */
    public Node<K, V> getRightChild() {
        return this.rightChild;
    } //getRightChild

    /**
     * Updates the color of this node with the specified color.
     *
     * @param color the color to be used in the update
     * @throws NullPointerException if the specified color is {@code null}
     */
    public void setColor(Color color) throws NullPointerException {
        Objects.requireNonNull(color, "the specified color is null");

        this.color = color;
    } //setColor

    /**
     * Updates the key of this node with the specified key.
     *
     * @param key the key to be used in the update
     * @throws NullPointerException if the specified key is {@code null}
     */
    public void setKey(K key) throws NullPointerException {
        Objects.requireNonNull(key, "the specified key is null");

        this.key = key;
    } //setKey

    /**
     * Updates the value of this node with the specified value.
     *
     * @param value the value to be used in the update
     */
    public void setValue(V value) {
        this.value = value;
    } //setValue

    /**
     * Updates the parent of this node with the specified parent.
     *
     * @param parent the parent to be used in the update
     */
    public void setParent(Node<K, V> parent) {
        this.parent = parent;
    } //setParent

    /**
     * Updates the left child of this node with the specified left child.
     *
     * @param leftChild the left child to be used in the update
     */
    public void setLeftChild(Node<K, V> leftChild) {
        if(this == leftChild) throw new RuntimeException();
        this.leftChild = leftChild;
        if (leftChild != null) {
            leftChild.parent = this;
        }
    } //setLeftChild

    /**
     * Updates the right child of this node with the specified right child.
     *
     * @param rightChild the right child to be used in the update
     */
    public void setRightChild(Node<K, V> rightChild) {
        if(this == rightChild) throw new RuntimeException();
        this.rightChild = rightChild;
        if (rightChild != null) {
            rightChild.parent = this;
        }
    } //setRightChild

    public void flipColor() {
        if (color == Color.RED) {
            color = Color.BLACK;
        } else {
            color = Color.RED;
        }
    }

    /**
     * Returns the hash code of this node.
     *
     * @return the hash code of this node
     */
    @Override
    public int hashCode() {
        int result = 23;

        result = 31 * result + Objects.hashCode(this.key);

        result = 31 * result + Objects.hashCode(this.value);

        return result;
    } //hashCode

    /**
     * Determines whether or not the specified object is equal to this node. {@code true} is returned if and only if
     * the specified object is an instance of {@code Node} and its key and value are equal to this node's.
     *
     * @param object the object to be used in the comparisons
     * @return {@code true}, if the specified object is equal to this node and {@code false} otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof Node) {
            boolean equal;

            equal = Objects.equals(this.key, ((Node) object).key);

            equal &= Objects.equals(this.value, ((Node) object).value);

            return equal;
        } else {
            return false;
        } //end if
    } //equals

    public void breadthFirst(Predicate<Node<K, V>> predicate) {
        List<Node<K, V>> list = new LinkedList<>();
        list.add(this);
        while (!list.isEmpty()) {
            Node<K, V> node = list.remove(0);
            if( !predicate.test(node) ) {
                return;
            }

            if (node.leftChild != null) {
                list.add(node.leftChild);
            }
            if (node.rightChild != null) {
                list.add(node.rightChild);
            }
        }
    }

    /**
     * Returns the {@code String} representation of this node. The returned {@code String} consists this node's key, an
     * equal sign, and this node's value.
     *
     * @return the {@code String} representation of this node
     */
    @Override
    public String toString() {
        String format = "%s=%s";

        return String.format(format, this.key, this.value);
    } //toString
}