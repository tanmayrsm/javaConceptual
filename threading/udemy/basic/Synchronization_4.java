package udemy.basic;

public class Synchronization_4 {
    static int[] cost = new int[] {1,2,3,4,5,6,7,8,9,10};
    static int total = 0;
    public static void main(String[] args) {
        Long startTime = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            Operations.ADD();
        });

        Thread t2 = new Thread(() -> {
            Operations.SUB();
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
            Long endTime = System.currentTimeMillis() - startTime;
            System.out.println("Total time :: " + endTime + "ms");
        } catch (InterruptedException e) {
            System.out.println("Interruption occured");
        }

        System.out.println("Final value of total ::" + total);
    }
    static class Operations {
        synchronized public static void ADD() {  // Add nos from 0 to 4
            // lock added due to synchronized keyword above
            System.out.println("LOCK ADDED for ADD method :: " + Thread.currentThread().getName());
            for(int i = 0; i < 100; i++) {
                total += i;
            }
            System.out.println("UNLOCK DONE for ADD method :: " + Thread.currentThread().getName());
            // unlock done
        }

        synchronized public static void SUB() {  // Subtract nos from 0 to 4
            System.out.println("LOCK ADDED for SUB method :: " + Thread.currentThread().getName());
            
            for(int i = 0; i < 100; i++) {
                total -= i;
            }
            
            System.out.println("UNLOCK DONE for SUB method :: " + Thread.currentThread().getName());
        }
    }
}
