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
            return getNodeCount(current.getLeft()) + 1 + nodeRank(current.getRight(),element);
        }else{
            return getNodeCount(current.getLeft());
        }
    }

    private  int getNodeCount(Node<E> current) {
        return current== null ? 0 : current.getCount();
    }

    public E ceil(E element) {
        if(this.root==null){
            return null;
        }

        Node<E> current = this.root;
        Node<E> nearestLarger = null;

        while (current!=null) {
            int cmp = element.compareTo(current.getValue());
            if (cmp > 0) {
                current = current.getRight();
            } else if (cmp < 0) {
                nearestLarger=current;
                current = current.getLeft();
            }else{
                Node<E> right = current.getRight();
                if(right !=null && nearestLarger!=null){
                    int cmp2 = right.getValue().compareTo(nearestLarger.getValue());
                    nearestLarger = cmp2<0 ? right : nearestLarger;
                }else if (nearestLarger==null){
                    nearestLarger=right;
                }
                break;
            }
        }
        return nearestLarger==null ? null :nearestLarger.getValue();
    }

    public E floor(E element) {
        if(this.root==null){
            return null;
        }

        Node<E> current = this.root;
        Node<E> nearestSmaller = null;

        while (current!=null) {
            int cmp = element.compareTo(current.getValue());
            if (cmp > 0) {
                nearestSmaller=current;
                current = current.getRight();
            } else if (cmp < 0) {
                current = current.getLeft();
            }else{
                Node<E> left = current.getLeft();
                if(left !=null && nearestSmaller!=null){
                    int cmp2 = left.getValue().compareTo(nearestSmaller.getValue());
                    nearestSmaller = cmp2>0 ? left : nearestSmaller;
                }else if (nearestSmaller==null){
                    nearestSmaller=left;
                }
                break;
            }
        }
        return nearestSmaller==null ? null :nearestSmaller.getValue();
    }
}
