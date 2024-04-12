package implementations;

import interfaces.AbstractTree;

import java.util.*;

public class Tree<E> implements AbstractTree<E> {

    private E value;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E value, Tree<E>... subtrees) {
        this.value = value;
        this.parent = null;
        this.children = new ArrayList<>();

        for (Tree<E> subtree : subtrees) {
            this.children.add(subtree);
            subtree.parent = this;
        }
    }

    @Override
    public List<E> orderBfs() {
        ArrayList<E> result = new ArrayList<>();
        if(this.value == null){
            return result;
        }
        ArrayDeque<Tree<E>> childrenQueue = new ArrayDeque<>();

        // We need to call the current tree  which we are working on.
        childrenQueue.offer(this);
        while (!childrenQueue.isEmpty()) {
            Tree<E> current = childrenQueue.poll();

            result.add(current.value);
            for (Tree<E> child : current.children) {
                childrenQueue.offer(child);
            }
        }

        return result;
    }

    @Override
    public List<E> orderDfs() {
        ArrayList<E> result = new ArrayList<>();

        this.doDfs(this, result);

        return result;
    }

    private void doDfs(Tree<E> node, ArrayList<E> result) {
        for (Tree<E> child : node.children) {
            doDfs(child, result);
        }
        // When there are no more children , we add the value to the list and then it will return one level up to the next child
        result.add(node.value);
    }

    @Override
    public List<E> orderDfsStack() {
        return null;
    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {
        Tree<E> search = findRecursive(this,parentKey);

        if (search == null) {
            throw new IllegalStateException("No such parent found !");
        }

        search.children.add(child);
        child.parent = search;
    }

    private Tree<E> findBfs(E parentKey) {
        ArrayDeque<Tree<E>> childrenQueue = new ArrayDeque<>();

        // We need to call the current tree  which we are working on.
        childrenQueue.offer(this);
        while (!childrenQueue.isEmpty()) {
            Tree<E> current = childrenQueue.poll();

            if (current.value.equals(parentKey)) {
                return current;
            }
            for (Tree<E> child : current.children) {
                childrenQueue.offer(child);
            }
        }

        return null;

    }

    private Tree<E> findRecursive(Tree<E> current, E parentKey) {

        if (current.value.equals(parentKey)) {
            return current;
        }


        for (Tree<E> child : current.children) {
            Tree<E> found = this.findRecursive(child, parentKey);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    @Override
    public void removeNode(E nodeKey) {
        Tree<E> toRemove = findBfs(nodeKey);

        if(toRemove==null){
            throw new IllegalArgumentException("No such node to remove !");
        }

        for (Tree<E> child : toRemove.children) {
            child.parent=null;
        }
        toRemove.children.clear();

        Tree<E> parent = toRemove.parent;
        if(parent!=null) {
            parent.children.remove(toRemove);
        }

        toRemove.value=null;
    }

    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> firstNode =findBfs(firstKey);
        Tree<E> secondNode = findBfs(secondKey);

        if(firstNode==null || secondNode==null){
            throw new IllegalArgumentException();
        }

        Tree<E> firstParent = firstNode.parent;
        Tree<E> secondParent = secondNode.parent;

        if(firstParent == null){
            swapRoot(secondNode);
            secondParent.parent=null;
            return;
        }

        if(secondParent==null){
            swapRoot(firstNode);
            firstParent.parent=null;
            return;
        }

        // Update the parents
        firstNode.parent = secondParent;
        secondNode.parent =firstParent;

        // Update the children collection of  the node by getting the indexes

        int firstIndex = firstParent.children.indexOf(firstNode);
        int secondIndex = secondParent.children.indexOf(secondNode);

        firstParent.children.set(firstIndex,secondNode);
        secondParent.children.set(secondIndex,firstNode);

    }

    private void swapRoot(Tree<E> node) {
        this.value= node.value;
        this.parent = null;
        this.children = node.children;
    }

}



