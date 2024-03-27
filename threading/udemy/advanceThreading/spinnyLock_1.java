package udemy.advanceThreading;


// Spinny lock - 
// use case - run two threads - ping-pong paralleyly
// but ping always needs to be printed beofre pong
// i.e successive pings, pongs arent allowed
public class spinnyLock_1 {
    private static volatile boolean doPing = false;
    public static void main(String[] args) throws InterruptedException {
        Ping p1 = new Ping();
        Pong p2 = new Pong();
        p1.start();
        p2.start();
        Thread.sleep(1000);
        p1.interrupt(); p2.interrupt();
        p1.join(); p2.join();
    }
    public static class Ping extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    if (doPing == false)
                        continue;
                    System.out.println("---Ping");
                    doPing = false;
                } finally {
                }
            }
        }
    }
    public static class Pong extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    if (doPing == true)
                        continue;
                    System.out.println("Pong");
                    doPing = true;
                } finally {
                }
            }
        }
    }
}
