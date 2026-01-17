  public class Main {
    public static void main(String[] args) {

        LFU cache = new LFU(2);

        System.out.println("Put(1, 10)");
        cache.put(1, 10); // freq(1)=1

        System.out.println("Put(2, 20)");
        cache.put(2, 20); // freq(2)=1

        System.out.println("Get(1) -> " + cache.get(1));
        // freq(1)=2, freq(2)=1

        System.out.println("Put(3, 30) [Should evict key=2]");
        cache.put(3, 30);
        // Evicts key=2 (LFU)

        System.out.println("Get(2) -> " + cache.get(2)); // -1 (evicted)
        System.out.println("Get(3) -> " + cache.get(3)); // 30
        System.out.println("Get(1) -> " + cache.get(1)); // 10

        System.out.println("Put(4, 40) [Should evict key=3]");
        cache.put(4, 40);
        // freq(1)=3, freq(3)=2 → evict key=3

        System.out.println("Get(3) -> " + cache.get(3)); // -1 (evicted)
        System.out.println("Get(4) -> " + cache.get(4)); // 40
        System.out.println("Get(1) -> " + cache.get(1)); // 10
    }
}