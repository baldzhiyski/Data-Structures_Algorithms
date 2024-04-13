package implementations;

import interfaces.AVLTree;

public class AVLTreeImpl<T extends Comparable<T>> implements AVLTree<T> {
    private TreeNode<T> root;

    public AVLTreeImpl(){
        this.root= null;
    }

    @Override
    public AVLTree<T> insert(T data) {
       root = insert(data,root);

       return this;
    }
    public TreeNode<T> insert(T data, TreeNode<T> node) {
        if(node == null){
            return new TreeNode<>(data);
        }
        if(data.compareTo(node.getData()) < 0){
            node.setLeft(insert(data,node.getLeft()));
        }else if(data.compareTo(node.getData())> 0){
            node.setRight(insert(data,node.getRight()));
        }

        updateHeight(node);
        return applyRotation(node);
    }

    @Override
    public void delete(T data) {
        root = delete(data,root);
    }

    private TreeNode<T> delete(T data, TreeNode<T> current) {
        if(current == null){
            return null;
        }
        if(data.compareTo(current.getData())<0){
            current.setLeft(delete(data,current.getLeft()));
        }else if(data.compareTo(current.getData())>0){
            current.setRight(delete(data,current.getRight()));
        }else{

            // One Child or Leaf Node
            if(current.getLeft()==null){
                return current.getRight();
            }else if(current.getRight()==null){
                return current.getLeft();
            }

            // Two Children
            current.setData(getMax(current.getLeft()));
            current.setLeft(delete(current.getData(),current.getLeft()));
        }
        updateHeight(current);
        return applyRotation(current);
    }

    private T getMax(TreeNode<T> node) {
        // Base case: if the node is null, return null or throw an exception, depending on your design
        if (node == null) {
            return null; // You can also throw an exception or return a default value
        }

        // Traverse to the rightmost node, which contains the maximum value
        while (node.getRight() != null) {
            node = node.getRight();
        }

        // Return the data of the rightmost node
        return node.getData();
    }

    /**
     * Applies rotation to balance the AVL Tree rooted at the given node.
     * <p>
     * This method checks the balance factor of the given node and performs rotation
     * operations if necessary to rebalance the tree. It handles left-heavy and right-heavy
     * situations by performing appropriate rotations.
     * </p>
     *
     * @param node the root node of the AVL Tree subtree to apply rotation to
     * @return the root node of the updated subtree after rotation
     */
    private TreeNode<T> applyRotation(TreeNode<T> node) {
        int balance = balance(node);

        // left-heavy situation
        if(balance>1){
            if(balance(node.getLeft()) < 0){
                node.setLeft(rotateLeft(node.getLeft()));
            }
            return rotateRight(node);
        }

        // right-heavy situation
        if(balance<-1){
            if(balance(node.getRight())>0){
                node.setRight(rotateRight(node.getRight()));
            }
            return rotateLeft(node);
        }
        return node;
    }

    /**
     * Performs a right rotation on the AVL Tree rooted at the given node.
     * <p>
     * This method performs a right rotation on the AVL Tree rooted at the given node.
     * It adjusts the links between the nodes and updates their heights accordingly
     * to maintain the balanced property of the tree.
     * </p>
     *
     * @param node the root node of the AVL Tree subtree to rotate right
     * @return the root node of the updated subtree after rotation
     */
    private TreeNode<T> rotateRight(TreeNode<T> node) {
        TreeNode<T> leftNode = node.getLeft();
        TreeNode<T> centerNode = leftNode.getRight();

        leftNode.setRight(node);
        node.setLeft(centerNode);
        updateHeight(node);
        updateHeight(leftNode);

        // We need to return the node that took the place of the root node
        return leftNode;
    }

    /**
     * Performs a left rotation on the AVL Tree rooted at the given node.
     * <p>
     * This method performs a left rotation on the AVL Tree rooted at the given node.
     * It adjusts the links between the nodes and updates their heights accordingly
     * to maintain the balanced property of the tree.
     * </p>
     *
     * @param node the root node of the AVL Tree subtree to rotate left
     * @return the root node of the updated subtree after rotation
     */
    private TreeNode<T> rotateLeft(TreeNode<T> node) {
        TreeNode<T> rightNode = node.getRight();
        TreeNode<T> centerNode = rightNode.getLeft();

        rightNode.setLeft(node);
        node.setRight(centerNode);
        updateHeight(node);
        updateHeight(rightNode);

        return rightNode;

    }

    /**
     * Calculates the balance factor of the AVL Tree rooted at the given node.
     * <p>
     * This method calculates the balance factor of the AVL Tree rooted at the given node.
     * The balance factor is the difference between the heights of the left and right subtrees.
     * </p>
     *
     * @param node the root node of the AVL Tree subtree to calculate the balance factor for
     * @return the balance factor of the AVL Tree subtree
     */
    private int balance(TreeNode<T> node) {
        return node !=null ? height(node.getLeft()) - height(node.getRight()) : 0;
    }

    /**
     * Updates the height of the given node in the AVL Tree.
     * <p>
     * This method updates the height of the given node in the AVL Tree based on the
     * heights of its left and right subtrees. The height of a node is defined as the
     * length of the longest path from the node to a leaf node.
     * </p>
     *
     * @param node the node whose height needs to be updated
     */
    private void updateHeight(TreeNode<T> node) {
        int maxHeight = Math.max(
                height(node.getLeft()),
                height(node.getRight())
        );
        node.setHeight(maxHeight + 1);
    }


    /**
     * Retrieves the height of the given node in the AVL Tree.
     * <p>
     * This method retrieves the height of the given node in the AVL Tree. If the node
     * is null, it returns 0. Otherwise, it returns the height stored in the node.
     * </p>
     *
     * @param node the node whose height needs to be retrieved
     * @return the height of the node, or 0 if the node is null
     */
    private int height(TreeNode<T> node) {
        return node !=null ? node.getHeight() : 0;
    }

    @Override
    public void traverse() {
        traverseInOrder(root);
    }

    private void traverseInOrder(TreeNode<T> node) {
        if(node!=null){
            traverseInOrder(node.getLeft());
            System.out.println(node);
            traverseInOrder(node.getRight());
        }
    }

    @Override
    public T getMax() {
        TreeNode<T> node = root;
        if(node == null){
            return null;
        }
        while (node.getRight()!=null){
            node= node.getRight();
        }

        return node.data;
    }

    @Override
    public T getMin() {
        TreeNode<T> node = root;
        if(node == null){
            return null;
        }
        while (node.getLeft()!=null){
            node= node.getLeft();
        }

        return node.data;
    }

    @Override
    public boolean isEmpty() {
        return this.root==null;
    }

}
