package udemy.basic;

public class Volatile_5 {
    // volatile variable are shared among all threads, and share same value across all threads
    // they can be used to carry out common process in all threads
    // eg - here im stopping all threads, once a var 'isStopped' changes

    static class Test {
        public volatile int sharedVar = 0;
        public volatile boolean isStopped = false;
        public void doOperations() throws InterruptedException {
            Thread write = new Thread(() -> {
                while(!this.isStopped) {
                    this.sharedVar += 1;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            });

            Thread read = new Thread(() -> {
                while(!this.isStopped) {
                    System.out.println("Var :: " + this.sharedVar);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            });

            write.start();  read.start();

            Thread.sleep(4000);

            this.isStopped = true;
            System.out.println("Stopping!!");
            
            write.join();   read.join();
        }
    }
    public static void main(String[] args) {
        Test t = new Test();
        try {
            t.doOperations();
        } catch (InterruptedException e) {
        }
    }
}
