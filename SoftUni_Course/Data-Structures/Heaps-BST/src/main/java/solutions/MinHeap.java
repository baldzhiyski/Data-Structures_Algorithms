package solutions;

import interfaces.Decrease;
import interfaces.HeapSec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinHeap<E extends Comparable<E> & Decrease<E>> implements HeapSec<E> {

    private List<E> data;

    public MinHeap(){
        data = new ArrayList<>();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public void add(E element) {
        this.data.add(element);
        this.heapifyUp(this.size()-1);
    }

    private void heapifyUp(int index) {
        int parentIndex = this.getParentIndex(index);

        while (index>0 && isLess(index,parentIndex)){
            Collections.swap(this.data,index,parentIndex);
            index = parentIndex;
            parentIndex = this.getParentIndex(index);
        }
    }

    private boolean isLess(int first, int second) {
        return this.data.get(first).compareTo(this.data.get(second)) < 0;
    }

    private int getParentIndex(int index) {
        return (index-1) /2;
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.data.get(0);
    }

    private void ensureNonEmpty() {
        if(this.data.isEmpty()){
            throw new IllegalStateException();
        }
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        Collections.swap(this.data,0,data.size()-1);
        E toReturn = this.data.remove(this.data.size() - 1);
        this.heapifyDown(0);

        return toReturn;
    }

    private void heapifyDown(int index) {
        int smallestIndex = index; // Assume the current node is the smallest
        int leftChildIndex = getLeftChildIndex(index); // Get the index of the left child

        // If the left child exists and it's smaller than the current smallest node
        if (leftChildIndex < this.data.size() && isLess(leftChildIndex, smallestIndex)) {
            smallestIndex = leftChildIndex; // Update the smallest index to the left child's index
        }

        int rightChildIndex = getRightChildIndex(index); // Get the index of the right child

        // If the right child exists and it's smaller than the current smallest node
        if (rightChildIndex < this.data.size() && isLess(rightChildIndex, smallestIndex)) {
            smallestIndex = rightChildIndex; // Update the smallest index to the right child's index
        }

        // If the smallest node is not the current node
        if (smallestIndex != index) {
            // Swap the current node with the smallest child
            Collections.swap(this.data, index, smallestIndex);

            // Recursively heapify down at the position of the smallest child
            heapifyDown(smallestIndex);
        }
    }
    private int getLeftChildIndex(int index) {
        return 2*index +1;
    }
    private int getRightChildIndex(int index) {
        return 2*index +2;
    }

    @Override
    public void decrease(E element) {
        int elementIndex = this.data.indexOf(element);
        E heapEl = this.data.get(elementIndex);

        heapEl.decrease();

        this.heapifyUp(elementIndex);
    }
}
