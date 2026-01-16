import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

//for ddll node1-><-node2
// for making tnis thread-safe we can use both Synchronization and Locks(RentratntLock)
class Node {
    int key;
    int value;
    Node prev;
    Node next;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

public class LRU {
    private final int capacity;
    private final Map<Integer,Node> cache;
    private Node head,tail;
    private final ReentrantLock lock = new ReentrantLock();

    // LRU Intialization
    public LRU(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
    }

    public int get(int key){
        try{
            lock.lock();
            Node node = cache.get(key);
            if(node==null){
                return -1;
            }
            moveToHead(node);
            return node.value;
        }finally {
            lock.unlock();
        }
    }

    public void put(int key,int val){
        try{
            Node node = cache.get(key);
            if(node!=null)
            {
                node.value=val;
                moveToHead(node);
            } else{
                Node newNode = new Node(key,val);
                //new node will be added to head in dll
                cache.put(key,newNode);
                addToHead(newNode);
                //if size of cache is already full
                if(cache.size() > capacity){
                    //remove node from Tail and get key then using that key remove from cache
                    Node removedNode = removeNodefromTail();
                    cache.remove(removedNode.key);
                }
            }
        }finally {
            lock.unlock();
        }

    }

    // ===HELPER METHOD===
    private void addToHead(Node node){
        // we have head and tail
        node.next=head;
        node.prev=null;

        if(head!=null)
        {
            head.prev=node;
        }
        head = node;
        //if head and tail are null then node will be both
        if(tail==null)
        {
            tail=node;
        }
    }
    private void removeNode(Node node){
        if(node.prev!=null)
        {
            node.prev.next=node.next;
        }else{
            head=node.next;
        }

        if(node.next!=null)
        {
            node.next.prev=node.prev;
        }else{
            tail=node.prev;
        }
    }
    private  void moveToHead(Node node){
        //remove node wherever it exits and add that node to haed
        removeNode(node);
        addToHead(node);
    }

    private Node removeNodefromTail(){
        Node tailNode = tail;
        removeNode(tailNode);
        return tailNode;
    }

}
