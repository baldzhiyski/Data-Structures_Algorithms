package interfaces;

import implementations.AVLTreeImpl;
import implementations.TreeNode;

public interface AVLTree<T> {
    AVLTree<T> insert (T data);

    void delete(T data);

    void traverse();

    T getMax();

    T getMin();

    boolean isEmpty();


}
