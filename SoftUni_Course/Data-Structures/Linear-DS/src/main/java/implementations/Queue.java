package implementations;

import interfaces.AbstractQueue;

import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {

    private Node<E> head;
    private int size;

    public Queue(){
        this.head = null;
        this.size =0;
    }
    private static class Node<E>{
        private E value;
        private Node<E> next;

        Node(E element){
            this.value= element;
        }
    }

    @Override
    public void offer(E element) {
        Node<E> toInsert = new Node<>(element);

        if(this.isEmpty()){
            this.head=toInsert;
            size++;
            return;
        }
        Node<E> current = this.head;

        // Traverse the list to find the last node
        while (current.next !=null){
            current = current.next;
        }
        // Set the 'next' reference of the last node to point to the new node
        current.next = toInsert;

        size++;

    }

    @Override
    public E poll() {
        ensureNotEmpty();
        Node<E> firstEl = this.head;
        this.head = firstEl.next;

        this.size--;

        return firstEl.value;
    }

    private void ensureNotEmpty() {
        if(this.isEmpty()){
            throw new IllegalStateException();
        }
    }

    @Override
    public E peek() {
        ensureNotEmpty();

        return this.head.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current =head;
            @Override
            public boolean hasNext() {
                return current !=null;
            }

            @Override
            public E next() {
                E value = current.value;

                current= current.next;

                return value;
            }
        };
    }
}
