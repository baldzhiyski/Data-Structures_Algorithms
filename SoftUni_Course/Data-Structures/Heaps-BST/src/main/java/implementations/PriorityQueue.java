package implementations;

import interfaces.AbstractQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriorityQueue<E extends Comparable<E>> implements AbstractQueue<E> {

    private List<E> elements;
    public PriorityQueue(){
        this.elements = new ArrayList<>();
    }
    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void add(E element) {
        this.elements.add(element);
        this.heapifyUp(this.size()-1);

    }

    private void heapifyUp(int index) {
        while (index > 0 && isLess(getParentIndex(index),index)){
            Collections.swap(this.elements,index,getParentIndex(index));
            index=getParentIndex(index);
        }
    }

    private boolean isLess(int first, int second) {
        return getAt(first).compareTo(getAt(second)) < 0;
    }

    private E getAt(int index){
        return this.elements.get(index);
    }

    private int getParentIndex(int index) {
        return (index-1 )/ 2;
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.elements.get(0);
    }

    private void ensureNonEmpty() {
        if(this.elements.isEmpty()){
            throw new IllegalStateException("Heap is empty !");
        }
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        E element = this.elements.get(0);
        Collections.swap(this.elements, 0, this.elements.size() - 1);
        this.elements.remove(this.elements.size() - 1);
        this.heapifyDown(0);
        return element;
    }

    private E getLeftChild(int index){
        return getAt(getLeftChildIndex(index));
    }

    private E getRightChild(int index){
        return getAt(getRightChildIndex(index));
    }


    private int getLeftChildIndex(int index){
        return 2*index + 1;
    }

    private int getRightChildIndex(int index){
        return 2*index + 2;
    }

    // HeapifyDown like MaxHeap
    private void heapifyDown(int index) {
        int largest = index;
        int leftChild = getLeftChildIndex(index);
        int rightChild = getRightChildIndex(index);

        // Find the largest among the current node, left child, and right child
        if (leftChild < elements.size() && isLess(largest, leftChild)) {
            largest = leftChild;
        }
        if (rightChild < elements.size() && isLess(largest, rightChild)) {
            largest = rightChild;
        }

        // If the largest element is not the current node, swap and continue heapifying down
        if (largest != index) {
            Collections.swap(elements, index, largest);
            // After changing them we continue in order not to violate the max heap property
            heapifyDown(largest);
        }
    }
}
