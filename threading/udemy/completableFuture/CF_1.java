package udemy.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CF_1 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        simpleFutureExample();
        
        completableFutureNormal();

        combinedCompletableFuture();    // thenCombineAsync

        composeCompletableFuture(); // compose
    }

    private static void composeCompletableFuture() throws InterruptedException, ExecutionException {
        CompletableFuture<CompletableFuture<Integer>> res = CompletableFuture.supplyAsync(() -> 3 + 3)
        .thenApplyAsync((value) -> someFunction()); // bad nested completable future practive
        
        // thenComposeAsync - break result from COmpletableFuture<X> to just X
        CompletableFuture<Integer> res2 = CompletableFuture.supplyAsync(() -> 3 + 3)
                                                            .thenComposeAsync((value) -> someFunction());
                                                            
        // to ensure each completable gets executed
        System.out.println("Res1 ::" + res.get().get());
        System.out.println("Res2 ::" + res2.get());
    }

    public static CompletableFuture<Integer> someFunction() {
        return CompletableFuture.completedFuture(12);
    }
    private static void combinedCompletableFuture() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> f1 = CompletableFuture.completedFuture(123);
        CompletableFuture<Integer> f2 = new CompletableFuture<>();
        // do computation
        f2.complete(34);
                                            // f1 combine with f2
        CompletableFuture<Void> combinedRes = f1.thenCombineAsync(f2, (f1Res, f2Res) -> f1Res + f2Res)
                                                    .thenAcceptAsync(System.out :: println);
        combinedRes.get();
    }

    private static void completableFutureNormal() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> cF = CompletableFuture.supplyAsync(() -> 2 + 2) // async keyword in each method means that it will execute in separate thread
                                        .thenApplyAsync((res) -> res + (3 + 3))
                                        .thenAcceptAsync((finRes) -> System.out.println("Final res completableFuture ::" + finRes));    // just like forEach of lambda - terminator function
        
        // TEDIOUS code start
        //
        // ...TEDIOUS code end
        cF.get();   // non blocking line 
    }

    private static void simpleFutureExample() throws InterruptedException, ExecutionException {
        ExecutorService ex = Executors.newFixedThreadPool(2);
        Future<Integer> f1 = ex.submit(() -> 2 + 2);
        // TEDIOUS code start
        //
        // ...TEDIOUS code end
        Integer f1Res = f1.get();   // this line will block below portion of code

        Future<Integer> f2 = ex.submit(() -> 3 + 3);
        // TEDIOUS code start
        //
        // ...TEDIOUS code end
        Integer f2Res = f2.get();   // this line will block below portion of code

        Future<Integer> res = ex.submit(() -> f1Res + f2Res);
        System.out.println("Fin res :: " + res.get());

        ex.shutdownNow();
    }
}
