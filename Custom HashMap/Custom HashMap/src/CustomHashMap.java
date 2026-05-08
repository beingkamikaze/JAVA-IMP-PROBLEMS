
//Entry Node
//Generic Class
class Node<K,V> {
    K key;
    V value;
    Node<K,V> next;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

public class CustomHashMap<K,V> {
    //capacity
    private static final int DEFAULT_CAPACITY = 16;
    //loadFactor
    private static final float LOAD_FACTOR = 0.75f;

    //craete bucket[] that will contain linked list
    private Node<K,V>[] bucket;
    private int size;

    public CustomHashMap() {
        bucket = new Node[DEFAULT_CAPACITY];
    }
}
