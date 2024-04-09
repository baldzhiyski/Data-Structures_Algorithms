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
    public void testAddFirst() {
        deque.addFirst(1);
        assertEquals(1, deque.size());
        assertEquals(Integer.valueOf(1), deque.peek());
    }

    @Test
    public void testAddLast() {
        deque.addLast(1);
        assertEquals(1, deque.size());
        assertEquals(Integer.valueOf(1), deque.peek());
    }

    @Test
    public void testAdd() {
        deque.add(1);
        assertEquals(1, deque.size());
        assertEquals(Integer.valueOf(1), deque.peek());
    }

    @Test
    public void testOffer() {
        deque.offer(1);
        assertEquals(1, deque.size());
        assertEquals(Integer.valueOf(1), deque.peek());
    }

    @Test
    public void testRemoveFirst() {
        deque.addFirst(1);
        assertEquals(Integer.valueOf(1), deque.removeFirst());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testRemoveLast() {
        deque.addLast(1);
        assertEquals(Integer.valueOf(1), deque.removeLast());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testPeek() {
        deque.add(1);
        assertEquals(Integer.valueOf(1), deque.peek());
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testPoll() {
        deque.add(1);
        assertEquals(Integer.valueOf(1), deque.poll());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testPop() {
        deque.add(1);
        assertEquals(Integer.valueOf(1), deque.pop());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testGetByIndex() {
        deque.add(1);
        assertEquals(Integer.valueOf(1), deque.get(0));
    }

    @Test
    public void testRemoveByIndex() {
        deque.add(1);
        assertEquals(Integer.valueOf(1), deque.remove(0));
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(0, deque.size());
        deque.add(1);
        deque.add(2);
        assertEquals(2, deque.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(deque.isEmpty());
        deque.add(1);
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testCapacity() {
        assertEquals(7, deque.capacity());
    }

    @Test
    public void testTrimToSize() {
        for (int i = 0; i < 10; i++) {
            deque.add(i);
        }
        assertEquals(15,deque.capacity());
        deque.trimToSize();
        assertEquals(10, deque.size());
    }

    @Test
    public void testIterator() {
        deque.add(1);
        deque.add(2);
        int sum = 0;
        for (Integer i : deque) {
            sum += i;
        }
        assertEquals(3, sum);
    }
}
