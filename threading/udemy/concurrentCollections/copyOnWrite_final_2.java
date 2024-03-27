
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class copyOnWrite_final_2 {
    // CopyOnWriteArrayList helps overcome problem while modifying an reading with 'Colelctions.synchronized in last exercise'
    // COWA ensure, that whenever some thread writes to an Array, that same updated Array is visible to all threads after updation
    // hence write operations in COWA is bit expensive
    // so its recommended to go for COWA, only if u have less no of writes, and more reads

    // internally COWA, creates a new copy of arraylist with updated value and shares with all threads

    // iterator on COWA, cant perform remove operations


    private static final int ARRAY_SIZE = 1000; // just reducing value, for compilation time purpose
    private static CopyOnWriteArrayList<Integer> sharedArray = new CopyOnWriteArrayList<Integer>();;

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
                        // it.remove();    // unsupported for COWA
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
