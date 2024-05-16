package trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trie implements Tree{
    private Node root;

    public Trie(){
        this.root = null;
    }
    @Override
    public Tree insert(String word) {
        Node current = root;
        Map<Character, Node> children = root.getChildren();
        for (char symbol : word.toCharArray()) {
            if(children.containsKey(symbol)){
                current = children.get(symbol);
            }else{
                current = new Node(symbol);
                children.put(symbol,current);
            }
            // Here we ensure that children variables will get initialized to an empty map
            // We maintain in this way the link
            children = current.getChildren();
        }
        current.setEndOfWord(true);
        return this;
    }

    @Override
    public boolean contains(String word) {
        Node lastPresentNode = search(word);

        return lastPresentNode !=null && lastPresentNode.isEndOfWord();
    }

    private Node search(String str) {
        Node currentNode = null;
        Map<Character, Node> children = root.getChildren();
        for (char symbol : str.toCharArray()) {
            if(!children.containsKey(symbol)){
                return null;
            }
            currentNode = children.get(symbol);
            children = currentNode.getChildren();
        }
        return currentNode;
    }

    @Override
    public void delete(String word) {
        // List to store nodes corresponding to characters in the word
        List<Node> list = new ArrayList<>();
        // Start with the children of the root node
        Map<Character, Node> children = root.getChildren();

        // Traverse through the Trie, finding nodes corresponding to characters in the word
        for (char c : word.toCharArray()) {
            // If the character doesn't exist in the Trie, break the loop (word doesn't exist)
            if (!children.containsKey(c)) {
                break;
            }
            // Get the node corresponding to the current character
            Node currentNode = children.get(c);
            // Update 'children' to point to the children of the current node
            children = currentNode.getChildren();
            // Add the current node to the list
            list.add(currentNode);
        }

        // If the list is empty or the word is not fully matched, return (word doesn't exist)
        if (list.isEmpty() || list.size() != word.length()) {
            return;
        }

        // Mark the last node in the list as not marking the end of a word
        list.get(list.size() - 1).setEndOfWord(false);

        // Traverse backwards through the list to remove unnecessary nodes
        for (int i = list.size() - 1; i > 0; i--) {
            Node current = list.get(i);
            // If the current node marks the end of a word, stop further processing
            if (current.isEndOfWord()) {
                break;
            } else if (current.getChildren().isEmpty()) {
                // If the current node has no children, remove it from its parent's children map
                // This prevents cases where we have similar words
                list.get(i - 1).getChildren().remove(current.getCharacter());
            }
        }
    }


    @Override
    public boolean containsPrefix(String prefix) {
        return search(prefix) !=null;
    }

    @Override
    public List<String> wordsWithPrefix(String prefix) {
        List<String> words = new ArrayList<>();
        // Search for the node corresponding to the prefix
        Node prefixNode = search(prefix);

        // If the prefix exists in the Trie, add words with the prefix to the list
        if (prefixNode != null) {
            addWords(prefixNode, prefix, words);
        }
        return words;
    }

    // Recursively adds words with the given prefix to the list
    private void addWords(Node prefixNode, String prefix, List<String> words) {
        // If the current node marks the end of a word, add the word to the list
        if (prefixNode.isEndOfWord()) {
            words.add(prefix);
        }

        // Iterate over each child node of the current node
        prefixNode.getChildren().values().forEach(child -> {
            // Get the character of the child node
            String character = String.valueOf(child.getCharacter());
            // Recursively call addWords with the child node and the updated prefix
            addWords(child, prefix.concat(character), words);
        });
    }
}
