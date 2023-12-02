package udemy.basic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionWait_13 {
    // wait and notify can be used as per object lock
    // conditional variables can also be used, providing more features
    
    static class Test {
        Queue<Integer> q;
        int qSize;
        Lock lock;
        Condition condition;    // condition var
        
        Test(int n) {
            this.qSize = n;
            q = new LinkedList<>();
            lock = new ReentrantLock();
            condition = lock.newCondition();
        }   

        public void doOperations() throws InterruptedException {
            Thread producer = new Thread(() -> {
                // synchronized(lock) {
                    lock.lock();
                    try {
                        while(!Thread.currentThread().isInterrupted()) {
                            Random r = new Random();
                            int random = r.nextInt();
                            if(q.size() == this.qSize) {
                                try {
                                    condition.await();
                                } catch (InterruptedException e) {
                                }
                            } else {
                                System.out.println("Produced :: " + random);
                                q.offer(random);
                                condition.signalAll();
                            }
                        }
                        if(Thread.currentThread().isInterrupted())  return;
                    } finally {
                        lock.unlock();
                    }
                // }
            });

            Thread consumer = new Thread(() -> {
                // synchronized(lock) {
                    lock.lock();
                    try {
                        while(!Thread.currentThread().isInterrupted()) {
                            if(q.size() == 0) {
                                try {
                                    condition.await();
                                } catch (InterruptedException e) {
                                }
                            } else {
                                System.out.println("Consuming :: " + q.poll() + " :: " + q.size());
                                condition.signalAll();
                            }
                        }
                        
                        if(Thread.currentThread().isInterrupted())  return;
                    } finally {
                        lock.unlock();
                    }
                // }
            });

            producer.start();   consumer.start();
            Thread.sleep(10);

            // interrupt after 5 second to end both thread
            producer.interrupt();   consumer.interrupt();

            
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Test t = new Test(5);
        t.doOperations();   
    }
}
