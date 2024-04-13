package implementations;

import interfaces.AbstractBinarySearchTree;

public class BinarySearchTree<E extends Comparable<E>> implements AbstractBinarySearchTree<E> {
    private Node<E> root;

    public BinarySearchTree(){
        this.root=null;
    }
    public BinarySearchTree(Node<E> other) {
        this.copy(other);
    }

    private void copy(Node<E> node) {
        if(node!=null){
            this.insert(node.value);
            this.copy(node.leftChild);
            this.copy(node.rightChild);
        }
    }

    @Override
    public void insert(E element) {
        Node<E> newNode = new Node<>(element);
        if(this.getRoot() == null){
            this.root=newNode;
        }else{
            Node<E> current = this.root;
            Node<E> prev = current;

            while (current!=null){
                prev=current;
                if(isLess(element,current.value)){
                    current=current.leftChild;
                }else if(isGreater(element,current.value)){
                    current=current.rightChild;
                }else if(areEqual(element,current.value)){
                    return;
                }
            }

            assert prev != null;
            if(isLess(element,prev.value)){
                prev.leftChild=newNode;
            }else if(isGreater(element,prev.value)){
                prev.rightChild = newNode;
            }
        }
    }

    private boolean isLess(E first, E second) {
        return first.compareTo(second)<0;
    }
    private boolean isGreater(E first, E second) {
        return first.compareTo(second)>0;
    }

    private boolean areEqual(E first, E second) {
        return first.compareTo(second) == 0;
    }


    @Override
    public boolean contains(E element) {
        return contains(element,root);
    }

    private boolean contains(E element, Node<E> current) {
        // Check if the current node is null
        if (current == null) {
            return false; // Element not found in the subtree
        }

        // Compare the element with the value of the current node
        int cmp = element.compareTo(current.value);

        // If the element matches the value of the current node, return true
        if (cmp == 0) {
            return true;
        }

        // If the element is smaller than the value of the current node, search in the left subtree
        else if (cmp < 0) {
            return contains(element, current.leftChild);
        }

        // If the element is larger than the value of the current node, search in the right subtree
        else {
            return contains(element, current.rightChild);
        }
    }

    @Override
    public AbstractBinarySearchTree<E> search(E element) {
        Node<E> current = this.root;

        while (current != null){
            if (element.compareTo(current.value) < 0) {
                current = current.leftChild;
            } else if (element.compareTo(current.value) > 0) {
                current = current.rightChild;
            } else {
                break;
            }
        }
        return new BinarySearchTree<>(current);
    }

    @Override
    public Node<E> getRoot() {
        return this.root;
    }

    @Override
    public Node<E> getLeft() {
        return this.root.leftChild;
    }

    @Override
    public Node<E> getRight() {
        return this.root.rightChild;
    }

    @Override
    public E getValue() {
        return this.root.value;
    }
}
