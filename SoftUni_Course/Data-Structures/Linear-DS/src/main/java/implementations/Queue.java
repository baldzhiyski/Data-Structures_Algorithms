package implementations;

import interfaces.AbstractQueue;

import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public Queue(){
        this.head = null;
        this.tail=null;
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
            this.head=this.tail=toInsert;
            size++;
            return;
        }
       this.tail.next = toInsert;
        this.tail = toInsert;


        size++;

    }

    @Override
    public E poll() {
        ensureNotEmpty();
        Node<E> firstEl = this.head;

        if(this.size == 1){
            this.head=this.tail = null;
        }else {

            Node<E> next = this.head.next;
            this.head.next = null;
            this.head = next;


        }
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
