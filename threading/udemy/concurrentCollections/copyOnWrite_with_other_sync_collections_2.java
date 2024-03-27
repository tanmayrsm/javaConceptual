
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class copyOnWrite_with_other_sync_collections_2 {
    // WILL GET CONCURRENT MODIFICATION EXCEPTION
    // as you're using Iterator to read elements, at same time, writing to same array
    // iterator is not thread safe in reader thread

    private static final int ARRAY_SIZE = 100000; // just increasing value, for concurrent modification exception to occur
    private static List<Integer> sharedArray = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {

        // Create and start the writer thread
        Thread writerThread = new Thread(() -> {
            int ct = 0;
            while(true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                // Writing to the shared array
                // synchronized (lock) {
                    System.out.println("\n===== write =====");
                    ct++;
                    for (int i = 0; i < ARRAY_SIZE; i++) {
                        // System.out.println("Writer Thread: Writing value " + i + " to the array.");
                        sharedArray.add(i);
                    }
                    System.out.println("WROTE IN ARRAY");
                    if(ct == 5)
                        return;
                // }
            }
        });

        // Create and start the reader thread
        Thread readerThread = new Thread(() -> {
            int ct = 0;
            while(true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                java.util.Iterator<Integer> it = sharedArray.iterator();
                // synchronized (lock) {
                    System.out.println("\n===== read =====");
                    ct++;
                    while(it.hasNext()) {
                        it.next();
                    }
                    // Reading from the shared array
                    System.out.println("Read all values :: " + sharedArray.size());
                    if(ct == 5) return;
                // }       
            }
        });

        // Start both threads
        writerThread.start();
        readerThread.start();

        // Wait for both threads to finish
        try {
            writerThread.join();
            readerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
