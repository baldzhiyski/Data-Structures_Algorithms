import java.util.ArrayDeque;
import java.util.Deque;

public class RedBlackTree<Key extends Comparable<Key>, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;     // root of the BST

    // BST helper node data type
    private class Node {
        private Key key;           // key
        private Value val;         // associated data
        private Node left, right;  // links to left and right subtrees
        private boolean color;     // color of parent link
        private int size;          // subtree count

        public Node(Key key, Value val, boolean color, int size) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.size = size;
        }
    }

    public RedBlackTree() {
    }

    // is node x red; false if x is null ?
    private boolean isRed(Node x) {
        return x == null ? false : x.color;
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }


    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return this.size(root);
    }

    /**
     * Is this symbol table empty?
     *
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key should not be null in get() call!");
        }


        return get(root, key);


    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Value get(Node node, Key key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            return get(node.right, key);
        } else if (cmp < 0) {
            return get(node.left, key);
        } else {
            return node.val;
        }
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null !");
        }
        this.root = put(this.root, key, val);
        this.root.color = BLACK;
    }

    // insert the key-value pair in the subtree rooted at h
    private Node put(Node node, Key key, Value val) {
        if (node == null) {
            return new Node(key, val, RED, 1);
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            node.left = put(node.left, key, val);
        } else if (cmp > 0) {
            node.right = put(node.right, key, val);
        } else {
            node.val = val;
        }

        node = balance(node);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void deleteMin() {
        if (this.isEmpty()) {
            throw new IllegalArgumentException(); // Tree is empty, nothing to delete
        }

        if(this.root.left == null){
            this.root =null;
            return;
        }

        this.root = deleteMin(root);

    }

    // Delete the minimum key-value pair rooted at node
    private Node deleteMin(Node node) {
        if (node.left == null) {
            // Found the minimum node, delete it by returning its right child( if not have it will return just null)
            return node.right;
        }
        if (!isRed(node.left) && !isRed(node.left.left)) {
            node = moveRedLeft(node);
        }

        // Recursively delete the minimum node in the left subtree
        node.left = deleteMin(node.left);

        // Backtracking
        return balance(node);
    }

    public void deleteMax() {
        if (this.isEmpty()) {
            throw new IllegalArgumentException(); // Tree is empty, nothing to delete
        }

        if(this.root.right == null){
            this.root =null;
            return;
        }

        this.root = deleteMax(root);
    }

    // delete the key-value pair with the maximum key rooted at h
    private Node deleteMax(Node node) {
        if(isRed(node.left)){
            node = rotateRight(node);
        }

        if (node.right == null) {
            // Found the minimum node, delete it by returning its right child( if not have it will return just null)
            return node.left;
        }

        if (!isRed(node.right) && !isRed(node.right.right)) {
            node = moveRedRight(node);
        }

        // Recursively delete the minimum node in the left subtree
        node.right = deleteMax(node.right);

        // Backtracking
        return balance(node);
    }

    public void delete(Key key) {
        if(key == null || isEmpty()){
            throw new IllegalStateException();
        }

        if(!contains(key)) return;

        root = delete(root,key);

        if(!isEmpty()){
            root.color = BLACK;
        }
    }

    // delete the key-value pair with the given key rooted at h
    private Node delete(Node h, Key key) {
        if(h==null){
            return null;
        }

        int cmp = key.compareTo(h.key);

        if(cmp<0){
            if(!isRed(h.left) && !isRed(h.left.left)){
                h = moveRedLeft(h);
            }
            h.left = delete(h.left,key);
        }else {
            if(isRed(h.left)){
                h = rotateRight(h);
            }
            if(cmp == 0 && h.right == null){
                return null;
            }

            if(!isRed(h.right) && !isRed(h.right.right)){
                h = moveRedRight(h);
            }

            if(cmp==0){
                Node min = min(h.right);
                h.key = min.key;
                h.val = min.val;

                h.right = deleteMin(h.right);
            }else{
                h.right = delete(h.right,key);
            }
        }
        return balance(h);

    }

    private Node rotateRight(Node h) {
        Node temp = h.left;
        h.left = temp.right;
        temp.right = h;

        temp.color = temp.right.color;
        temp.right.color = RED;
        temp.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;

        return temp;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        Node temp = h.right;
        h.right = temp.left;
        temp.left = h;
        temp.color = temp.left.color;
        temp.left.color = RED;
        temp.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;

        return temp;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node node) {
        flipColors(node);

        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }
        return node;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        flipColors(h);
        if(isRed(h.left.left)){
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree invariant
    private Node balance(Node node) {
        if(isRed(node.right)){
            node = rotateLeft(node);
        }
        if(isRed(node.left) && isRed(node.left.left)){
            node = rotateRight(node);
        }
        if(isRed(node.left) && isRed(node.right)){
            flipColors(node);
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) {
            return -1;
        }
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    public Key min() {
        Node min = this.min(root);
        return min != null ? min.key : null;
    }

    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }

        return min(x.left);
    }

    public Key max() {
        Node max = this.max(root);
        return max != null ? max.key : null;
    }

    // the largest key in the subtree rooted at x; null if no such key
    private Node max(Node x) {
        if (x.right == null) {
            return x;
        }

        return max(x.right);
    }

    public Key floor(Key key) {
        Node floor = floor(root, key);
        if(floor==null) throw new IllegalArgumentException();

        return floor.key;
    }

    // the largest key in the subtree rooted at x less than or equal to the given key
    private Node floor(Node x, Key key) {
        if(x==null){
            return null;
        }


        int cmp = key.compareTo(x.key);

        if(cmp == 0){
            return x;
        }

        if(cmp<0){
            return floor(x.left,key);
        }
        Node node = floor(x.right, key);
        if(node!=null){
            return node;
        }
        return x;
    }

    public Key ceiling(Key key) {
        Node ceiling = ceiling(root, key);
        if(ceiling==null) throw new IllegalArgumentException();

        return ceiling.key;
    }

    // the smallest key in the subtree rooted at x greater than or equal to the given key
    private Node ceiling(Node x, Key key) {
        if(x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);

        if(cmp==0){
            return x;
        }

        if(cmp>0){
            return ceiling(x.right,key);
        }
        Node node = ceiling(x.left,key);
        if(node!=null){
            return node;
        }
        return x;
    }

    public Key select(int rank) {
        return select(root,rank);
    }

    // Return key in BST rooted at x of given rank.
    // Precondition: rank is in legal range.
    private Key select(Node x, int rank) {
        if(x==null){
            return null;
        }

        int leftSize = size(x.left);

        if(leftSize>rank){
            return select(x.left,rank);
        }else if(leftSize<rank){
            return select(x.right,rank - leftSize - 1);
        }else{
            return x.key;
        }

    }

    //  The effect of accumulation still occurs implicitly through the recursive calls and return values
    public int rank(Key key) {
        return nodeRank(key,root);
    }

    private int nodeRank(Key key , Node current) {
        if(current==null){
            return 0;
        }
        int cmp = key.compareTo(current.key);

        if(cmp<0){
            return nodeRank(key,root);
        }else if(cmp>0){
            // We add the count of the left because in bst on the left are always smaller and we go
            // further to the right to make sure there
            return 1 + size(current.left) +  nodeRank(key,current.right);
        }else{
            return size(current.left);
        }
    }

    public Iterable<Key> keys() {
        return keys(min(),max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Deque<Key> deque = new ArrayDeque<>();

        keys(root,deque,lo,hi);

        return deque;

    }

    // add the keys between lo and hi in the subtree rooted at x
    // to the queue
    private void keys(Node x, Deque<Key> queue, Key lo, Key hi) {
        if(x == null){
            return;
        }

        int cmp1 = lo.compareTo(x.key);
        int cmp2 = hi.compareTo(x.key);

        if(cmp1<0){
            keys(x.left,queue,lo,hi);
        }
        if(cmp1 <=0 && cmp2 >=0){
            queue.offer(x.key);
        }
        if(cmp2 > 0){
            keys(x.right,queue,lo,hi);
        }
    }

    public int size(Key lo, Key hi) {
        int cmp = lo.compareTo(hi);

        if (cmp > 0) {
            return 0;
        }

        return rank(hi) - rank(lo);
    }

}