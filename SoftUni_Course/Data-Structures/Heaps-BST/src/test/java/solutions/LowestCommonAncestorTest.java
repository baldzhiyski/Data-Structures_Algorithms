package solutions;

import implementations.BSTExtended;
import org.junit.Test;

import static org.junit.Assert.*;

public class LowestCommonAncestorTest {

    @Test
    public void testLCA() {
        BSTExtended binaryTree =
                new BSTExtended<>(7,
                        new BSTExtended<>(21, null, null),
                        new BSTExtended<>(14,
                                new BSTExtended<>(23, null, null),
                                new BSTExtended<>(6, null,
                                        new BSTExtended<>(13, null, null))));

        assertEquals(Integer.valueOf(14), binaryTree.findLowestCommonAncestor(23, 13));
    }

}