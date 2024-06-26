package redblacktree;

import static java.awt.Color.BLACK;
import static java.awt.Color.RED;

public class RedBlackTree<T extends Comparable<T>>{

    private Node<T> root;

    public RedBlackTree<T> insert(T data) {
        Node<T> node = new Node<>(data); // Create a new node
        root = insert(root, node); // Insert the new node into the tree
        recolorAndRotate(node); // Perform recoloring and rotations to maintain Red-Black tree properties from inserted node
        return this;
    }
    public RedBlackTree<T> delete(T data){
        root = delete(root,data);
        recolorAndRotateRec(root);
        return this;
    }

    private Node<T> delete(Node<T> root, T data) {
        // If the tree is empty or the key is not found, return null
        if (root == null) {
            return null;
        }

        int cmp = data.compareTo(root.getData());

        // If the key is smaller than the value of the current node, go to the left subtree
        if (cmp<0) {
            root.setLeftChild(delete(root.getLeftChild(), data));
        }
        // If the key is larger than the value of the current node, go to the right subtree
        else if (cmp>0) {
            root.setRightChild(delete(root.getRightChild(), data));
        }
        // If the key is found
        else {
            // Case 1: Node has no children or only one child
            if (root.getLeftChild() == null) {
                return root.getRightChild(); // Return the right child (or null if it's also null)
            } else if (root.getRightChild() == null) {
                return root.getLeftChild(); // Return the left child
            }

            // Case 2: Node has two children
            // Find the smallest node in the right subtree (successor) and replace the current node with it
            root.setData(minValue(root.getRightChild()));
            // Delete the successor node from the right subtree
            root.setRightChild(delete(root.getRightChild(), root.getData()));
        }

        // Return the current node
        return root;
    }
    private void recolorAndRotateRec(Node<T> node) {
        // Recursively call recolorAndRotate on each child node
        if (node != null) {
            recolorAndRotateRec(node.getLeftChild()); // Recursively call on the left subtree
            recolorAndRotateRec(node.getRightChild()); // Recursively call on the right subtree

            // Perform recoloring and rotations for the current node
            Node<T> parent = node.getParent();
            // If the current node is not the root and its parent is red
            if (node != root && parent.getColor() == RED) {
                // Obtain the grandparent and uncle of the current node
                Node<T> grandParent = parent.getParent();
                Node<T> uncle = parent.isLeftChild() ? grandParent.getRightChild() : grandParent.getLeftChild();
                // If the uncle of the current node is red, perform recoloring
                if (uncle != null && uncle.getColor() == RED) {
                    handleRecoloring(parent, uncle, grandParent);
                } else if (parent.isLeftChild()) { // Handle left-leaning violation and uncle is not present
                    handleLeftSituations(node, parent, grandParent);
                } else if (!parent.isLeftChild()) { // Handle right-leaning violation and uncle is not present
                    handleRightSituations(node, parent, grandParent);
                }
            }
            root.setColor(BLACK); // Color the root node black
        }
    }

    private T minValue(Node<T> node) {
        if (node == null) {
            return null; // Return null if the tree is empty
        }
        if (node.getLeftChild() == null) {
            return node.getData(); // Return the data of the leftmost node
        }
        return minValue(node.getLeftChild());
    }


    // Private method to recursively insert a node into the Red-Black tree
    private Node<T> insert(Node<T> node, Node<T> nodeToInsert) {
        // Base case: If the current node is null, return the new node to be inserted
        if (node == null) {
            return nodeToInsert;
        }
        // Recursive case: Insert the new node into the appropriate subtree
        if (nodeToInsert.getData().compareTo(node.getData()) < 0) {
            node.setLeftChild(insert(node.getLeftChild(), nodeToInsert));
            node.getLeftChild().setParent(node);
        } else if (nodeToInsert.getData().compareTo(node.getData()) > 0) {
            node.setRightChild(insert(node.getRightChild(), nodeToInsert));
            node.getRightChild().setParent(node);
        }
        return node;
    }

    // Method to perform recoloring and rotations to maintain Red-Black tree properties after insertion
    private void recolorAndRotate(Node<T> node) {
        // Obtain the parent node of the inserted node
        Node<T> parent = node.getParent();
        // If the inserted node is not the root and its parent is red
        if (node != root && parent.getColor() == RED) {
            // Obtain the grandparent and uncle of the inserted node
            Node<T> grandParent = node.getParent().getParent();
            Node<T> uncle = parent.isLeftChild() ?
                    grandParent.getRightChild() : grandParent.getLeftChild();
            // If the uncle of the inserted node is red, perform recoloring
            if (uncle != null && uncle.getColor() == RED) {
                handleRecoloring(parent, uncle, grandParent); // Handles  just switching the colors
            } else if (parent.isLeftChild()) { // Handle left-leaning violation and uncle is no present
                handleLeftSituations(node, parent, grandParent);
            } else if (!parent.isLeftChild()) { // Handle right-leaning violation and uncle is no present
                handleRightSituations(node, parent, grandParent);
            }
        }
        root.setColor(BLACK); // Color the root node black
    }

