package hierarchy;

import java.util.*;
import java.util.stream.Collectors;

public class Hierarchy<T> implements IHierarchy<T> {

    private Map<T,HierarchyNode<T>> data;
    private HierarchyNode<T> root;

    public Hierarchy(T element){
        this.data = new HashMap<>();
        HierarchyNode<T> root = new HierarchyNode<>(element);
        this.data.put(element,root);
        this.root = root;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public void add(T element, T child) {
        HierarchyNode<T> parent = ensureExistsAndGet(element);
        if(this.data.containsKey(child))throw new IllegalArgumentException();

        HierarchyNode<T> toBeAdded = new HierarchyNode<>(child);
        toBeAdded.setParent(parent);
        parent.getChildren().add(toBeAdded);
        this.data.put(child,toBeAdded);
    }

    @Override
    public void remove(T element) {
        HierarchyNode<T> toRemove = ensureExistsAndGet(element);
        if(toRemove.getParent()==null) throw new IllegalStateException();

        HierarchyNode<T> parent = toRemove.getParent();
        List<HierarchyNode<T>> childrenOfRemoved = toRemove.getChildren();
        parent.getChildren().addAll(childrenOfRemoved);
        parent.getChildren().remove(toRemove);

        childrenOfRemoved.forEach(child->child.setParent(parent));


        this.data.remove(toRemove.getValue());
    }
    private HierarchyNode<T> ensureExistsAndGet(T key) {
        if (!data.containsKey(key)) {
            throw new IllegalArgumentException("Element does not exist in the hierarchy.");
        }
        return data.get(key);
    }

    @Override
    public Iterable<T> getChildren(T element) {
        HierarchyNode<T> current = ensureExistsAndGet(element);
        return  current.getChildren().stream().map(HierarchyNode::getValue).collect(Collectors.toList());
    }

    @Override
    public T getParent(T element) {
        HierarchyNode<T> current = ensureExistsAndGet(element);
        return current.getParent() == null ? null : current.getParent().getValue();
    }

    @Override
    public boolean contains(T element) {
        return this.data.containsKey(element);
    }

    @Override
    public Iterable<T> getCommonElements(IHierarchy<T> other) {
        List<T> result = new ArrayList<>();

        this.data.keySet().forEach(k->{
                    if(other.contains(k)) result.add(k);
                });

        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Deque<HierarchyNode<T>> deque = new ArrayDeque<>(
                    Collections.singletonList(root)
            );


            @Override
            public boolean hasNext() {
                return !deque.isEmpty();
            }

            @Override
            public T next() {
                HierarchyNode<T> nextEl = deque.poll();
                deque.addAll(nextEl.getChildren());
                return nextEl.getValue();
            }
        };
    }
}
