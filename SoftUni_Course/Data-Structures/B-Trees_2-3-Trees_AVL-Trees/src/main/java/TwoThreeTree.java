import javax.swing.*;

public class TwoThreeTree<K extends Comparable<K>> {
    private TreeNode<K> root;

    public TwoThreeTree() {
        this.root = null;
    }

    public static class TreeNode<K> {
        private K leftKey;
        private K rightKey;

        private TreeNode<K> leftChild;
        private TreeNode<K> middleChild;
        private TreeNode<K> rightChild;

        private TreeNode(K key) {
            this.leftKey = key;
            this.rightKey = null;
        }

        public TreeNode(K root, K left, K right) {
            this(root);
            this.leftChild = new TreeNode<>(left);
            this.rightChild = new TreeNode<>(right);
        }
        public TreeNode(K root , TreeNode<K> left, TreeNode<K> right){
            this.leftKey=root;
            this.leftChild=left;
            this.rightChild=right;
        }

        boolean isThreeNode() {
            return rightKey != null;
        }

        boolean isTwoNode() {
            return rightKey == null;
        }

        boolean isLeaf() {
            return this.leftChild == null && this.middleChild == null && this.rightChild == null;
        }
    }

    public void insert(K key) {
        if (this.root == null) {
            // If the tree is empty, create a new root node with the key
            this.root = new TreeNode<>(key);
            return;
        }

        // Recursively insert the key into the tree
        TreeNode<K> newRoot = this.insert(this.root, key);
        if (newRoot != null) {
            // If the root splits, update the root of the tree
            this.root = newRoot;
        }
    }

    private TreeNode<K> insert(TreeNode<K> node, K key) {
        if (node.isLeaf()) {
            // If the node is a leaf node
            if (node.isTwoNode()) {
                // If the leaf node is a 2-node
                if (node.leftKey.compareTo(key) < 0) {
                    // If the key is greater than the left key, set it as the right key
                    node.rightKey = key;
                } else {
                    // If the key is smaller, shift the left key to the right and set the new key as the left key
                    node.rightKey = node.leftKey;
                    node.leftKey = key;
                }
                return null; // No split needed
            }
            // If the leaf node is a 3-node
            K left = node.leftKey;
            K middle = key;
            K right = node.rightKey;
            if (middle.compareTo(node.leftKey) < 0) {
                // If the new key is smaller than the left key, adjust keys accordingly
                left = key;
                middle = node.leftKey;
            } else if (key.compareTo(node.rightKey) > 0) {
                // If the new key is larger than the right key, adjust keys accordingly
                middle = node.rightKey;
                right = key;
            }
            // Create a new node to split the current leaf node and promote the middle key
            return new TreeNode<>(middle, left, right);
        }

        // Navigate to the appropriate child node ( to the leaves)
        TreeNode<K> toFix = null;
        if (node.leftKey.compareTo(key) > 0) {
            toFix = insert(node.leftChild, key);
        } else if (node.isTwoNode() && node.leftKey.compareTo(key) < 0) {
            toFix = insert(node.rightChild, key);
        } else if (node.isThreeNode() && node.rightKey.compareTo(key) < 0) {
            toFix = insert(node.rightChild, key);
        } else {
            toFix = insert(node.middleChild, key);
        }
        if (toFix == null) return null;

        if (node.isTwoNode()) {
            // If the parent node is a 2-node, handle the split
            if (toFix.leftKey.compareTo(node.leftKey) < 0) {
                // If the left key of the new child is smaller than the left key of the parent
                node.rightKey = node.leftKey;
                node.leftKey = toFix.leftKey;
                node.leftChild = toFix.leftChild;
                node.middleChild = toFix.rightChild;
            } else {
                // If the left key of the new child is larger, adjust keys accordingly
                node.rightKey = toFix.leftKey;
                node.middleChild = toFix.leftChild;
                node.rightChild = toFix.rightChild;
            }
            return null; // No further split needed
        }

        // Handle split of a 3-node parent
        K promoteValue;
        TreeNode<K> left, right;
        if (toFix.leftKey.compareTo(node.leftKey) < 0) {
            promoteValue = node.leftKey;
            left = toFix;
            right = new TreeNode<>(node.rightKey, node.middleChild, node.rightChild);
        } else if (toFix.leftKey.compareTo(node.rightKey) > 0) {
            promoteValue = node.rightKey;
            left = new TreeNode<>(node.leftKey, node.leftChild, node.middleChild);
            right = toFix;
        } else {
            promoteValue = toFix.leftKey;
            left = new TreeNode<>(node.leftKey, node.leftChild, toFix.leftChild);
            right = new TreeNode<>(node.rightKey, toFix.rightChild, node.rightChild);
        }

        // Return a new node representing the split and promotion
        return new TreeNode<>(promoteValue, left, right);
    }


    public String getAsString() {
    StringBuilder out = new StringBuilder();
    recursivePrint(this.root, out);
    return out.toString().trim();
}

private void recursivePrint(TreeNode<K> node, StringBuilder out) {
    if (node == null) {
        return;
    }
    if (node.leftKey != null) {
        out.append(node.leftKey)
                .append(" ");
    }
    if (node.rightKey != null) {
        out.append(node.rightKey).append(System.lineSeparator());
    } else {
        out.append(System.lineSeparator());
    }
    if (node.isTwoNode()) {
        recursivePrint(node.leftChild, out);
        recursivePrint(node.rightChild, out);
    } else if (node.isThreeNode()) {
        recursivePrint(node.leftChild, out);
        recursivePrint(node.middleChild, out);
        recursivePrint(node.rightChild, out);
    }
}
}
