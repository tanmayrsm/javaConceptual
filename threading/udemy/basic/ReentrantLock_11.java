package udemy.basic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock_11 {
    private static final Lock lock = new ReentrantLock();
    private static int counter = 0;
    
    
    public static void main(String[] args) {
        start();   
    }

    private static void start() {
        Thread t1 = new Thread(() -> {
            makeWork();
        });
        Thread t2 = new Thread(() -> {
            makeWork();
        });
        t1.start();
        t2.start();
        System.out.println("Final counter : " + counter);
    }

    private static void makeWork() {
        int tempCtr = 0;
        for(int i = 0; i < 100; i++) {
            tempCtr++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }

        lock.lock();
        try {
            counter += tempCtr;
        } finally {
            lock.unlock();  // if some exception comes above, make sure to unlock the instance,
            // hence its a good practice to unlock in a finally block
        }   
    }
}
