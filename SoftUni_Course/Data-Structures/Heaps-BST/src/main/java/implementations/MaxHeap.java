package implementations;

import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
/*
  A completed binary tree ( we insert top to bottom , left to right)  for which parent >= children
  We need our elements to be comparable
 */
public class MaxHeap<E extends Comparable<E>> implements Heap<E> {
    private List<E> elements;

    public MaxHeap(){
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return elements.size();
    }

    /*
    By adding elements to the list sequentially and performing heapify operations to maintain the heap property after each addition,
    the implementation naturally achieves the "top to bottom, left to right" insertion pattern characteristic of a heap,
    because we use a list and we add at the end( the end layer of the tree)
     */
    @Override
    public void add(E element) {
        this.elements.add(element);
        this.heapifyUp(this.size()-1);

    }

    private void heapifyUp(int index) {
        int parentIndex = getParentIndex(index);
        while (index > 0 && isLess(index,parentIndex)){
            Collections.swap(this.elements,index,parentIndex);
            index=parentIndex;
        }
    }

    private boolean isLess(int childIndex, int parentIndex) {
        return getAt(childIndex).compareTo(getAt(parentIndex)) > 0;
    }

    private E getAt(int index){
        return this.elements.get(index);
    }

    private int getParentIndex(int index) {
        return (index-1 )/ 2;
    }

    @Override
    public E peek() {
        if(getAt(0)==null){
            throw new IllegalStateException("Heap is empty !");
        }
        return this.elements.get(0);
    }
    // Bulk insertion method
    public void addBulk(Collection<E> collection) {
        // Add all elements from the collection to the end of the list
        elements.addAll(collection);

        // Restore the heap property
        int startIndex = getParentIndex(elements.size() - 1); // Start from the parent of the last element
        for (int i = startIndex; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    // Helper method to restore the heap property by heapifying down
    private void heapifyDown(int index) {
        int largest = index;
        int leftChild = getLeftChildIndex(index);
        int rightChild = getRightChildIndex(index);

        // Find the largest among the current node, left child, and right child
        if (leftChild < elements.size() && isLess(leftChild, largest)) {
            largest = leftChild;
        }
        if (rightChild < elements.size() && isLess(rightChild, largest)) {
            largest = rightChild;
        }

        // If the largest element is not the current node, swap and continue heapifying down
        if (largest != index) {
            Collections.swap(elements, index, largest);
            heapifyDown(largest);
        }
    }
    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }
}
