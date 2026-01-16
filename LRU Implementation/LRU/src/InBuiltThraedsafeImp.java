import java.util.LinkedHashMap;
import java.util.Map;

public class InBuiltThraedsafeImp {

    private final Map<Integer, Integer> cache;

    public InBuiltThraedsafeImp(int capacity) {
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
    }

    public synchronized int get(int key) {
        return cache.getOrDefault(key, -1);
    }

    public synchronized void put(int key, int value) {
        cache.put(key, value);
    }
}
