import java.util.concurrent.ConcurrentHashMap;

    // +----------------------+
    // |      ConcurrentMap    |
    // +----------------------+
    //          |
// +-----------------------------+
// |                             |
// +--------------+          +----------------------+
// | ConcurrentHashMap  |          |  ConcurrentSkipListMap |
// +--------------+          +----------------------+

public class concurrentMap_3 {
    // a concurrent map, or concurrent hash map, is a data structure that allows multiple threads to read and write to the map concurrently without causing data corruption or inconsistencies. It's particularly useful in multithreaded or parallel programming environments where multiple threads may access shared data simultaneously.
    public static void main(String[] args) {
        // Create a concurrent map
        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();

        // Put some key-value pairs
        concurrentMap.put("One", 1);
        concurrentMap.put("Two", 2);
        concurrentMap.put("Three", 3);

        // Access values concurrently using multiple threads
        Runnable readTask = () -> {
            String key = "One";
            System.out.println(Thread.currentThread().getName() + ": " + key + " = " + concurrentMap.get(key));
        };

        new Thread(readTask).start();
        new Thread(readTask).start();
        new Thread(readTask).start();

        // Modify values concurrently using multiple threads
        Runnable writeTask = () -> {
            String key = "Two";
            concurrentMap.put(key, concurrentMap.get(key) + 10);
            System.out.println(Thread.currentThread().getName() + ": Updated " + key);
        };

        new Thread(writeTask).start();
        new Thread(writeTask).start();
    }
}
