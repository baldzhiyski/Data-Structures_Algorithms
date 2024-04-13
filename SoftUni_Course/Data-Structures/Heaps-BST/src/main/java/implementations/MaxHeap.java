package implementations;

import interfaces.Heap;

import java.util.ArrayList;
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
        return this.elements.get(0);
    }
}
