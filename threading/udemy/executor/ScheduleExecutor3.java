import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

public class ScheduleExecutor3 {
    // it helps overcome maximum drawbacks of timer like -
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);    // core size two, as one task is long
        Runnable task = () -> System.out.println("Output from normal scheduled task");
        // 1- can schedule same task - n no of times
        scheduledExecutorService.schedule(task, 0, TimeUnit.MILLISECONDS);
        scheduledExecutorService.schedule(task, 0, TimeUnit.MILLISECONDS);
        
        // 2 - exception task wont stop execution of toher tasks
        ScheduledFuture<?> resultOfException = scheduledExecutorService.schedule(() -> {
            System.out.println("Startign task with exception..");
            throw new RuntimeErrorException(null, "Thrown");
        }, 0, TimeUnit.MILLISECONDS);

        ScheduledFuture<Integer> st = scheduledExecutorService.schedule(() -> {
            System.out.println("Startign task with return result..");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }     // API CALL
            return 123;
        }, 0, TimeUnit.MILLISECONDS);

        // 3 - long task wont dominate other task ka execution
        ScheduledFuture<Integer> st2 = scheduledExecutorService.schedule(() -> {
            System.out.println("Startign long task with return result..");
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }     // API CALL
            return 123223;
        }, 0, TimeUnit.MILLISECONDS);

        // getting result of each scheduled task
        try {
            System.out.println("Res of excpetion ::" + resultOfException.get());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Exception task threw exception");
        }
        
        try {
            System.out.println("Res of normal task ::" + st.get());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("notmal return task threw exception");
        }

        try {
            System.out.println("Res of long task ::" + st2.get());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("long task threw exception");
        }

        // finally shut down executor
        scheduledExecutorService.shutdownNow(); // kill all execute and .submit

        
    }
}
