import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// GOLDMAN SACHS - 4TH round question scenario - 4th Oct 2023

public class ProducerConsumerExample {
    public static void main(String[] args) {
        // Creating a shared blocking queue with a capacity of 5
        BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<>(5);

        // Creating two producer threads
        Thread producer1 = new Thread(new Producer(sharedQueue, "Producer 1"));
        Thread producer2 = new Thread(new Producer(sharedQueue, "Producer 2"));

        // Creating a consumer thread
        Thread consumer = new Thread(new Consumer(sharedQueue, "Consumer"));

        // Starting the threads
        producer1.start();
        producer2.start();
        consumer.start();
    }
}

class Producer implements Runnable {
    private final BlockingQueue<Integer> sharedQueue;
    private final String name;
    private static int counter = 1;

    public Producer(BlockingQueue<Integer> sharedQueue, String name) {
        this.sharedQueue = sharedQueue;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (true) {
                produce();
                Thread.sleep(1000); // Simulating some work before producing the next item
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void produce() throws InterruptedException {
        int item = counter++;
        System.out.println(name + " produced: " + item);
        sharedQueue.put(item);
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<Integer> sharedQueue;
    private final String name;

    public Consumer(BlockingQueue<Integer> sharedQueue, String name) {
        this.sharedQueue = sharedQueue;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (true) {
                consume();
                Thread.sleep(2000); // Simulating some work before consuming the next item
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void consume() throws InterruptedException {
        int item = sharedQueue.take();
        System.out.println(name + " consumed: " + item);
    }
}
