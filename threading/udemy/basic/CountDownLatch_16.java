package udemy.basic;

import java.util.concurrent.CountDownLatch;

public class CountDownLatch_16 {

        public static void main(String[] args) {
            // Create a CountDownLatch with a count of 3
            CountDownLatch latch = new CountDownLatch(3);
    
            // Create three worker threads
            WorkerThread worker1 = new WorkerThread("Worker 1", latch);
            WorkerThread worker2 = new WorkerThread("Worker 2", latch);
            WorkerThread worker3 = new WorkerThread("Worker 3", latch);
    
            // Start the worker threads
            worker1.start();
            worker2.start();
            worker3.start();
    
            try {
                // Main thread waits for the latch count to reach zero
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    
            // All worker threads have completed their work
            System.out.println("All workers have completed their tasks. Main thread can proceed.");
        }
    
        static class WorkerThread extends Thread {
            private final CountDownLatch latch;
    
            public WorkerThread(String name, CountDownLatch latch) {
                super(name);
                this.latch = latch;
            }
    
            @Override
            public void run() {
                // Simulate some work
                System.out.println(getName() + " is performing some work.");
                
                // Decrement the latch count
                latch.countDown();
                
                System.out.println(getName() + " has completed its task.");
            }
        }
}
