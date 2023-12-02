package udemy.concurrentCollections;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class nonBlocking_queue_and_blockingqueue_3 {

    // concurrent queues - provide non-blocking operations on add/remove from queue data structure
    // its completely thread safe, as atomic type operations occur on add/remove elements from queue
     
    private static final int NUM_ELEMENTS = 1000;
    private static final int NUM_PRODUCERS = 2;
    private static final int NUM_CONSUMERS = 2;

    public static void main(String[] args) throws InterruptedException {
        // Using a synchronized queue
        Queue<Integer> synchronizedQueue = new LinkedList<>();

        // Using a concurrent queue
        Queue<Integer> concurrentQueue = new ConcurrentLinkedQueue<>();

        // Using a blocking queue
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(NUM_ELEMENTS);  // advantage of blocking queue, u can block operations, when queue is full



        // Run with synchronized queue
        runProducerConsumer(synchronizedQueue, "Synchronized Queue");

        // Run with concurrent queue
        runProducerConsumer(concurrentQueue, "Concurrent Queue");

        // Run with blocking queue
        runProducerConsumer(blockingQueue, "Blocking Queue");
    }

    private static void runProducerConsumer(Queue<Integer> queue, String queueType) throws InterruptedException {
        Thread[] producers = new Thread[NUM_PRODUCERS];
        Thread[] consumers = new Thread[NUM_CONSUMERS];

        long startTime = System.currentTimeMillis();

        // Create and start producer threads
        for (int i = 0; i < NUM_PRODUCERS; i++) {
            producers[i] = new Thread(() -> {
                for (int j = 0; j < NUM_ELEMENTS / NUM_PRODUCERS; j++) {
                    queue.offer(j);
                    // System.out.println(Thread.currentThread().getName() + " produced: " + j);
                }
            });
            producers[i].start();
        }

        // Create and start consumer threads
        for (int i = 0; i < NUM_CONSUMERS; i++) {
            consumers[i] = new Thread(() -> {
                for (int j = 0; j < NUM_ELEMENTS / NUM_CONSUMERS; j++) {
                    Integer element = queue.poll();
                    // Process the element (e.g., print it)
                    // if (element != null) {
                    //     System.out.println(Thread.currentThread().getName() + " consumed: " + element);
                    // }
                }
            });
            consumers[i].start();
        }

        // Wait for all producers to finish
        for (Thread producer : producers) {
            producer.join();
        }

        // Wait for all consumers to finish
        for (Thread consumer : consumers) {
            consumer.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println(queueType + " Time: " + (endTime - startTime) + " ms");
    }
}
