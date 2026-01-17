import java.util.*;

class Node {
    int key;
    int value;
    int freq;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
        this.freq = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return key == node.key;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }
}

public class LFU {
    // max capacity of cache
    private final int capacity;
    private int minFreq;

    //map to contain key ->node
    private final Map<Integer,Node> mp;
    //map to contain freq -> LinkedHashset of node
    private final Map<Integer, LinkedHashSet<Node>> freqMap;

    public LFU(int capacity) {
        this.capacity = capacity;
        this.mp = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.minFreq = 1;
    }

    //method to get element from Cache Based on key
    //update frequency as well
    int get(int key){
        if(!mp.containsKey(key))
        {
            return -1;
        }else {
            Node node = mp.get(key);
            updateFrequency(node);
            return node.value;
        }
    }
    //method to upsert element into cache with eviction case
    //Update Frequency as well
    void put(int key,int value){
        if(capacity==0)
            return;
        //check if key already present in the map
        if(mp.containsKey(key))
        {
            Node node = mp.get(key);
            node.value = value;
            updateFrequency(node);
            return;
        }
        //if key doesnot exits first check-> if capacity is already full ?
        //if full then evict LFU
        if(capacity==mp.size()){
            evictLFU();
        }
        //Now space is there in the cache
        Node newNode = new Node(key,value);
        mp.put(key,newNode);
        //check if in freqMap -> freq is there or not -> if not create new entry for fre=1
        if (!freqMap.containsKey(1)) {
            freqMap.put(1, new LinkedHashSet<>());
        }
        LinkedHashSet<Node> set = freqMap.get(1);
        set.add(newNode);
        minFreq=1;
    }
    void updateFrequency(Node node){
        //get initial freq of node{}
        int freq = node.freq;
        //get set mapped to that freq
        LinkedHashSet<Node> nodes = freqMap.get(freq);
        //remove that node -> since freq= freq+1
        nodes.remove(node);

        //if nodes set gets empty ie., for given freq only one node was there
        if(nodes.isEmpty()){
            freqMap.remove(freq);

            if(minFreq==freq){
                minFreq++;
            }
        }
        //update frequency of node
        node.freq++;
        // get set of nodes with updated freq of node
        //if not exists then create new set of node -> add node to newly made set
        if(!freqMap.containsKey(node.freq)){
            LinkedHashSet newSet = new LinkedHashSet();
            newSet.add(node);
            freqMap.put(node.freq, newSet);
        }
        freqMap.get(node.freq).add(node);
    }
    //method to removing LFU element
    void evictLFU(){
        //get set of node for key=minFreq from freqMap
        LinkedHashSet<Node> nodes = freqMap.get(minFreq);
        Node nodeToRemove = nodes.iterator().next();
        nodes.remove(nodeToRemove);
        if (nodes.isEmpty()) {
            freqMap.remove(minFreq);
        }
        mp.remove(nodeToRemove.key);
    }
}
