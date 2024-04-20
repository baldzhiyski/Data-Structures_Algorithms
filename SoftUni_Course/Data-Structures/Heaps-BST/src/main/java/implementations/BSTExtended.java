package implementations;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Consumer;

public class BSTExtended <E extends Comparable<E>>{
    private Node<E> root;


    public static class Node<E> {
        private E value;
        private Node<E> leftChild;
        private Node<E> rightChild;
        private int count;

        public Node(E value) {
            this.value = value;
        }
        public Node(Node<E> other){
            this.value = other.value;
            this.count=other.count;

            if(other.getLeft()!=null){
                this.leftChild=new Node<>(other.getLeft());
            }

            if(other.getRight()!=null){
                this.rightChild = new Node<>(other.getRight());
            }
        }

        public Node<E> getLeft() {
            return this.leftChild;
        }

        public Node<E> getRight() {
            return this.rightChild;
        }

        public E getValue() {
            return this.value;
        }

        public int getCount() {
            return count;
        }
    }
    public BSTExtended(E element){
        this.root=new Node<>(element);
        root.count=1;
    }
    public BSTExtended(Node<E> root){
        this.root = new Node<>(root);
    }

    public BSTExtended(){
        this.root=null;
    }
    public void eachInOrder(Consumer<E> consumer) {
        eachInOrder(consumer,root);
    }

    private void eachInOrder(Consumer<E> consumer, Node<E> current) {
        if(current!=null){
            eachInOrder(consumer,current.getLeft());
            consumer.accept(current.getValue());
            eachInOrder(consumer,current.getRight());
        }
    }

    public Node<E> getRoot() {
        return this.root;
    }

    public void insert(E element) {
        this.root=insert(element,root);

    }

    private Node<E> insert(E element, Node<E> current) {
        if(current==null){
            current = new Node<>(element);
            current.count++;
            return current;
        }
        int cmp = element.compareTo(current.getValue());

        if(cmp<0){
            current.leftChild=insert(element,current.getLeft());
        }
        if(cmp>0){
            current.rightChild=insert(element,current.getRight());
        }
        current.count++;
        return current;
    }

    public boolean contains(E element) {
        return contains(element,root);
    }

    private boolean contains(E element, Node<E> root) {
        if(root==null){
            return false;
        }
        if(element.compareTo(root.getValue())<0){
            return contains(element,root.getLeft());
        }else if(element.compareTo(root.getValue())>0){
            return contains(element,root.getRight());
        }else{
            return true;
        }
    }

    public BSTExtended<E> search(E element) {
        Node<E> foundNode = search(element, root);
        return foundNode != null ? new BSTExtended<>(foundNode) : null;
    }

    private Node<E> search(E element, Node<E> current) {
        if (current == null || current.getValue().equals(element)) {
            return current;
        }
        int cmp = element.compareTo(current.getValue());
        if (cmp < 0) {
            return search(element, current.getLeft());
        } else {
            return search(element, current.getRight());
        }
    }

    public List<E> range(E lower, E upper) {
        List<E> result = new ArrayList<>();

        if(this.root==null){
            return result;
        }

        Deque<Node<E>> queue = new ArrayDeque<>();

        queue.offer(this.root);

        while (!queue.isEmpty()){
            Node<E> current = queue.poll();

            if(current.getLeft() !=null){
                queue.offer(current.getLeft());
            }
            if(current.getRight() !=null){
                queue.offer(current.getRight());
            }

            int isLess = lower.compareTo(current.getValue());
            int isGreater = upper.compareTo(current.getValue());

            if(isLess < 0 && isGreater>0){
                result.add(current.getValue());
            }else if(isGreater==0 || isLess==0){
                result.add(current.getValue());
            }
        }

        return result;
    }
    public void deleteMin() {
        ensureNonEmpty();
        if(this.root.getLeft()==null){
            this.root=this.root.getRight();
            return;
        }

        Node<E> current = this.root;
        while (current.getLeft().getLeft()!=null){
            current.count-=1;
            current=current.getLeft();
        }
        current.count-=1;
        current.leftChild=current.getLeft().getRight();

    }

    private void ensureNonEmpty() {
        if(this.root == null){
            throw new IllegalStateException();
        }
    }

    public void deleteMax() {
        ensureNonEmpty();
        if(this.root.getRight()==null){
            this.root=this.root.getLeft();
            return;
        }

        Node<E> current = this.root;
        while (current.getRight().getRight()!=null){
            current.count-=1;
            current=current.getRight();
        }
        current.count-=1;
        current.rightChild=current.getRight().getLeft();

    }

    public int count() {
        return this.root == null ? 0 : this.root.getCount();
    }

