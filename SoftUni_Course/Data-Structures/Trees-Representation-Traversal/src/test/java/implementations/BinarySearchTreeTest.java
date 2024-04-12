package implementations;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BinarySearchTreeTest {
    private BinarySearchTree<Integer> bst;

    @Before
    public void setUp() {
        bst = new BinarySearchTree<>();
    }

    @Test
    public void testInsertionAndTraversal() {
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);

        // Test inorder traversal
        assertEquals("Inorder traversal", "20 30 40 50 60 70 80 ", bst.inorderTraversalString());
    }

    @Test
    public void testRecursiveDeletion() {
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);

        // Delete nodes using recursive deletion
        bst.delete(20); // Node with no children
        bst.delete(30); // Node with one child
        bst.delete(70); // Node with two children

        // Test inorder traversal after deletion
        assertEquals("Inorder traversal after recursive deletion", "40 50 60 80 ", bst.inorderTraversalString());
    }

    @Test
    public void testIterativeDeletion() {
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);

        // Iterative deletion
        bst.deleteIterative(40); // Node with no children
        bst.deleteIterative(50); // Root node with two children

        // Test inorder traversal after iterative deletion
        assertEquals("Inorder traversal after iterative deletion", "20 30 60 70 80 ", bst.inorderTraversalString());
    }
}
