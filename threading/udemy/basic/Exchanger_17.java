package udemy.basic;

import java.util.concurrent.Exchanger;

public class Exchanger_17 {

    // Exchanger class is a synchronization point at which threads can pair and swap elements within pairs. Each thread presents some object on entry to the exchange method, matches with a partner thread, and receives its partner's object on return. This can be useful in scenarios where two threads need to exchange data in a concurrent program.

    // sometimes below program gets stuck, if some exchange data is not available
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        // Create three threads
        Thread thread1 = new Thread(new Worker("1", "Data from 1", exchanger));
        Thread thread2 = new Thread(new Worker("2", "Data from 2", exchanger));
        Thread thread3 = new Thread(new Worker("3", "Data from 3", exchanger));

        // Start the threads
        thread1.start();
        thread2.start();
        thread3.start();
    }

    static class Worker implements Runnable {
        private final String name;
        private final String data;
        private final Exchanger<String> exchanger;

        public Worker(String name, String data, Exchanger<String> exchanger) {
            this.name = name;
            this.data = data;
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + " has data: " + data);

                // Exchange data with the other two threads
                String exchangedData1 = exchanger.exchange(data);
                String exchangedData2 = exchanger.exchange(exchangedData1);

                System.out.println(name + " received exchanged data #1: " + exchangedData1);
                System.out.println(name + " received exchanged data #2: " + exchangedData2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
