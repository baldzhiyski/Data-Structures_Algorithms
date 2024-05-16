package trie;

import java.util.HashMap;
import java.util.Map;

public class Node {

    private final char character;
    private Map<Character,Node> children;
    private boolean isEndOfWord;
    public Node(char character) {
        this.character = character;
        this.children = new HashMap<>();
    }

    public char getCharacter() {
        return character;
    }

    public Map<Character, Node> getChildren() {
        return children;
    }

    public void setChildren(Map<Character, Node> children) {
        this.children = children;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }
}