    public int rank(E element) {
        return nodeRank(root,element);
    }

    private int nodeRank(Node<E> current, E element) {
        if(current==null){
            return 0;
        }
        int cmp = element.compareTo(current.getValue());

        if(cmp<0){
            return nodeRank(current.getLeft(),element);
        }else if(cmp>0){
            // We add the count of the left because in bst on the left are always smaller and we go
            // further to the right to make sure there
            return getNodeCount(current.getLeft()) + 1 + nodeRank(current.getRight(),element);
        }else{
            return getNodeCount(current.getLeft());
        }
    }

    private  int getNodeCount(Node<E> current) {
        return current== null ? 0 : current.getCount();
    }

    public E ceil(E element) {
        // Check if the tree is empty
        if (this.root == null) {
            return null; // If empty, return null
        }

        Node<E> current = this.root; // Start from the root
        Node<E> nearestLarger = null; // Store the nearest larger node

        // Traverse the tree
        while (current != null) {
            int cmp = element.compareTo(current.getValue()); // Compare the given element with the current node's value

            // If the given element is greater than the current node's value
            if (cmp > 0) {
                current = current.getRight(); // Move to the right child
            }
            // If the given element is less than the current node's value
            else if (cmp < 0) {
                nearestLarger = current; // Update nearestLarger to the current node
                current = current.getLeft(); // Move to the left child
            }
            // If the given element is equal to the current node's value
            else {
                Node<E> right = current.getRight(); // Get the right child of the current node

                // If there is a right child and nearestLarger is not null
                if (right != null && nearestLarger != null) {
                    int cmp2 = right.getValue().compareTo(nearestLarger.getValue()); // Compare the right child's value with nearestLarger's value
                    nearestLarger = cmp2 < 0 ? right : nearestLarger; // Update nearestLarger if right child is smaller
                }
                // If nearestLarger is null
                else if (nearestLarger == null) {
                    nearestLarger = right; // Update nearestLarger to the right child
                }
                break; // Break out of the loop
            }
        }

        // Return the value of the nearestLarger node, or null if nearestLarger is null
        return nearestLarger == null ? null : nearestLarger.getValue();
    }


    public E floor(E element) {
        // Check if the tree is empty
        if (this.root == null) {
            return null; // If empty, return null
        }

        Node<E> current = this.root; // Start from the root
        Node<E> nearestSmaller = null; // Store the nearest smaller node

        // Traverse the tree
        while (current != null) {
            int cmp = element.compareTo(current.getValue()); // Compare the given element with the current node's value

            // If the given element is greater than the current node's value
            if (cmp > 0) {
                nearestSmaller = current; // Update nearestSmaller to the current node
                current = current.getRight(); // Move to the right child
            }
            // If the given element is less than the current node's value
            else if (cmp < 0) {
                current = current.getLeft(); // Move to the left child
            }
            // If the given element is equal to the current node's value
            else {
                Node<E> left = current.getLeft(); // Get the left child of the current node

                // If there is a left child and nearestSmaller is not null
                if (left != null && nearestSmaller != null) {
                    int cmp2 = left.getValue().compareTo(nearestSmaller.getValue()); // Compare the left child's value with nearestSmaller's value
                    nearestSmaller = cmp2 > 0 ? left : nearestSmaller; // Update nearestSmaller if left child is greater (we need the closest!!! smaller value )
                }
                // If nearestSmaller is null
                else if (nearestSmaller == null) {
                    nearestSmaller = left; // Update nearestSmaller to the left child
                }
                break; // Break out of the loop
            }
        }
        // Return the value of the nearestSmaller node, or null if nearestSmaller is null
        return nearestSmaller == null ? null : nearestSmaller.getValue();
    }
    public E floorRecursive(E element) {
        return floorRecursive(root, element, null);
    }

    private E floorRecursive(Node<E> current, E element, E floor) {
        if (current == null)
            return floor;

        int cmp = element.compareTo(current.getValue());

        if (cmp < 0) {
            return floorRecursive(current.getLeft(), element, floor);
        } else if (cmp > 0) {
            floor = current.getValue();
            return floorRecursive(current.getRight(), element, floor);
        } else {
            return current.getValue();
        }
    }

    public E ceilRecursive(E element) {
        return ceilRecursive(root, element, null);
    }

    private E ceilRecursive(Node<E> current, E element, E ceil) {
        if (current == null)
            return ceil;

        int cmp = element.compareTo(current.getValue());

        if (cmp < 0) {
            ceil = current.getValue();
            return ceilRecursive(current.getLeft(), element, ceil);
        } else if (cmp > 0) {
            return ceilRecursive(current.getRight(), element, ceil);
        } else {
            return current.getValue();
        }
    }


}
