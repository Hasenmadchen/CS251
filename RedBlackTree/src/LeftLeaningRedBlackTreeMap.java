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

        int oldSize = size;
        Node<K, V> node = findOrMakeNode(root, key, value);
        node = adjust(node);
        return oldSize > size ? node.getValue() : null;
    }

    private Node<K, V> findOrMakeNode(Node<K, V> node, K key, V value) {
        if (node == null) {
            size++;
            return new Node<>(Color.RED, key, value, null, null, null);
        }

        int compare = key.compareTo(node.getKey());
        if (compare < 0) {
            node.setLeftChild(findOrMakeNode(node.getLeftChild(), key, value));
        } else if (compare > 0) {
            node.setRightChild(findOrMakeNode(node.getRightChild(), key, value));
        } else {
            node.setValue(value);
        }
        return node;
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

    public K ceilingKey(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        Node<K, V> node = findNode(key, root);
        if (node.getRightChild() == null) {
            return null;
        } else {
            if (node.getRightChild().getLeftChild() == null) {
                return node.getRightChild().getKey();
            } else {
                node = node.getRightChild();
                while (node.getLeftChild() != null) {
                    node = node.getLeftChild();
                }
                return node.getKey();
            }
        }
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

    public boolean containsKey(K key) {
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

        boolean[] same = {false};
        root.breadthFirst( node -> {
            Node thatNode = that.findNode(node.getKey(), that.root);
            if(thatNode == null || !thatNode.equals(node)) {
                same[0] = false;
                return false;
            }
            return true;
        });
        return same[0];
    }

    public K firstKey() {
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

    public K floorKey(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        Node<K, V> node = findNode(key, root);
        if (node.getLeftChild() == null) {
            return null;
        } else {
            if (node.getLeftChild().getRightChild() == null) {
                return node.getLeftChild().getKey();
            } else {
                node = node.getLeftChild();
                while (node.getRightChild() != null) {
                    node = node.getRightChild();
                }
                return node.getKey();
            }
        }
    }

    public V get(K key) {
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        Node<K, V> node = findNode(key, root);
        return node == null ? null : node.getValue();
    }

    public K higherKey(K key) {

    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public K lastKey() {
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

    public K lowerKey(K key) {

    }

    public int size() {
        return size;
    }

    public String toString() {

    }
}
