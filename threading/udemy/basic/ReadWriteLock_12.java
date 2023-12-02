package udemy.basic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock_12 {
    // helps to acquire R_W lock
    // multiple threads can read at a time
    // only one thread can write at a time...no other reads/writes that time

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static int counter = 0;
    
    public static void main(String[] args) {
        start();   
    }

    private static void start() {
        Thread t1 = new Thread(() -> {
            writeWork();
        });
        Thread t2 = new Thread(() -> {
            readWork();
        });
        t1.start();
        t2.start();
    }

    private static void writeWork() {
        int tempCtr = 0;
        for(int i = 0; i < 5; i++) {
            tempCtr++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            System.out.println("WROTE acquire : " + counter);
            lock.writeLock().lock();;
            try {
                counter += tempCtr;        
            } finally {
                lock.writeLock().unlock();            
                System.out.println("WROTE release : " + counter);
            }   
        }

    }

    private static void readWork() {
        for(int i = 0; i < 5; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            
            System.out.println("READ acquire : " + counter);
            lock.readLock().lock();;
            try {
            } finally {
                lock.readLock().unlock();            
                System.out.println("READ release : " + counter);
            }   
        }

    }
}
