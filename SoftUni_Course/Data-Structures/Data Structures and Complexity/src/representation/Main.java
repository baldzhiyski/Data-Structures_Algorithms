package representation;

public class Main {
    public static void main(String[] args) {
        NodeStorage storage = new NodeStorage();
        storage.add(1);
        storage.add(2);
        storage.add(3);

        System.out.println("Items:");
        storage.iterate();

        storage.remove(2);

        System.out.println("After removing item:");
        storage.iterate();

        System.out.println("Element at index 1: " + storage.get(1));
    }
}
