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
        while (index > 0 && isLess(index,getParentIndex(index))){
            Collections.swap(this.elements,index,getParentIndex(index));
            index=getParentIndex(index);
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
        if(this.elements.isEmpty()){
            throw new IllegalStateException("Heap is empty !");
        }
        return this.elements.get(0);
    }

    @Override
    public E poll() {
        return null;
    }
}
