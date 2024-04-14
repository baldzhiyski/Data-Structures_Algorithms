package interfaces;

public interface HeapSec<E extends Comparable<E>> {
    int size();
    void add(E element);
    E peek();
    E poll();
    void decrease(E element);
}