    // Method to handle right-leaning situations (Right-Right and Right-Left)
    private void handleRightSituations(Node<T> node, Node<T> parent, Node<T> grandParent) {
        if (node.isLeftChild()) {
            rotateRight(parent); // Rotate the parent node to the right
        }
        parent.flipColor(); // Flip the colors of the parent and grandparent nodes
        grandParent.flipColor();
        rotateLeft(grandParent); // Rotate the grandparent node to the left
        recolorAndRotate(node.isLeftChild() ? grandParent : parent); // Recursively check for violations
    }

    // Method to handle left-leaning situations (Left-Left and Left-Right)
    private void handleLeftSituations(Node<T> node, Node<T> parent, Node<T> grandParent) {
        if (!node.isLeftChild()) {
            rotateLeft(parent); // Rotate the parent node to the left
        }
        parent.flipColor(); // Flip the colors of the parent and grandparent nodes
        grandParent.flipColor();
        rotateRight(grandParent); // Rotate the grandparent node to the right
        recolorAndRotate(node.isLeftChild() ? parent : grandParent); // Recursively check for violations
    }

    // Method to handle recoloring when both parent and uncle of the inserted node are red
    private void handleRecoloring(Node<T> parent, Node<T> uncle, Node<T> grandParent) {
        uncle.flipColor(); // Flip the colors of the parent, uncle, and grandparent nodes
        parent.flipColor();
        grandParent.flipColor();
        recolorAndRotate(grandParent); // Recursively check for violations starting from the grandparent and moving upwards
    }

    // Method to rotate the subtree rooted at the given node to the right
    private void rotateRight(Node<T> node) {
        Node<T> leftNode = node.getLeftChild();
        node.setLeftChild(leftNode.getRightChild()); // Set the right child of the left node as the left child of the current node
        if (node.getLeftChild() != null) {
            node.getLeftChild().setParent(node); // Update the parent reference of the new left child
        }
        leftNode.setRightChild(node); // Make the current node the right child of the left node
        leftNode.setParent(node.getParent()); // Update the parent reference of the left node
        updateChildrenOfParentNode(node, leftNode); // Update the children references of the parent node
        node.setParent(leftNode); // Update the parent reference of the current node
    }

    // Method to rotate the subtree rooted at the given node to the left
    private void rotateLeft(Node<T> node) {
        Node<T> rightNode = node.getRightChild();
        node.setRightChild(rightNode.getLeftChild()); // Set the left child of the right node as the right child of the current node
        if (node.getRightChild() != null) {
            node.getRightChild().setParent(node); // Update the parent reference of the new right child
        }
        rightNode.setLeftChild(node); // Make the current node the left child of the right node
        rightNode.setParent(node.getParent()); // Update the parent reference of the right node
        updateChildrenOfParentNode(node, rightNode); // Update the children references of the parent node
        node.setParent(rightNode); // Update the parent reference of the current node
    }

    // Method to update the children references of the parent node after rotation
    private void updateChildrenOfParentNode(Node<T> node, Node<T> tempNode) {
        if (node.getParent() == null) { // If the current node is the root
            root = tempNode; // Update the root reference
        } else if (node.isLeftChild()) {
            node.getParent().setLeftChild(tempNode); // Update the left child reference of the parent node
        } else {
            node.getParent().setRightChild(tempNode); // Update the right child reference of the parent node
        }
    }

    public void traverse() {
        traverseInOrder(root);
    }

    private void traverseInOrder(Node<T> node) {
        if (node != null) {
            traverseInOrder(node.getLeftChild());
            System.out.println(node);
            traverseInOrder(node.getRightChild());
        }
    }


    public T getMax() {
        if (isEmpty()) {
            return null;
        }
        return getMax(root);
    }

    private T getMax(Node<T> node) {
        if (node.getRightChild() != null) {
            return getMax(node.getRightChild());
        }
        return node.getData();
    }


    public T getMin() {
        if (isEmpty()) {
            return null;
        }
        return getMin(root);
    }

    private T getMin(Node<T> node) {
        if (node.getLeftChild() != null) {
            return getMin(node.getLeftChild());
        }
        return node.getData();
    }

    public boolean isEmpty() {
        return root == null;
    }

}