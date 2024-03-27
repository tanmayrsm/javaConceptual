package udemy.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CF_advance_2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> future1 = CompletableFuture.completedFuture(11);
        CompletableFuture<Integer> future2 = new CompletableFuture<>();

        // 5. thenAcceptBothAsync/runAfterBothAsync waits for completion of both futures
        future1.thenAcceptBothAsync(future2, (f1, f2) -> System.out.println(f1 + f2));
        future1.runAfterBothAsync(future2, () -> System.out.println("something..."));

        // 6. waits for the first available result from the future1 or future2
        future1.acceptEitherAsync(future2, (f) -> System.out.println(f));
        future1.applyToEitherAsync(future2, (f) -> f + f);
        future1.runAfterEitherAsync(future2, () -> System.out.println("something..."));

        // 7.1 allOf waits for all futures
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(future1, future2);
        allOfFuture.get();

        // 7.2 anyOf waits for the first available future
        CompletableFuture<Object> anyOfResult = CompletableFuture.anyOf(future1, future2);
        anyOfResult.get();

        // 8 exceptionally or handle methods for handling an exception in a future object
        CompletableFuture<Integer> res2 = CompletableFuture.supplyAsync(() -> someFunctionWithException(-55))
                .exceptionally((e) -> {
                    System.out.println("Caught an exception");
                    return 0;
                });
        System.out.println(res2.get());

        CompletableFuture<Integer> res3 = CompletableFuture.supplyAsync(() -> someFunctionWithException(66))
                .handle((value, ex) -> {
                   if (ex != null) {
                       System.out.println("Caught an exception in the handle method");
                       return 0;
                   } else {
                       return value;
                   }
                });
        System.out.println(res3.get());

        // 9. Complete a future with exception
        CompletableFuture<Integer> future3 = new CompletableFuture<>();
        future3.completeExceptionally(new RuntimeException());
        //future3.cancel();

        // 10. Custom ExecutorService
        ExecutorService customThreadPool = Executors.newFixedThreadPool(2);
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return 33;
        }, customThreadPool);
        f1.get();
        customThreadPool.shutdownNow();    
    }

    static int someFunctionWithException(int value) {
        if (value < 0) {
            throw new RuntimeException("The value less the 0");
        }

        return value * value;
    }
    
}
