package representation;

public class ArrayStorage {
    private final int INITIAL_CAPACITY = 4;

    private int[] elements;
    private int index;

    public ArrayStorage() {
        this.elements = new int[INITIAL_CAPACITY];
        this.index = 0;
    }

    public boolean add(int element) {
        add(element, ++index);
        return true;
    }

    private void add(int element, int index) {
        if (index == this.elements.length) {
            grow(elements);
        }
        this.elements[index] = element;
    }

    private void grow(int[] elements) {
        int[] result = new int[elements.length*2];

        System.arraycopy(elements, 0, result, 0, elements.length);
    }

    public boolean remove(int element){
        if(element > elements.length){
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < this.elements.length; i++) {
            if(this.elements[i] == element){
                this.elements[i] = 0;
                return true;
            }
        }
        return false;
    }

}
