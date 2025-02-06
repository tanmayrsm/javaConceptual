import java.util.concurrent.CountDownLatch;

public class FiveThreads {
    private static CountDownLatch ct = new CountDownLatch(1);
    static class Lame extends Thread {
        int id;
        
        Lame(int id) {
            this.id = id;
        }
        
        public void initiate() {
            System.out.println("Initiating by ::" + this.id);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ct.countDown();
            System.out.println("Initiation complted");
        }

        @Override
        public void run() {
            try {
                ct.await();
            } catch(InterruptedException e) {
                System.out.println("Error in await");
            }
            System.out.println("Accessed ::" + this.id);
        }
    }
    public static void main(String[] args) {
        Lame[] lames = new Lame[15];
        for(int i = 0; i < lames.length; i++) {
            lames[i] = new Lame(i);
            lames[i].start();
            if(i == 4) {
                lames[i].initiate();
            }
        }
        for(int i = 0; i < lames.length; i++) {
            try {
                lames[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("done!!");
    }
}