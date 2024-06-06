import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeapSort {

    public static <T extends Comparable<T>> void heapSort(final ArrayList<T> list) {
        int size = list.size();

        // Step 1: Build a max-heap from the input data . Use the alg from the lecture
        for (int i = size / 2; i >= 0; i--) {
            heapifyDown(list, size, i);
        }

        // Step 2: Extract elements from heap one by one ( Heap Sort)
        for (int i = size - 1; i > 0; i--) {
            // Move current root to end of the list
            Collections.swap(list,0, i);

            // call max-heapify on the reduced heap
            heapifyDown(list, i, 0);
        }
    }

    // To heapify a subtree rooted with node i which is an index in list[]
    private static <T extends Comparable<T>> void heapifyDown(ArrayList<T> list, int heapSize, int rootIndex) {
        int largest = rootIndex; // Initialize largest as root
        int leftChild = 2 * rootIndex + 1; // leftChild index
        int rightChild = 2 * rootIndex + 2; // rightChild index

        // If left child is larger than root and the index of the child is in bounds
        if (leftChild < heapSize && list.get(leftChild).compareTo(list.get(largest)) > 0) {
            largest = leftChild;
        }

        // If right child is larger than largest so far and the index of the child is in bounds
        if (rightChild < heapSize && list.get(rightChild).compareTo(list.get(largest)) > 0) {
            largest = rightChild;
        }

        // If largest is not rootIndex
        if (largest != rootIndex) {
            Collections.swap(list,rootIndex, largest);

            // Recursively heapify the affected subtree
            heapifyDown(list, heapSize, largest);
        }
    }

}