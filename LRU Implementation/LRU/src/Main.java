public class Main {
    public static void main(String[] args) {

        LRU lruCache = new LRU(2);

        lruCache.put(1,10);
        lruCache.put(2,20);

        System.out.println(lruCache.get(1));

        lruCache.put(3,30);
        System.out.println(lruCache.get(2));

    }
}