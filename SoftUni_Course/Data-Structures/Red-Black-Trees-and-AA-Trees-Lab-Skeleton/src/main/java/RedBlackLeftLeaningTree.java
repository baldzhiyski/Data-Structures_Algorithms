import java.util.function.Consumer;

public class RedBlackLeftLeaningTree<T extends Comparable<T>> {
    private static final boolean RED =true;
    private static final boolean BLACK =false;
    private Node<T> root;

    public RedBlackLeftLeaningTree() {
    }

    private RedBlackLeftLeaningTree(Node<T> node) {
        this.preOrderCopy(node);
    }

    private void preOrderCopy(Node<T> node) {
        if (node == null) {
            return;
        }

        this.insert(node.value);
        this.preOrderCopy(node.left);
        this.preOrderCopy(node.right);
    }

    public int getNodesCount() {
        return this.getNodesCount(this.root);
    }

    private int getNodesCount(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return node.count;
    }

    // TODO:
    //  Insert using iteration over the nodes
    //  You can make a recursive one it is up to you
    //  The difference is that the recursive call should
    //  return Node. We want left-leaning red nodes
    public void insert(T value) {
        this.root = this.insert(this.root,value);
        this.root.color = BLACK;
    }


    private Node<T> insert(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }

        if (node.value.compareTo(value) > 0) {
            // If value is less than current node's value, insert into left subtree
            node.left = this.insert(node.left, value);
        } else if (node.value.compareTo(value) < 0) {
            // If value is greater than current node's value, insert into right subtree
            node.right = this.insert(node.right, value);
        }

        // Red-black tree balancing operations ( all cases) 
        if (!isRed(node.left) && isRed(node.right)) {
            // If the left child is not red and the right child is red, rotate left
            node = rotateLeft(node);
        } else if (isRed(node.left) && isRed(node.left.left)) {
            // If the left child and its left child are both red, rotate right and flip colors
            node = rotateRight(node);
            node = flipColors(node);
        } else if (isRed(node.left) && isRed(node.left.right)) {
            // If the left child and its right child are both red, rotate left on the left child and then right on the current node, and flip colors
            node.left = rotateLeft(node.left);
            node = rotateRight(node);
            node = flipColors(node);
        } else if (isRed(node.left) && isRed(node.right)) {
            // If both children are red, flip colors
            node = flipColors(node);
        }

        // Update count of nodes in the subtree rooted at this node
        node.count = getNodesCount(node.left) + getNodesCount(node.right) + 1;

        return node;
    }

    private Node<T> flipColors(Node<T> node) {
        // Flip colors of the node and its children
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
        return node;
    }

    private Node<T> rotateRight(Node<T> node) {
        // Perform right rotation
        Node<T> result = node.left;
        node.left = result.right;
        result.right = node;

        // Adjust colors
        result.color = BLACK;
        node.color = RED;

        // Update count of nodes in the subtree rooted at this node
        node.count = getNodesCount(node.left) + getNodesCount(node.right) + 1;

        return result;
    }

    private Node<T> rotateLeft(Node<T> node) {
        // Perform left rotation
        Node<T> result = node.right;
        node.right = result.left;
        result.left = node;

        // Adjust colors
        result.color = BLACK;
        node.color = RED;

        // Update count of nodes in the subtree rooted at this node
        node.count = getNodesCount(node.left) + getNodesCount(node.right) + 1;

        return result;
    }

    private boolean isRed(Node<T> node) {
        return node==null ? false : node.isRed();
    }

    public boolean contains(T value) {
        return this.findElement(value) != null;
    }

    public RedBlackLeftLeaningTree<T> search(T item) {
        return new RedBlackLeftLeaningTree<>(this.findElement(item));
    }

    private Node<T> findElement(T item) {
        Node<T> current = this.root;
        while (current != null) {
            if (item.compareTo(current.value) < 0) {
                current = current.left;
            } else if (item.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                break;
            }
        }
        return current;
    }

    public void eachInOrder(Consumer<T> consumer) {
        this.eachInOrder(this.root, consumer);
    }

    private void eachInOrder(Node<T> node, Consumer<T> consumer) {
        if (node == null) {
            return;
        }

        this.eachInOrder(node.left, consumer);
        consumer.accept(node.value);
        this.eachInOrder(node.right, consumer);
    }

    public static class Node<T extends Comparable<T>> {
        private T value;
        private Node<T> left;
        private Node<T> right;
        private boolean color;
        private int count;

        public Node(T value){
            this.value = value;
            this.color = RED;
            this.count=1;
        }

        public  boolean isRed(){
            return this.color == RED;
        }
    }
}

