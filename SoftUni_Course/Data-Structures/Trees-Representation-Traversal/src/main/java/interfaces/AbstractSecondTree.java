package interfaces;

import implementations.SecondTree;
import implementations.Tree;

import java.util.List;

public interface AbstractSecondTree<E> {
    void setParent(SecondTree<E> parent);

    void addChild(SecondTree<E> child);

    SecondTree<E> getParent();

    E getKey();

    String getAsString();

    List<E> getLeafKeys();

    List<E> getMiddleKeys();

    SecondTree<E> getDeepestLeftmostNode();

    List<E> getLongestPath();

    List<List<E>> pathsWithGivenSum(int sum);

    List<SecondTree<E>> subTreesWithGivenSum(int sum);
}
