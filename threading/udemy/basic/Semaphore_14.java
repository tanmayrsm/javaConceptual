package udemy.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class Semaphore_14 {
    // Semaphores are used in a case, where no of resources (in this example - no of cores) are less than no of workers (threads)
    // HOW IT WORKS ?
    // as resources are less, semaphore will take 'n' threads, where 'n' is no of resources
    // allocate them to perform task
    // let next n + 1, n + 2.... threads wait in queue unless first 'n' release the resource

    private static final int NO_OF_CORES_IN_MY_MACHINE = Runtime.getRuntime().availableProcessors();

    // making no of resources - 
    private static Semaphore semaphore = new Semaphore(NO_OF_CORES_IN_MY_MACHINE, true);
                                                      // permits no - NO OF CORES IN MY MACHINE
                                                      // if permits == 1 -> semaphore acts like a normal LOCK        
    public static void main(String[] args) {
        System.out.println("No of cores available for my machine :: " + NO_OF_CORES_IN_MY_MACHINE);
        Semaphore_14 sm = new Semaphore_14();
        sm.start();

    }
    void start() {
        int noOfWorkers = NO_OF_CORES_IN_MY_MACHINE + 2;    // more workers than cores available
        List<WorkerThread> workers = new ArrayList<>();
        IntStream.range(0, noOfWorkers + 1).forEach(item -> {
            WorkerThread worker = new WorkerThread();
            workers.add(worker);
        });
        System.out.println("Total workers :: " + workers.size());

        // start all threads who are in access of 2, than ur no of cores
        for(WorkerThread worker : workers) {
            worker.start();
        }

        for(WorkerThread worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted :: " + worker.getId());
            }
        }
    }

    class WorkerThread extends Thread {
        @Override
        public void run() {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                return;    
            }
            try {
                try {
                    System.out.println("Doing useful work for thread  : " + Thread.currentThread().getId());
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } finally {
                semaphore.release();
                System.out.println("Work done for thread : " + Thread.currentThread().getId());
            }
        }
    }
}
