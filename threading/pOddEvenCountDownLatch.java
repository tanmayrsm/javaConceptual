import java.util.concurrent.CountDownLatch;

public class pOddEvenCountDownLatch {
    static class Printer extends Thread{
        CountDownLatch ct;
        Printer(int n) {
            ct = new CountDownLatch(n);
        }
        public void printOdd() throws InterruptedException {
            while (true) {
                // System.out.println("ct in odd::" + ct.getCount());
                if(ct.getCount() % 2 == 1) {
                    System.out.println(ct.getCount());
                    ct.countDown();
                }
                if(ct.getCount() == 0)  break;
            }
        }
        public void printEven() throws InterruptedException {
            while (true) {
                // System.out.println("ct in even::" + ct.getCount());
                if(ct.getCount() % 2 == 0) {
                    System.out.println(ct.getCount());
                    ct.countDown();
                }
                if(ct.getCount() == 0)  break;
            }
        }
        
    } 
    public static void main(String[] args) throws InterruptedException {
        Printer px = new Printer(10);
        Thread t1 = new Thread(() -> {
            try {
                px.printOdd();
            } catch (InterruptedException e) {
                System.err.println("Error in printing odd ::" + e);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                px.printEven();
            } catch (InterruptedException e) {
                System.err.println("Error in printing even ::" + e);
            }
        });

        t1.start();
        t2.start();

        t1.join();  t2.join();

        System.out.println("Done");
    }
}
