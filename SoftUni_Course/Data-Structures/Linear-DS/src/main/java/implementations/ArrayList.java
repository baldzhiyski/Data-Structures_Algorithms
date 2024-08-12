package implementations;

import interfaces.List;

import java.util.Iterator;
import java.util.Objects;

public class ArrayList<E> implements List<E> {
    private Object[] elements;
    private static final Integer INITIAL_SIZE = 4;
    private Integer size;
    private Integer capacity;

    public ArrayList(){
        this.elements = new Object[INITIAL_SIZE];
        this.size = 0;
        this.capacity= INITIAL_SIZE;
    }

    @Override
    public boolean add(E element) {
        if(notEnoughCapacity()){
            grow(elements);
        }

        this.elements[this.size++]=element;
        return true;
    }

    private void grow(Object[] elements) {
        this.capacity *= 2;
        Object[] result = new Object[capacity];

        System.arraycopy(this.elements, 0, result, 0, elements.length);

        this.elements = result;
    }

    private boolean notEnoughCapacity() {
        return this.size == this.elements.length;
    }

    @Override
    public boolean add(int index, E element) {
        if(!validIndex(index)){
            return false;
        }

        E lastEl = (E) this.elements[size - 1];


        shiftRight(index);

        this.elements[index] = element;
        this.elements[size] = lastEl;
        this.size++;

        return true;
    }

    private void shiftRight(int index) {
        if(notEnoughCapacity()){
            grow(elements);
        }

        // Shift elements to the right starting from the last element
        for (int i = this.size; i > index; i--) {
            this.elements[i] = this.elements[i - 1];
        }

    }

    @Override
    public E get(int index) {
        if(!validIndex(index)){
             throw new IndexOutOfBoundsException(String.format("Cannot get index %d ! Invalid index !",index));
        }

        return (E) this.elements[index];
    }

    private boolean validIndex(int index) {
        return index >= 0 && index < size;
    }

    @Override
    public E set(int index, E element) {
        if(!validIndex(index)){
            throw new IndexOutOfBoundsException();
        }

        this.elements[index] = element;

        return element;
    }

    @Override
    public E remove(int index) {
        if (!validIndex(index)) {
            throw new IndexOutOfBoundsException();
        }

        // Store the element to be removed
        E elementToRemove = (E) this.elements[index];

        // Shift elements to the left to fill the gap
        for (int i = index; i < this.size - 1; i++) {
            this.elements[i] = this.elements[i + 1];
        }

        // Decrease the size
        this.size--;
        this.elements[size] = null;


        ensureCapacity();

        return elementToRemove;
    }

    private void ensureCapacity() {
        // Optionally shrink the size of the array if needed
        if (this.size < this.elements.length / 3) {
            shrinkSize(this.capacity/2);
        }
    }

    private void shrinkSize(int newCapacity) {
        Object[] newElements = new Object[newCapacity];
        System.arraycopy(this.elements, 0, newElements, 0, this.size);
        this.elements = newElements;
        this.capacity = newCapacity;
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        if(this.elements.length < 1){
            return -1;
        }

        for (int i = 0; i < size; i++) {
            if(this.elements[i].equals(element)){
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (this.elements[i].equals(element)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.size== 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public E next() {
                return get(index++);
            }
        };
    }
}
