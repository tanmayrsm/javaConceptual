package udemy.basic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class WaitNotify_6 {
    // wait and notify can be used as per object lock
    // its used to wait for notification by other thread(s)
    
    static class Test {
        Queue<Integer> q;
        int qSize;
        Object lock;
        
        Test(int n) {
            this.qSize = n;
            q = new LinkedList<>();
            lock = new Object();
        }   

        public void doOperations() throws InterruptedException {
            Thread producer = new Thread(() -> {
                synchronized(lock) {
                    while(!Thread.currentThread().isInterrupted()) {
                        Random r = new Random();
                        int random = r.nextInt();
                        if(q.size() == this.qSize) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                            }
                        } else {
                            System.out.println("Produced :: " + random);
                            q.offer(random);
                            lock.notify();
                        }
                    }
                    if(Thread.currentThread().isInterrupted())  return;
                }
            });

            Thread consumer = new Thread(() -> {
                synchronized(lock) {
                    while(!Thread.currentThread().isInterrupted()) {
                        if(q.size() == 0) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                            }
                        } else {
                            System.out.println("Consuming :: " + q.poll() + " :: " + q.size());
                            lock.notify();
                        }
                    }
                    
                    if(Thread.currentThread().isInterrupted())  return;
                }
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
