package implementations;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class TreeFactory {
    private Map<Integer, SecondTree<Integer>> nodesByKeys;

    public TreeFactory() {
        this.nodesByKeys = new LinkedHashMap<>();
    }

    public SecondTree<Integer> createTreeFromStrings(String[] input) {
        // Here we take input such as "7 19"
        for (String params : input) {
            int[] keys = Arrays.stream(params.split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int parentKey = keys[0];
            int childKey = keys[1];

            // Create the parent if no such exists and make the relations between them
            addEdge(parentKey,childKey);

        }
        return this.getRoot();
    }

    private SecondTree<Integer> getRoot() {
        for (SecondTree<Integer> value : nodesByKeys.values()) {
            if(value.getParent()==null){
                return value;
            }
        }
        return null;
    }

    public SecondTree<Integer> createNodeByKey(int key) {
        this.nodesByKeys.putIfAbsent(key,new SecondTree<>(key));
       return this.nodesByKeys.get(key);
    }

    public void addEdge(int parent, int child) {
        SecondTree<Integer> parentByKey = this.createNodeByKey(parent);
        SecondTree<Integer> childByKey = createNodeByKey(child);

        childByKey.setParent(parentByKey);
        parentByKey.addChild(childByKey);
    }
}



