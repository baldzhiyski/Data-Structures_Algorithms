package implementations;

import static org.junit.Assert.*;

import interfaces.Deque;
import org.junit.*;

public class DequeTest {

    private Deque<Integer> deque;

    @Before
    public void setUp() {
        deque = new ArrayDeque<>();
    }

    @Test
    public void testAdd() {
        deque.add(1);
        deque.add(2);
        assertEquals(2, deque.size());
        assertEquals(Integer.valueOf(1), deque.get(0));
        assertEquals(Integer.valueOf(2), deque.get(1));
    }

    @Test
    public void testOffer() {
        deque.offer(1);
        deque.offer(2);
        assertEquals(2, deque.size());
        assertEquals(Integer.valueOf(1), deque.get(0));
        assertEquals(Integer.valueOf(2), deque.get(1));
    }

    @Test
    public void testAddFirst() {
        deque.addFirst(1);
        deque.addFirst(2);
        assertEquals(2, deque.size());
        assertEquals(Integer.valueOf(2), deque.get(0));
        assertEquals(Integer.valueOf(1), deque.get(1));
    }

    @Test
    public void testAddLast() {
        deque.addLast(1);
        deque.addLast(2);
        assertEquals(2, deque.size());
        assertEquals(Integer.valueOf(1), deque.get(0));
        assertEquals(Integer.valueOf(2), deque.get(1));
    }

    @Test
    public void testPush() {
        deque.push(1);
        deque.push(2);
        assertEquals(2, deque.size());
        assertEquals(Integer.valueOf(2), deque.get(0));
        assertEquals(Integer.valueOf(1), deque.get(1));
    }

    @Test
    public void testInsert() {
        deque.add(1);
        deque.insert(1, 2);
        assertEquals(2, deque.size());
        assertEquals(Integer.valueOf(1), deque.get(0));
        assertEquals(Integer.valueOf(2), deque.get(1));
    }

    @Test
    public void testSet() {
        deque.add(1);
        deque.set(0, 2);
        assertEquals(1, deque.size());
        assertEquals(Integer.valueOf(2), deque.get(0));
    }

    @Test
    public void testPeek() {
        deque.add(1);
        deque.add(2);
        assertEquals(Integer.valueOf(1), deque.peek());
    }

    @Test
    public void testPoll() {
        deque.add(1);
        deque.add(2);
        assertEquals(Integer.valueOf(1), deque.poll());
        assertEquals(1, deque.size());
    }

    @Test
    public void testPop() {
        deque.add(1);
        deque.add(2);
        assertEquals(Integer.valueOf(1), deque.pop());
        assertEquals(1, deque.size());
    }

    @Test
    public void testGetByIndex() {
        deque.add(1);
        deque.add(2);
        assertEquals(Integer.valueOf(1), deque.get(0));
        assertEquals(Integer.valueOf(2), deque.get(1));
    }

    @Test
    public void testGetByObject() {
        deque.add(1);
        deque.add(2);
        assertEquals(Integer.valueOf(1), deque.get(1));
    }

    @Test
    public void testRemoveByIndex() {
        deque.add(1);
        deque.add(2);
        assertEquals(Integer.valueOf(1), deque.remove(0));
        assertEquals(1, deque.size());
    }

    @Test
    public void testRemoveByObject() {
        deque.add(1);
        deque.add(2);
        assertEquals(1, deque.size());
    }

    @Test
    public void testRemoveFirst() {
        deque.add(1);
        deque.add(2);
        assertEquals(Integer.valueOf(1), deque.removeFirst());
        assertEquals(1, deque.size());
    }

    @Test
    public void testRemoveLast() {
        deque.add(1);
        deque.add(2);
        assertEquals(Integer.valueOf(2), deque.removeLast());
        assertEquals(1, deque.size());
    }

    @Test
    public void testSize() {
        deque.add(1);
        deque.add(2);
        assertEquals(2, deque.size());
    }

    @Test
    public void testCapacity() {
        assertEquals(0, deque.capacity());
    }

    @Test
    public void testTrimToSize() {
        deque.add(1);
        deque.add(2);
        deque.trimToSize();
        assertEquals(2, deque.capacity());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(deque.isEmpty());
        deque.add(1);
        assertFalse(deque.isEmpty());
    }
}
