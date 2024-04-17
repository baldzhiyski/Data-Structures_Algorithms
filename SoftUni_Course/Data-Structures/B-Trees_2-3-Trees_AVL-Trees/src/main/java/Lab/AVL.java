package Lab;

import java.util.function.Consumer;

public class AVL<T extends Comparable<T>> {

    private Node<T> root;

    public Node<T> getRoot() {
        return this.root;
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

    private void eachInOrder(Node<T> node, Consumer<T> action) {
        if (node == null) {
            return;
        }

        this.eachInOrder(node.left, action);
        action.accept(node.value);
        this.eachInOrder(node.right, action);
    }

    private Node<T> insert(Node<T> node, T item) {
        if (node == null) {
            return new Node<>(item);
        }

        int cmp = item.compareTo(node.value);
        if (cmp < 0) {
            node.left = this.insert(node.left, item);
        } else if (cmp > 0) {
            node.right = this.insert(node.right, item);
        }

        updateHeight(node);
        return balance(node);

    }

    private Node<T> rotateRight(Node<T> x){
        Node<T> temp = x.left;
        x.left = temp.right;
        temp.right = x;

        updateHeight(x);
        updateHeight(temp);

        return temp;
    }

    private Node<T>  rotateLeft(Node<T> x){
        Node<T> temp = x.right;
        x.right = temp.left;
        temp.left = x;

        updateHeight(x);
        updateHeight(temp);

        return temp;
    }
    private Node<T> balance(Node<T> node) {
        int balanced = height(node.left) - height(node.right);

        if (balanced > 1) {
            int childBalance = height(node.left.left) - height(node.left.right);
            if (childBalance < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        } else if (balanced < -1) {
            int childBalance = height(node.right.left) - height(node.right.right);
            if (childBalance > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        updateHeight(node);
        return node;
    }


    private Node<T> search(Node<T> node, T item) {
        if (node == null) {
            return null;
        }

        int cmp = item.compareTo(node.value);
        if (cmp < 0) {
            return search(node.left, item);
        } else if (cmp > 0) {
            return search(node.right, item);
        }

        return node;
    }

    private void updateHeight(Node<T> node){
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }
    private  int height(Node<T> node){return node == null ? 0 : node.height;}


}
