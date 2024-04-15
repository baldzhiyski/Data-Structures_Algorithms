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
        this.heapifyDown();

        return toReturn;
    }

    private void heapifyDown() {
        int index = 0; // Start from the root node
        int swapIndex = getLeftChildIndex(index); // Initialize the swap index to the left child of the root

        // Continue heapifying down as long as the current node has at least one child
        while (swapIndex < this.data.size()) {
            int rightChildIndex = getRightChildIndex(index); // Get the index of the right child

            // Determine which child (left or right) to swap with
            if (rightChildIndex < this.data.size()) { // If the right child exists
                swapIndex = isLess(swapIndex, rightChildIndex) ? swapIndex : rightChildIndex; // Choose the smaller of the two children
            }

            // If the current node is less than or equal to its smallest child, no need to swap further
            if (isLess(index, swapIndex)) {
                break;
            }

            // Swap the current node with its smallest child
            Collections.swap(this.data, index, swapIndex);

            // Move down the tree to the position of the smallest child
            index = swapIndex;

            // Update the swap index to the left child of the new current node
            swapIndex = getLeftChildIndex(index);
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
