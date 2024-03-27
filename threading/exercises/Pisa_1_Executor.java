package exercises;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
public class Pisa_1_Executor {
    // do Pisa_1 using executor service 

    // static CopyOnWriteArrayList<Integer> q = new CopyOnWriteArrayList<>(); // BEST CHOICE
    // static List<Integer> q = Collections.synchronizedList(new ArrayList<>());   // GOOD CHOICE, but more time in execution
    public static void main(String[] args) throws Exception {
        int n = 10;
        ExecutorService ex = Executors.newFixedThreadPool(3);
        List<GetCurrentValue> list = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            list.add(new GetCurrentValue());
        }
        List<Future<Integer>> list2 = ex.invokeAll(list);

        ex.shutdownNow();
        
        System.out.println("final list :: ");
        for(Future<Integer> x : list2)
            System.out.print(x.get() + "::");
    }

    static class GetCurrentValue implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(1000);
            Random x = new Random();
            return x.nextInt(100,222);
        }
    }
}
