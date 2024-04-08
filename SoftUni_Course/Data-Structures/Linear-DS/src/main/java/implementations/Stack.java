package implementations;

import interfaces.AbstractStack;

import java.util.Iterator;

public class Stack<E> implements AbstractStack<E> {

    private Node<E> top;
    private int size;

    private static class Node<E>{
        private E value;
        private Node<E> next;
        Node(E element){
            this.value = element;
        }
    }

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    @Override
    public void push(E element) {

        Node<E> toInsert = new Node<>(element);

        // Set the 'next' reference of the new node to point to the current top of the stack
        toInsert.next = top;

        // Update the 'top' reference to point to the newly inserted node,
        // making it the new top of the stack
        this.top = toInsert;


        this.size++;
    }

    @Override
    public E pop() {
        ensureNotEmpty();

        Node<E> lastElTmp = top;
        top = lastElTmp.next;

        this.size--;
        return lastElTmp.value;
    }

    private void ensureNotEmpty() {
        if(this.isEmpty()){
            throw new IllegalStateException();
        }
    }

    @Override
    public E peek() {
        ensureNotEmpty();

        return this.top.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private Node<E> current = top;
            @Override
            public boolean hasNext() {
                return this.current !=null;
            }

            @Override
            public E next() {
                E value = this.current.value;
                this.current = this.current.next;

                return value;
            }
        };
    }
}
