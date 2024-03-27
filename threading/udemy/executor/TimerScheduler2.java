import java.util.Timer;
import java.util.TimerTask;

import javax.management.RuntimeErrorException;

public class TimerScheduler2 {
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer("timer-thread", false);
        TimerTask timerTask = new TimerTask() { // simple task
            @Override
            public void run() {
                System.out.println("Task executed from timer..");
            }
        };
        TimerTask timerExceptionTask = new TimerTask() {    // exception task
            @Override
            public void run() {
                throw new RuntimeErrorException(null, "Error from exception task..");
            }
        };
        TimerTask longTimerTask = new TimerTask() { // a long task
            @Override
            public void run() {
                System.out.println("A long task started..");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println("error in long task");
                }
                System.out.println("Long task ended..");
            }
        };

        // drawbacks of timer class
        // timer.schedule(timerExceptionTask, 0, 100);  // 1- if any one timer's scheduled task throws exception, rest timer tasks wont run
        timer.schedule(longTimerTask, 0, 100);  // 2- if a task takes too long, the next task which takes less time, wont execute
        timer.schedule(timerTask, 100, 100);    // 3 - u cant schedule one task twice i.e writing same line will throw error
        
        Thread.sleep(4000);
        timer.cancel(); // if not a daemon timer, will need this
    }
}
