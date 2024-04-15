package implementations;

import interfaces.AbstractBinaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
        List<E> firstPath = findPath(first);
        List<E> secondPath = findPath(second);

        int smallerSize = Math.min(firstPath.size(), secondPath.size());

        int i = 0;
        for (; i < smallerSize; i++) {
            if(!firstPath.get(i).equals(secondPath.get(i))){
                break;
            }
        }


        return firstPath.get(i-1);
    }

    private List<E> findPath(E element) {
        List<E> result = new ArrayList<>();
        findNodePath(this,element,result);

        return result;
    }

    private boolean findNodePath(BinaryTree<E> binaryTree, E element, List<E> result) {
        if(binaryTree==null){
            return false;
        }
        if(binaryTree.key==element){
            return true;
        }
        result.add(binaryTree.key);

        boolean leftResult = findNodePath(binaryTree.leftChild, element, result);
        if(leftResult) {
            return true;
        }
        boolean rightResult = findNodePath(binaryTree.rightChild, element, result);
        if(rightResult){
            return true;
        }

        result.remove((E)binaryTree.key);
        return false;
    }

    public List<E> topView() {
        return null;
    }
}
