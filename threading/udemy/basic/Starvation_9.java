package udemy.basic;

public class Starvation_9 {
    private final static Object lock = new Object();

    private static int counter = 0;
    
    public static void main(String[] args) {
        start();   
    }

    private static void start() {
        Thread t1 = new Thread(() -> {
            makeWork();
        });
        Thread t2 = new Thread(() -> {
            makeWork();
        });

        t1.start();
        t2.start();

        System.out.println("Final counter : " + counter);
           
    }

    private static void makeWork() {
        for(int i = 0; i < 100; i++) {
            synchronized(lock) {    // STARVATION - so at max, here we can have, overall 100 waiting instances of 1 thread
                counter++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    // FIX FOR STARVATION
    // private static void makeWork() {
    //     int tempCtr = 0;
    //     for(int i = 0; i < 100; i++) {
    //         tempCtr++;
    //         try {
    //             Thread.sleep(100);
    //         } catch (InterruptedException e) {
    //         }
    //     }
    //     synchronized(lock) {   // FIX FOR STARVATION
    //         counter += tempCtr;
    //     }
    // }

}
