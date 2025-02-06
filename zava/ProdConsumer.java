import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ProdConsumer {
    public static BlockingQueue<Integer> pq;
    static class PC extends Thread {
        int no;
        PC(int no) {
            this.no = no;
            pq = new LinkedBlockingQueue<>(3);
        }
        public void produce() {
            try {
                Thread.sleep(1000);
                while(!pq.offer(this.no)) {
                    
                }
                System.out.println("Produced ::" + this.no);

            } catch(InterruptedException e) {
                System.err.println("error in sleep");
            }
        }
        public void consume() {
            try {
                Thread.sleep(5000);
                while(true) {
                    if(!pq.isEmpty()) {
                        System.out.println(pq.poll() + ":: consumed");
                        break;
                    }
                }

            } catch(InterruptedException e) {
                System.err.println("error in sleep");
            }
        }

        @Override
        public void run() {
            produce();
            consume();
        }
    }
    public static void main(String[] args) {
        IntStream.range(1, 15).
        forEach(item -> {
            new PC(item).start();
        });

    }
}
