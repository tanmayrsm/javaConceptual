
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Basic_1 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService ex = Executors.newFixedThreadPool(10);  // fixed thread pool size
        // Executors.newSingleThreadExecutor(); // single pool size
        // Executors.newCachedThreadPool(); // cached thread to be used, if task done
        // Executors.newScheduledThreadPool(20);    // scheduled task to start after some delay in each thread

        // we dont care about result
        ex.execute(() -> {  // runnable
            System.out.println("Inside runnable execute");
        });

        // we dont care about result, but need to wait until task is done
        Future<?> subRes = ex.submit(() -> {    // runnable
            System.out.println("Inside runnable submit");
        });

        System.out.println("is submit done??" + subRes.isDone());
        System.out.println("submit result :: " + subRes.get());

        // we care about result
        Future<?> subRes2 = ex.submit(() -> {   // callable
            try {
                Thread.sleep(4000); // API call
                return 10;

            } catch (InterruptedException e) {
            }
            return null;
        });

        // subRes2.cancel(boolean mayInterruptIfRunning); to cancel any executor in between
        try {
            System.out.println("Result we care :: " + subRes2.get(3, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            System.out.println("cant receive result in stipulated time");
        }

        // an infinite task
        ex.execute(() -> {
            Thread.currentThread();
            while(!Thread.interrupted()) {
                System.out.println("A long task...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted long task thread");
                    return;
                }
            }
        });

        ex.shutdown();  // will shut all .submit tasks
        Thread.sleep(1000);
        ex.shutdownNow();   // will close all .submit and .execute tasks
    }
}
