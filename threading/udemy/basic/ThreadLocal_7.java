package udemy.basic;

public class ThreadLocal_7 {
    // variables, which are local for each thread are associated with ThreadLocal type
    // in other words, private variable for each thread

    private final ThreadLocal<Integer> counterThreadLocal = ThreadLocal.withInitial(() -> 0);

    public class Worker extends Thread {
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                int curr = counterThreadLocal.get();
                System.out.println("ID :: " + Thread.currentThread().getId() + " :: value of local private var :: " + curr);
                counterThreadLocal.set(curr + 1);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }

            }
            if(Thread.currentThread().isInterrupted())  return;
        }
    }

    public void okay() throws InterruptedException {
        Worker w1 = new Worker();
        Worker w2 = new Worker();
        
        w1.start();
        w2.start();

        Thread.sleep(500);

        w1.interrupt();
        w2.interrupt();

        w1.join();
        w2.join();
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocal_7 tt = new ThreadLocal_7();
        tt.okay();
        
    }
    
}
