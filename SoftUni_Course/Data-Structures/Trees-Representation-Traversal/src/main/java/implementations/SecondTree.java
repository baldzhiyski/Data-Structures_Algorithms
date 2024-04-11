package implementations;

import interfaces.AbstractSecondTree;

import java.util.ArrayList;
import java.util.List;

public class SecondTree<E> implements AbstractSecondTree<E> {

    private E key;
    private SecondTree<E> parent;
    private List<SecondTree<E>> children;

    public SecondTree(E value, SecondTree<E>... subtrees) {
        this.key = value;
        this.parent = null;
        this.children = new ArrayList<>();

        for (SecondTree<E> subtree : subtrees) {
            this.children.add(subtree);
            setParent(this);
        }
    }

    @Override
    public void setParent(SecondTree<E> parent) {
        this.parent=parent;
    }

    @Override
    public void addChild(SecondTree<E> child) {
        this.children.add(child);

    }

    @Override
    public SecondTree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public String getAsString() {
        StringBuilder builder = new StringBuilder();

        traverseTreeWithRecurrence(builder,0,this);

        return builder.toString().trim();
    }

    private void traverseTreeWithRecurrence(StringBuilder builder, int level, SecondTree<E> treeBegin) {

        // If there are no more children it will return

        builder.append(getPadding(level))
                .append(treeBegin.getKey())
                .append(System.lineSeparator());

        for (SecondTree<E> child : treeBegin.children) {
            traverseTreeWithRecurrence(builder,level+2,child);
        }
    }

    private String getPadding(int level) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < level; i++) {
            res.append(" ");
        }
        return res.toString();
    }

    @Override
    public List<E> getLeafKeys() {
        return null;
    }

    @Override
    public List<E> getMiddleKeys() {
        return null;
    }

    @Override
    public SecondTree<E> getDeepestLeftmostNode() {
        return null;
    }

    @Override
    public List<E> getLongestPath() {
        return null;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        return null;
    }

    @Override
    public List<SecondTree<E>> subTreesWithGivenSum(int sum) {
        return null;
    }
}


