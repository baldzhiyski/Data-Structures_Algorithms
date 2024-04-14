package solutions;

import implementations.BSTExtended;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TopViewTest {

    @Test
    public void testLCA() {
        BSTExtended binaryTree =
                new BSTExtended<>(1,
                        new BSTExtended<>(2,
                                new BSTExtended<>(4, null, null),
                                new BSTExtended<>(5, null, null)),
                        new BSTExtended<>(3,
                                new BSTExtended<>(6, null, null),
                                new BSTExtended<>(7, null, null)));


        List<Integer> list = binaryTree.topView();

        Collections.sort(list);
        Integer[] expected = {1, 2, 3, 4, 7};

        assertEquals(expected.length, list.size());

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], list.get(i));
        }
    }
}
