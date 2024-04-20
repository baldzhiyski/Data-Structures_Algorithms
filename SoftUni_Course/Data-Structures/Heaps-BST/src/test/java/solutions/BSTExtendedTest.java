package solutions;

import implementations.BSTExtended;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BSTExtendedTest {

    @Test
    public void testCreate() {
        BSTExtended<Integer> bst = new BSTExtended<>(5);

        assertEquals(Integer.valueOf(5), bst.getRoot().getValue());
    }

    @Test
    public void testInsert() {
        BSTExtended<Integer> bst = new BSTExtended<>(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);
        bst.insert(12);
        bst.insert(17);

        assertEquals(Integer.valueOf(10), bst.getRoot().getValue());
        assertEquals(Integer.valueOf(5), bst.getRoot().getLeft().getValue());
        assertEquals(Integer.valueOf(15), bst.getRoot().getRight().getValue());
        assertEquals(Integer.valueOf(3), bst.getRoot().getLeft().getLeft().getValue());
        assertEquals(Integer.valueOf(7), bst.getRoot().getLeft().getRight().getValue());
        assertEquals(Integer.valueOf(12), bst.getRoot().getRight().getLeft().getValue());
        assertEquals(Integer.valueOf(17), bst.getRoot().getRight().getRight().getValue());
    }

    @Test
    public void testInsertSecond() {
        BSTExtended<Integer> bst = new BSTExtended<>(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(6);
        bst.insert(1);
        bst.insert(17);

        assertEquals(Integer.valueOf(3), bst.getRoot().getLeft().getValue());
        assertEquals(Integer.valueOf(7), bst.getRoot().getRight().getValue());
        assertEquals(Integer.valueOf(6), bst.getRoot().getRight().getLeft().getValue());

    }

    @Test
    public void testEachInOrder() {
        BSTExtended<Integer> bst = new BSTExtended<>(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);
        bst.insert(12);
        bst.insert(17);

        StringBuilder result = new StringBuilder();
        bst.eachInOrder(result::append);

        assertEquals("35710121517", result.toString());
    }

    @Test
    public void testContains() {
        BSTExtended<Integer> bst = new BSTExtended<>(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);
        bst.insert(12);
        bst.insert(17);

        assertTrue(bst.contains(10));
        assertTrue(bst.contains(5));
        assertTrue(bst.contains(15));
        assertTrue(bst.contains(3));
        assertTrue(bst.contains(7));
        assertTrue(bst.contains(12));
        assertTrue(bst.contains(17));
        assertFalse(bst.contains(1));
        assertFalse(bst.contains(8));
        assertFalse(bst.contains(13));
    }

    @Test
    public void testSearch() {
        BSTExtended<Integer> bst = new BSTExtended<>(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);
        bst.insert(12);
        bst.insert(17);

        // Searching for the root element
        BSTExtended<Integer> result1 = bst.search(10);
        assertNotNull(result1);
        assertEquals(Integer.valueOf(10), result1.getRoot().getValue());

        // Checking left and right children of the root
        assertEquals(Integer.valueOf(5), result1.getRoot().getLeft().getValue());
        assertEquals(Integer.valueOf(15), result1.getRoot().getRight().getValue());

        // Searching for elements in the left subtree
        BSTExtended<Integer> result2 = bst.search(5);
        assertNotNull(result2);
        assertEquals(Integer.valueOf(5), result2.getRoot().getValue());

        // Checking left and right children of 5
        assertEquals(Integer.valueOf(3), result2.getRoot().getLeft().getValue());
        assertEquals(Integer.valueOf(7), result2.getRoot().getRight().getValue());

        // Searching for elements in the right subtree
        BSTExtended<Integer> result3 = bst.search(15);
        assertNotNull(result3);
        assertEquals(Integer.valueOf(15), result3.getRoot().getValue());

        // Checking left and right children of 15
        assertEquals(Integer.valueOf(12), result3.getRoot().getLeft().getValue());
        assertEquals(Integer.valueOf(17), result3.getRoot().getRight().getValue());

        // Testing for elements not in the tree
        assertNull(bst.search(1));
        assertNull(bst.search(8));
        assertNull(bst.search(13));
    }

    @Test
    public void testRange() {
        BSTExtended<Integer> bst = new BSTExtended<>(10);
        bst.insert(5);
        bst.insert(4);
        bst.insert(3);
        bst.insert(7);
        bst.insert(6);
        bst.insert(17);
        List<Integer> expected = bst.range(3, 7);

        for (Integer integer : Arrays.asList(3, 5, 7, 6)) {
            assertTrue(expected.contains(integer));
        }

    }

    @Test
    public void testDeleteMin() {
        // Create a binary search tree
        BSTExtended<Integer> bst = new BSTExtended<>(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);
        bst.insert(12);
        bst.insert(1);

        // Delete the minimum value
        bst.deleteMin();

        // Verify that the minimum value is no longer present in the tree
        assertFalse(bst.contains(1));

    }

    @Test
    public void testDeleteMax() {
        // Case 1: Maximum value is not the root
        BSTExtended<Integer> bst1 = new BSTExtended<>(10);
        bst1.insert(5);
        bst1.insert(15);
        bst1.insert(3);
        bst1.insert(7);
        bst1.insert(12);
        bst1.insert(17);
        bst1.deleteMax();
        assertFalse(bst1.contains(17));
        assertTrue(bst1.contains(3));
        assertTrue(bst1.contains(5));
        assertTrue(bst1.contains(7));
        assertTrue(bst1.contains(10));
        assertTrue(bst1.contains(12));
        assertTrue(bst1.contains(15));

        // Case 2: Maximum value is the root
        BSTExtended<Integer> bst2 = new BSTExtended<>(20);
        bst2.deleteMax();
        assertNull(bst2.getRoot());

        // Case 3: Only one element in the tree
        BSTExtended<Integer> bst3 = new BSTExtended<>(25);
        bst3.deleteMax();
        assertNull(bst3.getRoot());
    }

    @Test
    public void testCountAfterInsertionAndDeletion() {
        BSTExtended<Integer> bst = new BSTExtended<>(10);

        // Insert elements
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);
        bst.insert(12);
        bst.insert(17);

        // Verify counts after insertion
        assertEquals(7, bst.count());

        // Delete elements
        bst.deleteMin(); // Delete minimum element
        bst.deleteMax(); // Delete maximum element

        // Verify counts after deletion
        assertEquals(5, bst.count());

        // Insert a new element
        bst.insert(20);

        // Verify counts after insertion
        assertEquals(6, bst.count());
    }
    @Test
    public void testRank() {
        BSTExtended<Integer> bst = new BSTExtended<>(10);

        // Insert elements
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);
        bst.insert(12);
        bst.insert(17);

        // Test ranks of various elements
        assertEquals(0, bst.rank(3)); // Rank of 3 should be 0
        assertEquals(1, bst.rank(5)); // Rank of 5 should be 1
        assertEquals(3, bst.rank(10)); // Rank of 10 should be 3
        assertEquals(5, bst.rank(15)); // Rank of 15 should be 5
        assertEquals(6, bst.rank(17)); // Rank of 17 should be 6

        // Test ranks of elements not in the tree
        assertEquals(7, bst.rank(20)); // Rank of 20 should be 7
        assertEquals(0, bst.rank(1)); // Rank of 1 should be 0

        // Test rank of elements when tree is empty
        BSTExtended<Integer> emptyBst = new BSTExtended<>();
        assertEquals(0, emptyBst.rank(10)); // Rank of any element in empty tree should be 0

    }

    @Test
    public void testCeil() {
        BSTExtended<Integer> bst = new BSTExtended<>(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);
        bst.insert(12);
        bst.insert(17);

        assertEquals(Integer.valueOf(12), bst.ceil(11));
        assertEquals(Integer.valueOf(5), bst.ceil(4));
        assertNull(bst.ceil(20));
    }

    @Test
    public void testFloor() {
        BSTExtended<Integer> emptyBst = new BSTExtended<>();
        assertNull(emptyBst.floor(5)); // No floor in an empty tree

        // Create a tree
        BSTExtended<Integer> bst = new BSTExtended<>(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);
        bst.insert(12);
        bst.insert(17);

        // Test when the element is smaller than all elements in the tree
        assertNull(bst.floorRecursive(2)); // No floor for 2 in the tree

        // Test when the element is larger than all elements in the tree
        assertEquals(17, (int) bst.floorRecursive(20)); // Floor for 20 is 17

        // Test when the element is between two elements in the tree
        assertEquals(7, (int) bst.floorRecursive(8)); // Floor for 8 is 7
    }
}
