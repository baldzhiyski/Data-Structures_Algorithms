package implementations;

import interfaces.AbstractSecondTree;

import java.util.*;
import java.util.stream.Collectors;

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
        this.parent = parent;
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

        traverseTreeWithRecurrence(builder, 0, this);

        return builder.toString().trim();
    }

    private void traverseTreeWithRecurrence(StringBuilder builder, int level, SecondTree<E> treeBegin) {

        // If there are no more children it will return

        builder.append(getPadding(level))
                .append(treeBegin.getKey())
                .append(System.lineSeparator());

        for (SecondTree<E> child : treeBegin.children) {
            traverseTreeWithRecurrence(builder, level + 2, child);
        }
    }

    private void traverseTreeWithRecurrenceMiddleLeaves(List<SecondTree<E>> collection, SecondTree<E> treeBegin) {
        collection.add(treeBegin);
        for (SecondTree<E> child : treeBegin.children) {
            traverseTreeWithRecurrenceMiddleLeaves(collection, child);
        }
    }

    public List<SecondTree<E>> traverseWithBFSGetLeaves() {
        Deque<SecondTree<E>> deque = new ArrayDeque<>();

        deque.offer(this);
        int level = 0;

        List<SecondTree<E>> allNodes = new ArrayList<>();

        while (!deque.isEmpty()) {
            SecondTree<E> tree = deque.poll();

            allNodes.add(tree);
            for (SecondTree<E> child : tree.children) {
                deque.offer(child);
            }
        }

        return allNodes;

    }

    private String getPadding(int level) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < level; i++) {
            res.append(" ");
        }
        return res.toString();
    }

    /*
      Get the keys that are on the last level ordered
     */
    @Override
    public List<E> getLeafKeys() {
        return traverseWithBFSGetLeaves()
                .stream()
                .filter(tree -> tree.children.isEmpty())
                .map(SecondTree::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<E> getMiddleKeys() {
        List<SecondTree<E>> allNodes = new ArrayList<>();
        this.traverseTreeWithRecurrenceMiddleLeaves(allNodes,this);
        return allNodes
                .stream()
                .filter(tree-> tree.parent!=null && tree.children.size()>0)
                .map(SecondTree::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public SecondTree<E> getDeepestLeftmostNode() {
        return getNodeWithBFSTravercel();
    }

    private SecondTree<E> getNodeWithBFSTravercel() {
        List<SecondTree<E>> trees = this.traverseWithBFSGetLeaves();


        int maxPath = 0;
        SecondTree<E> deepestLeftNode = null;
        for (SecondTree<E> tree : trees) {
            if(tree.isLeaf()){
               int currentPath = getStepsFromLeafToRoot(tree);
               if(currentPath > maxPath){
                   maxPath=currentPath;
                   deepestLeftNode=tree;
               }
            }
        }

        return deepestLeftNode;
    }

    private int getStepsFromLeafToRoot(SecondTree<E> tree) {
        int counter = 0;
        SecondTree<E> current = tree;
        while (current.parent !=null){
            counter++;
            current=current.parent;
        }

        return counter;
    }

    private boolean isLeaf() {
        return this.parent!=null && this.children.isEmpty();
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


