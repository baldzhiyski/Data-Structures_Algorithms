import java.util.function.Consumer;

public class AVL<T extends Comparable<T>> {

    private Node<T> root;

    public Node<T> getRoot() {
        return this.root;
    }

    public int height() {
        return height(this.root);
    }

    public boolean contains(T item) {
        Node<T> node = this.search(this.root, item);
        return node != null;
    }

    public void insert(T item) {
        this.root = this.insert(this.root, item);
    }

    public void eachInOrder(Consumer<T> consumer) {
        this.eachInOrder(this.root, consumer);
    }

    public void delete(T item) {
        this.root = this.delete(this.root, item);
    }

    // Delete operation for a specific item
    private Node<T> delete(Node<T> node, T item) {
        if (node == null) {
            return null;
        }

        int cmp = item.compareTo(node.value);
        if (cmp < 0) {
            // If the item to be deleted is smaller, move to the left subtree
            node.left = delete(node.left, item);
        } else if (cmp > 0) {
            // If the item to be deleted is greater, move to the right subtree
            node.right = delete(node.right, item);
        } else {
            // If the item is found
            if (node.left == null) {
                return node.right; // If the left subtree is null, return the right subtree
            }
            if (node.right == null) {
                return node.left; // If the right subtree is null, return the left subtree
            }
            // If the node has both left and right subtrees
            // Find the minimum node from the right subtree
            Node<T> minNode = this.getMin(node.right);
            // Delete the minimum node from the right subtree
            minNode.right = this.deleteMin(node.right);
            // Assign left subtree of the original node to the minimum node
            minNode.left = node.left;
            node = minNode; // Replace the original node with the minimum node
        }

        // Update height and balance the tree
        updateHeight(node);
        return balance(node);
    }

    // Delete operation for the minimum value in the tree
    public void deleteMin() {
        this.root = this.deleteMin(root);
    }

    // Delete the minimum value starting from the given node
    private Node<T> deleteMin(Node<T> node) {
        if (node == null) return null;

        // If the left child is null, return the right child
        if (node.left == null) return node.right;

        // Recursively move to the left subtree until reaching the node with no left child
        node.left = deleteMin(node.left);

        // Update height and balance the tree
        updateHeight(node);
        return balance(node);
    }

    // Delete operation for the maximum value in the tree
    public void deleteMax() {
        if (this.root == null) {
            throw new IllegalArgumentException();
        }
        this.root = this.deleteMax(this.root);
    }

    // Delete the maximum value starting from the given node
    private Node<T> deleteMax(Node<T> node) {
        if (node == null) return null;

        // If the right child is null, return the left child
        if (node.right == null) return node.left;

        // Recursively move to the right subtree until reaching the node with no right child
        node.right = this.deleteMax(node.right);

        return node;
    }

    // Perform an in-order traversal of the tree and apply the action to each node
    private void eachInOrder(Node<T> node, Consumer<T> action) {
        if (node == null) {
            return;
        }

        // Traverse the left subtree
        this.eachInOrder(node.left, action);
        // Perform action on the current node
        action.accept(node.value);
        // Traverse the right subtree
        this.eachInOrder(node.right, action);
    }

    // Insert an item into the tree
    private Node<T> insert(Node<T> node, T item) {
        if (node == null) {
            return new Node<>(item);
        }

        int cmp = item.compareTo(node.value);
        if (cmp < 0) {
            // Insert into the left subtree if smaller
            node.left = this.insert(node.left, item);
        } else if (cmp > 0) {
            // Insert into the right subtree if greater
            node.right = this.insert(node.right, item);
        }

        // Update height and balance the tree
        this.updateHeight(node);
        return balance(node);
    }

    // Get the node with the minimum value from the given node
    private Node<T> getMin(Node<T> node) {
        if (node == null) {
            return null;
        }

        // Keep traversing left until reaching the node with no left child
        if (node.left == null) {
            return node;
        }

        return getMin(node.left);
    }

    // Perform a left rotation on the given node
    private Node<T> rotateLeft(Node<T> node) {
        Node<T> right = node.right;
        node.right = right.left;
        right.left = node;

        // Update height of the rotated nodes
        this.updateHeight(node);
        this.updateHeight(right);

        return right;
    }

    // Perform a right rotation on the given node
    private Node<T> rotateRight(Node<T> node) {
        Node<T> left = node.left;
        node.left = node.left.right;
        left.right = node;

        // Update height of the rotated nodes
        this.updateHeight(node);
        this.updateHeight(left);

        return left;
    }

    // Balance the tree by performing rotations if necessary
    private Node<T> balance(Node<T> node) {
        int balance = this.balanceFactor(node);

        if (balance < -1) {
            int childBalance = this.balanceFactor(node.right);
            if (childBalance > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);

        } else if (balance > 1) {
            int childBalance = this.balanceFactor(node.left);
            if (childBalance < 0) {
                node.left = this.rotateRight(node.left);
            }
            return this.rotateRight(node);
        }

        return node;
    }

    // Search for a node with a specific value
    private Node<T> search(Node<T> node, T item) {
        if (node == null) {
            return null;
        }

        int cmp = item.compareTo(node.value);
        if (cmp < 0) {
            // Search in the left subtree if smaller
            return search(node.left, item);
        } else if (cmp > 0) {
            // Search in the right subtree if greater
            return search(node.right, item);
        }

        return node;
    }

    // Calculate the balance factor of a node
    private int balanceFactor(Node<T> node) {
        return height(node.left) - height(node.right);
    }

    // Calculate the height of a node
    private int height(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private void updateHeight(Node<T> node) {
        node.height = Math.max(this.height(node.left), this.height(node.right)) + 1;
    }
}
