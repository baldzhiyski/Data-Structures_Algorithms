package representation;

public class NodeStorage {

    private Node node;

    class Node {
        private int element;
        private Node next;

        Node(int element) {
            this.element = element;
        }
    }

    public NodeStorage() {
        this.node = new Node(0);
    }

    public boolean add(int element) {
        Node newNode = new Node(element);
        Node current = this.node;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
        return true;
    }

    // TODO: How do we iterate over the items? How do we remove? How do we iterate and access data?

    // Method to iterate over the items
    public void iterate() {
        Node current = this.node.next; // Start from the first actual node
        while (current != null) {
            System.out.println(current.element);
            current = current.next;
        }
    }

    // Method to remove an item
    public boolean remove(int element) {
        Node current = this.node;
        while (current.next != null) {
            if (current.next.element == element) {
                current.next = current.next.next;
                return true;
            }
            current = current.next;
        }
        return false; // Element not found
    }

    // Method to access data at a specific index
    public int get(int index) {
        Node current = this.node.next; // Start from the first actual node
        int currentIndex = 0;
        while (current != null) {
            if (currentIndex == index) {
                return current.element;
            }
            currentIndex++;
            current = current.next;
        }
        throw new IndexOutOfBoundsException("Index out of bounds: " + index);

    }
}
