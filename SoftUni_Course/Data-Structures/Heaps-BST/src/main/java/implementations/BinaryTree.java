package implementations;

import interfaces.AbstractBinaryTree;
import solutions.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class BinaryTree<E> implements AbstractBinaryTree<E> {
    private  E key;
    private final BinaryTree<E> leftChild;

    private final BinaryTree<E> rightChild;

    public BinaryTree(E key, BinaryTree<E> leftChild,BinaryTree<E> rightChild){
        this.setKey(key);
        this.leftChild=leftChild;
        this.rightChild=rightChild;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public AbstractBinaryTree<E> getLeft() {
        return this.leftChild;
    }

    @Override
    public AbstractBinaryTree<E> getRight() {
        return this.rightChild;
    }

    @Override
    public void setKey(E key) {
        this.key=key;
    }

    @Override
    public String asIndentedPreOrder(int indent) {
        StringBuilder builder = new StringBuilder();
        builder.append(createPadding(indent))
                .append(this.getKey());

        if(this.getLeft()!=null) {
            String preOrder = this.getLeft().asIndentedPreOrder(indent + 2);
            builder.append(System.lineSeparator())
                    .append(preOrder);
        }
        if(this.getRight()!=null){
            String preOrder = this.getRight().asIndentedPreOrder(indent + 2);
            builder.append(System.lineSeparator())
                    .append(preOrder);
        }


        return builder.toString();
    }

    private String createPadding(int indent) {
        return " ".repeat(indent);
    }

    @Override
    public List<AbstractBinaryTree<E>> preOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();
        preOrder(result,this);
        return result;
    }

    private void preOrder(List<AbstractBinaryTree<E>> result, BinaryTree<E> current) {
        if(current!=null){
            result.add(current);
            preOrder(result,current.leftChild);
            preOrder(result,current.rightChild);
        }
    }

    @Override
    public List<AbstractBinaryTree<E>> inOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();
        inOrder(result,this);
        return result;
    }

    private void inOrder(List<AbstractBinaryTree<E>> result, BinaryTree<E> current) {
        if(current!=null){
            inOrder(result,current.leftChild);
            result.add(current);
            inOrder(result,current.rightChild);
        }
    }

    @Override
    public List<AbstractBinaryTree<E>> postOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();
        postOrder(result,this);
        return result;
    }

    private void postOrder(List<AbstractBinaryTree<E>> result, BinaryTree<E> current) {
        if(current!=null){
            postOrder(result,current.leftChild);
            postOrder(result,current.rightChild);
            result.add(current);
        }
    }

    @Override
    public void forEachInOrder(Consumer<E> consumer) {
        forEachInOrder(this,consumer);
    }

    private void forEachInOrder(BinaryTree<E> current, Consumer<E> consumer) {
        if(current!=null){
            forEachInOrder(current.leftChild,consumer);
            consumer.accept(current.getKey());
            forEachInOrder(current.rightChild,consumer);
        }
    }

    public E findLowestCommonAncestor(E first, E second) {
        // Find paths from root to the nodes containing 'first' and 'second' values
        List<E> firstPath = findPath(first);
        List<E> secondPath = findPath(second);

        // Find the smaller size between the two paths
        int smallerSize = Math.min(firstPath.size(), secondPath.size());

        // Iterate over the paths until a different node is found
        int i = 0;
        for (; i < smallerSize; i++) {
            if (!firstPath.get(i).equals(secondPath.get(i))) {
                break;
            }
        }

        // Return the value of the last common node (i-1) found in the paths
        return firstPath.get(i - 1);
    }

    // Finds the path from the root to the node containing the given element
    private List<E> findPath(E element) {
        List<E> result = new ArrayList<>();
        // Find the path recursively
        findNodePath(this, element, result);
        return result;
    }

    // Recursive method to find the path from the current node to the node containing the given element
    private boolean findNodePath(BinaryTree<E> binaryTree, E element, List<E> result) {
        // Base case: If the current node is null, return false
        if (binaryTree == null) {
            return false;
        }
        // If the current node contains the element, add it to the path and return true
        if (binaryTree.key.equals(element)) {
            result.add(binaryTree.key);
            return true;
        }
        // Add the current node to the path
        result.add(binaryTree.key);

        // Recursively search in the left subtree
        boolean leftResult = findNodePath(binaryTree.leftChild, element, result);
        // If found in the left subtree, return true
        if (leftResult) {
            return true;
        }
        // Recursively search in the right subtree
        boolean rightResult = findNodePath(binaryTree.rightChild, element, result);
        // If found in the right subtree, return true
        if (rightResult) {
            return true;
        }

        // If the element is not found in the subtree rooted at the current node, remove it from the path and return false
        result.remove(binaryTree.key);
        return false;
    }


    public List<E> topView() {
        // Create a HashMap to store the offset to value and level mappings
        Map<E, Pair<E,E>> offsetToValueLevel = new HashMap();

        // Traverse the tree and populate the offsetToValueLevel map
        traverseTree(this, 0, 1, (Map<Integer, Pair<E, E>>) offsetToValueLevel);

        // Return the values of the map, sorted by their levels
        return offsetToValueLevel.values()
                .stream()
                .map(Pair::getKey)
                .collect(Collectors.toList());
    }

    // Helper method to recursively traverse the tree
    private void traverseTree(BinaryTree<E> binaryTree, int offset, int level, Map<Integer, Pair<E, E>> offsetToValueLevel) {
        // Base case: if the current node is null, return
        if(binaryTree == null) {
            return;
        }

        // Check if there's already a value at the current offset
        Pair<E, E> currentValueLevel = offsetToValueLevel.get(offset);

        // If there's no value or the current level is less than the existing level for this offset,
        // update the value for this offset with the current node's value and level
        if(currentValueLevel == null || level < Integer.parseInt(String.valueOf(currentValueLevel.getValue()))) {
            offsetToValueLevel.put(offset, (Pair<E, E>) new Pair<>(binaryTree.key, level));
        }

        // Recursively traverse the left subtree with a decreased offset and increased level
        traverseTree(binaryTree.leftChild, offset - 1, level + 1, offsetToValueLevel);

        // Recursively traverse the right subtree with an increased offset and increased level
        traverseTree(binaryTree.rightChild, offset + 1, level + 1, offsetToValueLevel);
    }

}
