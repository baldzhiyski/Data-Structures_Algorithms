package implementations;

import interfaces.AbstractBinarySearchTree;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BinarySearchTreeTest {
    private BinarySearchTree<Integer> bst;

    @Before
    public void setUp() {
        bst = new BinarySearchTree<>();
        bst.insert(12);
        bst.insert(21);
        bst.insert(5);
        bst.insert(1);
        bst.insert(8);
        bst.insert(18);
        bst.insert(23);
    }

    @Test
    public void testLeftSideBST() {
        AbstractBinarySearchTree.Node<Integer> root = bst.getRoot();

        assertEquals(Integer.valueOf(12), root.value);

        AbstractBinarySearchTree.Node<Integer> left = root.leftChild;

        assertEquals(Integer.valueOf(5), left.value);

        AbstractBinarySearchTree.Node<Integer> left_left = left.leftChild;
        AbstractBinarySearchTree.Node<Integer> left_right = left.rightChild;

        assertEquals(Integer.valueOf(1), left_left.value);
        assertEquals(Integer.valueOf(8), left_right.value);
    }

    @Test
    public void testRightSideBST() {
        AbstractBinarySearchTree.Node<Integer> root = bst.getRoot();

        assertEquals(Integer.valueOf(12), root.value);

        AbstractBinarySearchTree.Node<Integer> right = root.rightChild;

        assertEquals(Integer.valueOf(21), right.value);

        AbstractBinarySearchTree.Node<Integer> right_left = right.leftChild;
        AbstractBinarySearchTree.Node<Integer> right_right = right.rightChild;

        assertEquals(Integer.valueOf(18), right_left.value);
        assertEquals(Integer.valueOf(23), right_right.value);
    }

    @Test
    public void testSearchCheckReturnedTreeStructure() {
        AbstractBinarySearchTree<Integer> searched = bst.search(5);

        AbstractBinarySearchTree.Node<Integer> root = searched.getRoot();
        assertEquals(Integer.valueOf(5), root.value);

        AbstractBinarySearchTree.Node<Integer> left = root.leftChild;
        AbstractBinarySearchTree.Node<Integer> right = root.rightChild;

        assertEquals(Integer.valueOf(1), left.value);
        assertEquals(Integer.valueOf(8), right.value);
    }
    @Test
    public void testContains() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        // Insert elements into the tree
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(3);
        tree.insert(7);
        tree.insert(12);
        tree.insert(20);

        // Test contains method with existing elements
        assertTrue(tree.contains(10));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(15));
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(7));
        assertTrue(tree.contains(12));
        assertTrue(tree.contains(20));

        // Test contains method with non-existing elements
        assertFalse(tree.contains(2));
        assertFalse(tree.contains(8));
        assertFalse(tree.contains(11));
        assertFalse(tree.contains(18));
        assertFalse(tree.contains(25));
    }
}