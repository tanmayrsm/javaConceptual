package udemy.basic;

import java.util.concurrent.Phaser;

public class Phaser_18 {

    public static void main(String[] args) {
        // Create a Phaser with one registered party (main thread)
        Phaser phaser = new Phaser(1);

        // Create three worker threads
        WorkerThread worker1 = new WorkerThread("Worker 1", phaser);
        WorkerThread worker2 = new WorkerThread("Worker 2", phaser);
        WorkerThread worker3 = new WorkerThread("Worker 3", phaser);

        // Start the worker threads
        worker1.start();
        worker2.start();
        worker3.start();

        // Allow the worker threads to start and synchronize at the first phase
        phaser.arriveAndDeregister();

        // Main thread now waits for the worker threads to complete all phases
        phaser.awaitAdvance(phaser.getPhase());

        System.out.println("All workers have completed all phases. Main thread can proceed.");
    }

    static class WorkerThread extends Thread {
        private final Phaser phaser;

        public WorkerThread(String name, Phaser phaser) {
            super(name);
            this.phaser = phaser;
            // Register this thread with the Phaser
            this.phaser.register();
        }

        @Override
        public void run() {
            for (int phase = 0; phase < 3; phase++) {
                System.out.println(getName() + " is performing phase " + phase);
                // Synchronize with other threads at the end of each phase
                phaser.arriveAndAwaitAdvance();
            }
            // Deregister this thread from the Phaser after completing all phases
            phaser.arriveAndDeregister();
        }
    }
}
