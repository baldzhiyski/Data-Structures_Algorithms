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

    private void heapifyDown(int index) {
        // Loop until the current node is not a leaf node
        while (index < this.elements.size() / 2) {
            // Calculate the index of the left child
            int child = getLeftChildIndex(index);

            // Check if the right child exists and is larger than the left child
            if (getRightChildIndex(index)< this.elements.size() && isLess(child, getRightChildIndex(index))) {
                // If so, update the child index to point to the right child
                child = getRightChildIndex(index);
            }

            // Compare the larger child with the parent node
            if (isLess(child, index)) {
                // If the parent is larger than or equal to the larger child, break out of the loop
                break;
            }

            // Swap the parent with the larger child to maintain the max heap property
            Collections.swap(this.elements, index, child);

            // Update the index to continue the heapification process downward
            index = child;
        }
    }
}
