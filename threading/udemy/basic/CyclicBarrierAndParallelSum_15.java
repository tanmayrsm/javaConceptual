package udemy.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import udemy.basic.ThreadLocal_7.Worker;

public class CyclicBarrierAndParallelSum_15 {
    private final static int NUMBER_OF_THREADS = 4;
    private int totalSum = 0;
    private final List<Worker> workers = new ArrayList<>();

    private final CyclicBarrier barrier = new CyclicBarrier(NUMBER_OF_THREADS, () -> {
        // what to do with result of barrier from 4 threads? 
        // do it here
        for(Worker worker : workers) {
            totalSum += worker.sum;
        }
        System.out.println("Total sum  :: " + totalSum);
    });

    public static void main(String[] args) {
        CyclicBarrierAndParallelSum_15 cyclicBarrierAndParallelSum_15 = new CyclicBarrierAndParallelSum_15();
        cyclicBarrierAndParallelSum_15.start();
    }
    private void start() {
        
        for(int i = 0; i < NUMBER_OF_THREADS; i++) {
            List<Integer> newArr = new ArrayList<>();
            for(int j = 0; j < 4; j++)  
                newArr.add(j + 1);
            Worker worker = new Worker(newArr);
            workers.add(worker);
            worker.start();
        }

        for(Worker worker : workers)
            try {
                worker.join();
            } catch (InterruptedException e) {
            }
        System.out.println("Total fin sum :: " + totalSum);
    }

    class Worker extends Thread {
        final List<Integer> partialArrayForThisThread;
        int sum = 0;

        Worker(List<Integer> partialArrayForThisThread) {
            this.partialArrayForThisThread = partialArrayForThisThread;
        }

        @Override
        public void run() {
            for(Integer item : this.partialArrayForThisThread) {
                sum += item;
            }

            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
                interrupt();
            }

            if(isInterrupted()) return;
        }
    }
}
