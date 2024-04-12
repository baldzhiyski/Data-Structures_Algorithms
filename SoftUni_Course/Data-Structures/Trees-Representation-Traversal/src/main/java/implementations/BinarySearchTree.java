package implementations;



public class BinarySearchTree<T extends Comparable<T>>{
    // Inner class for tree nodes
    private static class TreeNode<T> {
        T data;
        TreeNode<T> left;
        TreeNode<T> right;

        public TreeNode(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        public T getData() {
            return data;
        }

        public TreeNode<T> getLeft() {
            return left;
        }

        public TreeNode<T> getRight() {
            return right;
        }

        public void setLeft(TreeNode<T> left) {
            this.left = left;
        }

        public void setRight(TreeNode<T> right) {
            this.right = right;
        }
    }

    private TreeNode<T> root;

    public BinarySearchTree() {
        this.root = null;
    }

    // Method to insert a new node into the binary search tree
    public void insert(T key) {
        root = insertRec(root, key);
    }

    // Recursive helper method to insert a new node
    private TreeNode<T> insertRec(TreeNode<T> root, T key) {
        // If the tree is empty, create a new node and return it as the root
        if (root == null) {
            root = new TreeNode<>(key);
            return root;
        }

        // Use compareTo for comparing generic types
        int cmp = key.compareTo(root.getData());

        // If the key is smaller than the value of the current node, go to the left subtree
        if (cmp < 0) {
            root.left = insertRec(root.left, key);
        }
        // If the key is larger than the value of the current node, go to the right subtree
        else if (cmp > 0) {
            root.right = insertRec(root.right, key);
        }

        // Return the structure
        return root;
    }

    public void deleteIterative(T key) {
        TreeNode<T> current = root;
        TreeNode<T> parent = null;
        boolean isLeftChild = false;

        // Search for the node to delete
        while (current != null && current.data != key) {
            parent = current;
            int cmp = key.compareTo(current.data);
            if (cmp<0) {
                current = current.left;
                isLeftChild = true;
            } else {
                current = current.right;
                isLeftChild = false;
            }
        }

        // If the node with the given key is not found
        if (current == null) {
            System.out.println("Node with key " + key + " not found");
            return;
        }

        // Case 1: Node to delete has no children
        if (current.left == null && current.right == null) {
            if (current == root) {
                root = null; // If it's the root node
            } else if (isLeftChild) {
                parent.left = null; // If it's a left child
            } else {
                parent.right = null; // If it's a right child
            }
        }
        // Case 2: Node to delete has only one child
        else if (current.right == null) {
            if (current == root) {
                root = current.left; // If it's the root node
            } else if (isLeftChild) {
                parent.left = current.left; // If it's a left child
            } else {
                parent.right = current.left; // If it's a right child
            }
        } else if (current.left == null) {
            if (current == root) {
                root = current.right; // If it's the root node
            } else if (isLeftChild) {
                parent.left = current.right; // If it's a left child
            } else {
                parent.right = current.right; // If it's a right child
            }
        }
        // Case 3: Node to delete has two children
        else {
            TreeNode<T> successor = getSuccessor(current);
            TreeNode<T> successorParent = getSuccessorParent(current);

            // Delete the successor from its previous position
            if (successorParent.left == successor) {
                successorParent.left = null;
            } else {
                successorParent.right = null;
            }

            // Connect the parent of the node to delete with the successor
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }

            // Connect the successor to the left child of the node to delete
            successor.left = current.left;
            successor.right = current.right;
        }
    }

    // Helper method to find the successor of a node
    private TreeNode<T> getSuccessor(TreeNode<T> node) {
        TreeNode<T> current = node.right;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Helper method to find the parent of the successor of a node
    private TreeNode<T> getSuccessorParent(TreeNode<T> node) {
        TreeNode<T> current = node.right;
        TreeNode<T> parent = node;
        while (current.left != null) {
            parent = current;
            current = current.left;
        }
        return parent;
    }
    public void delete(T key) {
        root = deleteRec(root, key);
    }

    // Recursive helper method to delete a node
    private TreeNode<T> deleteRec(TreeNode<T> root, T key) {
        // If the tree is empty or the key is not found, return null
        if (root == null) {
            return root;
        }

        int cmp = key.compareTo(root.data);

        // If the key is smaller than the value of the current node, go to the left subtree
        if (cmp<0) {
            root.left = deleteRec(root.left, key);
        }
        // If the key is larger than the value of the current node, go to the right subtree
        else if (cmp>0) {
            root.right = deleteRec(root.right, key);
        }
        // If the key is found
        else {
            // Case 1: Node has no children or only one child
            if (root.left == null) {
                return root.right; // Return the right child (or null if it's also null)
            } else if (root.right == null) {
                return root.left; // Return the left child
            }

            // Case 2: Node has two children
            // Find the smallest node in the right subtree (successor) and replace the current node with it
            root.data = minValue(root.right);
            // Delete the successor node from the right subtree
            root.right = deleteRec(root.right, root.data);
        }

        // Return the current node
        return root;
    }

    // Helper method to find the smallest node in a subtree
    private T minValue(TreeNode<T> node) {
        T minValue = node.data;
        while (node.left != null) {
            minValue = node.left.data;
            node = node.left;
        }
        return minValue;
    }
    public String inorderTraversalString() {
        StringBuilder result = new StringBuilder();
        inorderTraversal(root, result);
        return result.toString();
    }

    private void inorderTraversal(TreeNode<T> node, StringBuilder result) {
        if (node != null) {
            inorderTraversal(node.left, result);
            result.append(node.data).append(" ");
            inorderTraversal(node.right, result);
        }
    }
}
