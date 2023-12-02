package udemy.basic;

public class Deadlock_8 {
    private final static Object lock1 = new Object();
    private final static Object lock2 = new Object();

    private static int counter = 0;
    
    public static void main(String[] args) {
        start();   
    }

    private static void start() {
        Thread t1 = new Thread(() -> {
            while(true)
                buildCache();
        });
        Thread t2 = new Thread(() -> {
            while(true)
                searchCacheValue();
        });

        t1.start();
        t2.start();
           
    }

    private static void buildCache() {
        synchronized(lock1) {
            synchronized(lock2) {   
                // LOCK 2 waits for LOCK 1 always
                counter++;
            }
        }
    }

    private static void searchCacheValue() {
        synchronized(lock1) {
            synchronized(lock2) {   
                // LOCK 2 waits for LOCK 1 always
                System.out.println("Val :: " + counter);
            }
        }
    }
}
