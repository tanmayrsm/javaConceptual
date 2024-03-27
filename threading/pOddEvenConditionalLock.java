import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class pOddEvenConditionalLock {
    private static final Lock lock = new ReentrantLock();
    private static final Condition oddCondition = lock.newCondition();
    private static final Condition evenCondition = lock.newCondition();
    private static int count = 1;

    public static void main(String[] args) {
        Thread d1 = new Thread(() -> printOdd());
        Thread d2 = new Thread(() -> printEven());

        d1.start();
        d2.start();

        try {
            d1.join();
            d2.join();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    private static void printOdd() {
        for (int i = 1; i <= 10; i += 2) {
            lock.lock();
            try {
                while (count % 2 == 0) {
                    // Release the lock and wait for an even number to be printed
                    oddCondition.await();
                }
                System.out.println("Odd :: " + i);
                count++;
                // Signal the other thread to print an even number
                evenCondition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    private static void printEven() {
        for (int i = 2; i <= 10; i += 2) {
            lock.lock();
            try {
                while (count % 2 == 1) {
                    // Release the lock and wait for an odd number to be printed
                    evenCondition.await();
                }
                System.out.println("Even :: " + i);
                count++;
                // Signal the other thread to print an odd number
                oddCondition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}

