public class LeftLeaningRedBlackTreeMap<K extends Comparable<? super K>, V> {
    private Node<K, V> root;
    private int size;

    public LeftLeaningRedBlackTreeMap() {
        root = null;
        size = 0;
    }

    public V put(K key, V value) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException("key is null");
        }

        V oldValue = get(key);
        root = putAndAdjust(root, key, value);
        root.setColor(Color.BLACK);
        root.setParent(null);
        return oldValue;
    }

    public Node<K, V> putAndAdjust(Node<K, V> position, K key, V value) throws NullPointerException {
        if (position == null) {
            size++;
            return new Node<>(Color.RED, key, value, null, null, null);
        }

        int compare = key.compareTo(position.getKey());
        if (compare < 0) {
            position.setLeftChild(putAndAdjust(position.getLeftChild(), key, value));
        } else if (compare > 0) {
            position.setRightChild(putAndAdjust(position.getRightChild(), key, value));
        } else {
            position.setValue(value);
        }
        return adjust(position);
    }

    private Node<K, V> adjust(Node<K, V> node) {
        if (isRed(node.getRightChild()) && isBlack(node.getLeftChild())) {
            node = rotateLeft(node);
        }
        if (isRed(node.getLeftChild()) && isRed(node.getLeftChild().getLeftChild())) {
            node = rotateRight(node);
        }
        if (isRed(node.getLeftChild()) && isRed(node.getRightChild())) {
            flipColors(node);
        }
        return node;
    }

    private Node<K, V> rotateRight(Node<K, V> node) {
        // assert (node != null) && isRed(node.left);
        Node<K, V> rotated = node.getLeftChild();
        node.setLeftChild(rotated.getRightChild());
        rotated.setRightChild(node);
        rotated.setColor(rotated.getRightChild().getColor());
        rotated.getRightChild().setColor(Color.RED);
        return rotated;
    }

    private Node<K, V> rotateLeft(Node<K, V> node) {
        // assert (node != null) && isRed(node.getRightChild());
        Node<K, V> rotated = node.getRightChild();
        node.setRightChild(rotated.getLeftChild());
        rotated.setLeftChild(node);
        rotated.setColor(rotated.getLeftChild().getColor());
        rotated.getLeftChild().setColor(Color.RED);
        return rotated;
    }

    private void flipColors(Node<K, V> node) {
        // node must have opposite color of its two children
        // assert (node != null) && (node.getLeftChild() != null) && (node.getRightChild() != null);
        // assert (!isRed(node) &&  isRed(node.getLeftChild()) &&  isRed(node.getRightChild()))
        //    || (isRed(node)  && !isRed(node.getLeftChild()) && !isRed(node.getRightChild()));
        node.flipColor();
        node.getLeftChild().flipColor();
        node.getRightChild().flipColor();
    }

    private boolean isBlack(Node<K, V> node) {
        return node == null || node.getColor() == Color.BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return node != null && node.getColor() == Color.RED;
    }

    public K ceilingKey(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        Node<K, V> node = findCeiling(key, root);
        if (node == null) {
            return null;
        } else {
            return node.getKey();
        }
    }

    private Node<K, V> findCeiling(K key, Node<K, V> node) {
        if (node == null) {
            return null;
        }
        Node<K, V> result = findCeiling(key, node.getLeftChild());
        if (result != null) {
            return result;
        }
        if (node.getKey().compareTo(key) >= 0) {
            return node;
        }
        return findCeiling(key, node.getRightChild());
    }

    private Node<K, V> findNode(K key, Node<K, V> node) {
        if (node == null) {
            return null;
        }

        if (node.getKey().compareTo(key) == 0) {
            return node;
        } else if (node.getKey().compareTo(key) > 0) {
            findNode(key, node.getLeftChild());
        } else if (node.getKey().compareTo(key) < 0) {
            findNode(key, node.getRightChild());
        }
        return null; //should never get here
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public boolean containsKey(K key) throws NullPointerException {
        return findNode(key, root) != null;
    }

    public boolean containsValue(V value) {
        Node<K, V> node = findNode(value, root);
        return node != null;
    }

    private Node<K, V> findNode(V value, Node<K, V> node) {
        if (node == null) {
            return null;
        }
        Node<K, V> result = findNode(value, node.getLeftChild());
        if (result != null) {
            return result;
        }
        if (node.getValue().equals(value)) {
            return node;
        }
        return findNode(value, node.getRightChild());
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if( object.getClass() != LeftLeaningRedBlackTreeMap.class ) {
            return false;
        }
        LeftLeaningRedBlackTreeMap<K, V> that = (LeftLeaningRedBlackTreeMap<K, V>) object;
        if( size != that.size ) {
            return false;
        }

        boolean[] isEqual = {true};
        root.breadthFirst( node -> {
            Node thatNode = that.findNode(node.getKey(), that.root);
            if(thatNode == null || !thatNode.equals(node)) {
                isEqual[0] = false;
                return false;
            }
            return true;
        });
        return isEqual[0];
    }

    public K firstKey() throws NullPointerException {
        if (root.getLeftChild() == null) {
            return root.getKey();
        } else {
            Node<K, V> node = root.getLeftChild();
            while (node.getLeftChild() != null) {
                node = node.getLeftChild();
            }
            return node.getKey();
        }
    }

    public K floorKey(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        Node<K, V> node = findFloor(key, root);
        if (node == null) {
            return null;
        } else {
            return node.getKey();
        }
    }

    private Node<K, V> findFloor(K key, Node<K, V> node) {
        if (node == null) {
            return null;
        }
        Node<K, V> result = findFloor(key, node.getRightChild());
        if (result != null) {
            return result;
        }
        if (node.getKey().compareTo(key) <= 0) {
            return node;
        }
        return findFloor(key, node.getLeftChild());
    }

    public V get(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        Node<K, V> node = findNode(key, root);
        return node == null ? null : node.getValue();
    }

    public K higherKey(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        Node<K, V> node = findHigher(key, root);
        if (node == null) {
            return null;
        } else {
            return node.getKey();
        }
    }

    private Node<K, V> findHigher(K key, Node<K, V> node) {
        if (node == null) {
            return null;
        }
        Node<K, V> result = findHigher(key, node.getLeftChild());
        if (result != null) {
            return result;
        }
        if (node.getKey().compareTo(key) > 0) {
            return node;
        }
        return findHigher(key, node.getRightChild());
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public K lastKey() throws NullPointerException {
        if (root.getRightChild() == null) {
            return root.getKey();
        } else {
            Node<K, V> node = root.getRightChild();
            while (node.getRightChild() != null) {
                node = node.getRightChild();
            }
            return node.getKey();
        }
    }

    public K lowerKey(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        Node<K, V> node = findLower(key, root);
        if (node == null) {
            return null;
        } else {
            return node.getKey();
        }
    }

    private Node<K, V> findLower(K key, Node<K, V> node) {
        if (node == null) {
            return null;
        }
        Node<K, V> result = findLower(key, node.getRightChild());
        if (result != null) {
            return result;
        }
        if (node.getKey().compareTo(key) < 0) {
            return node;
        }
        return findLower(key, node.getLeftChild());
    }

    public int size() {
        return size;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        root.breadthFirst( node -> {
            if(result.length() > 0) {
                result.append(", ");
            }
            result.append(node.getKey() + ": " + node.getValue());
            return true;
        });
        result.append('\n');
        return result.toString();
    }

    Node<K, V> getRoot() {
        return root;
    }
}
