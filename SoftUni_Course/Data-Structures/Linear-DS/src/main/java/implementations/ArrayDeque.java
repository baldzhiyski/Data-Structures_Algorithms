package implementations;

import interfaces.Deque;

import java.util.Iterator;

public class ArrayDeque<E> implements Deque<E> {
    private final int INITIAL_CAPACITY = 7;
    private int size;
    private int head;
    private int tail;

    private Object[] elements;
    public ArrayDeque(){
        this.elements = new Object[INITIAL_CAPACITY];
        int middle = INITIAL_CAPACITY/2;
        head=tail=middle;

    }

    @Override
    public void add(E element) {
        addLast(element);
    }

    @Override
    public void offer(E element) {
        addLast(element);
    }

    @Override
    public void addFirst(E element) {
        if(this.size==0){
            this.addLast(element);
        }else{
            if(this.head==0){
                this.elements=grow();
            }
            this.elements[--this.head] = element;
            this.size++;
        }
    }

    @Override
    public void addLast(E element) {
        if(this.size==0){
            this.elements[this.tail] = element;
        }else{
            if(this.tail==this.elements.length-1){
                this.elements = grow();
            }
            this.elements[++this.tail] = element;
        }
        this.size++;
    }

    private Object[] grow() {
        int newCapacity = this.elements.length*2 + 1;
        Object[] newElements = new Object[newCapacity];
        int middle = newCapacity/2;

        // We want to set the new in the middle so we need to make small calculation
        int begin = middle - this.size/2;
        int index = this.head;

        // We copy the elements from the previous array in the middle of the new one
        for (int i = begin; index<=this.tail ; i++) {
            newElements[i] = this.elements[index++];
        }

        // We update the head and tail indices
        this.head=begin;
        this.tail = this.head +this.size -1;

        return  newElements;
    }


    @Override
    public void push(E element) {
        addFirst(element);
    }

    @Override
    public void insert(int index, E element) {

        int realIndex = this.head + index;
        this.ensureIndex(realIndex);


        if(realIndex - this.head < this.tail - realIndex){
            insertAndShiftLeft(realIndex-1,element);
        }else{
            insertAndShiftRight(realIndex,element);
        }
    }

    private void insertAndShiftRight(int index , E element) {
        E lastElement = this.getAt(this.tail);
        for (int i = this.tail; i >index; i--) {
            this.elements[i] = this.elements[i-1];
        }
        this.elements[index] = element;
        this.addLast(lastElement);
    }

    private void insertAndShiftLeft( int index , E element) {
        E firstElement = this.getAt(this.head);
        for (int i = this.head; i <=index; i++) {
            this.elements[i] = this.elements[i+1];
        }
        this.elements[index] = element;
        this.addFirst(firstElement);
    }



    @Override
    public void set(int index, E element) {
        ensureIndex(index);

        this.elements[index] = element;
    }

    @Override
    public E peek() {
        if(this.size!=0){
            return this.getAt(this.head);
        }
        return null;
    }

    private E getAt(int index) {
        return (E) this.elements[index];
    }

    @Override
    public E poll() {
        return removeFirst();
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public E get(int index) {
        int realIndex = this.head + index;
        this.ensureIndex(realIndex);
        return getAt(realIndex);
    }

    @Override
    public E get(Object object) {
        if(isEmpty()){
            return null;
        }
        for (int i = this.head; i <=this.tail ; i++) {
            if(this.elements[i].equals(object)){
                return this.getAt(i);
            }
        }
        return null;
    }
   @Override
    public E remove(int index) {
        // Calculate the real index based on the head position
        int realIndex = this.head + index;
        // Ensure that the index is within bounds
        this.ensureIndex(realIndex);

        // Get the element at the specified index
        E result = getAt(realIndex);

        // Shift elements to the left to remove the element at the specified index
        for (int i = realIndex; i < this.tail; i++) {
            this.elements[i] = this.elements[i + 1];
        }

       // Nullify the element at the old tail position
        this.elements[this.tail] =null;
        // Adjust the tail index
        this.tail--;

        // Decrement the size
        this.size--;

        return result;
    }

    private void ensureIndex(int realIndex) {
        if(realIndex>this.tail || realIndex>this.tail){
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public E removeFirst() {
        if(!isEmpty()){
            E element = this.getAt(this.head);
            this.elements[this.head] = null;
            this.head++;
            this.size--;
            return element;
        }
        return null;
    }

    @Override
    public E removeLast() {
        if(!isEmpty()){
            E element = this.getAt(this.tail);
            this.elements[this.tail] = null;
            this.tail--;
            this.size--;
            return element;

        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.elements.length;
    }

    @Override
    public void trimToSize() {
        Object[] newElements = new Object[this.size];
        int index = 0;
        for (int i = this.head; i <= this.tail; i++) {
            newElements[index++] = this.elements[i];
        }
        this.elements=newElements;
    }

    @Override
    public boolean isEmpty() {
        return this.size==0;
    }

    @Override
    public Iterator<E> iterator() {
          return new Iterator<E>() {
            private  int index = head;
            @Override
            public boolean hasNext() {
                return index<=tail;
            }

            @Override
            public E next() {
                return getAt(index++);
            }
        };
    }
}
