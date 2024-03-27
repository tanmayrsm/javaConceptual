package exercises;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Pisa_1 {
    // 1.You are given a integer array as input.
    // 2.Create an individual thread for each integer in the array and sleep the thread for integer value of the array element.
    // 3.After the thread sleep time is completed, add it to the output list and return the final list.

    // static CopyOnWriteArrayList<Integer> q = new CopyOnWriteArrayList<>(); // BEST CHOICE
    static List<Integer> q = Collections.synchronizedList(new ArrayList<>());   // GOOD CHOICE, but more time in execution
    public static void main(String[] args) throws Exception {
        int n = 10;
        for(int i = 0; i < n; i++)
            q.add(-1);

        Thread[] th = new Thread[n];
        for(int i = 0; i < n; i++) {
            final int x = i;
            th[i] = new Thread(() ->  {
                GetCurrentValue g = new GetCurrentValue();
                try {
                    int p = g.call();
                    q.set(x, p);
                } catch (Exception e) {
                    System.out.println("Error while filling detail in output");
                }
            });

            th[i].run();
        }

        for(Thread x : th)
            x.join();
        
        System.out.println("final list :: ");
        for(Integer x : q)
            System.out.print(x + "::");
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
