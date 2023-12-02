package udemy.basic;

public class InterruptThread_3 {
    // u can use interrupt instead of making a thread as DAEMON
    // u can externally interrupt same thread, else keep on running it in background unless interrupted

    private static int[] salesByDay = new int[] {0,23,33,44,55};
    public static void main(String[] args) {
        Thread calcuThread = new Thread(() -> {
            calculateSales(0,2);
        }, "calculation thread");
        
        Thread backupThr = new Thread(() -> {
            backUp();
        }, "backup thread");
        calcuThread.start();

        backupThr.start();  // this infinite thread will stop, once interrupted externally
        
        try {
            calcuThread.join();
            backupThr.interrupt();  // LOGICAL - interrupting, has to happen before thread completion, i.e. before backUpThr.join()
            backupThr.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Calculation done!");
    }

    private static void calculateSales(int start, int end) {
        long totalSales = 0;
        for(int i = start; i <= end; i++) {
            totalSales += salesByDay[i];
        }
        Thread currentThread = Thread.currentThread();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.err.println("Error in caluclating");
            e.printStackTrace();
        }

        System.out.print("Total :: " + totalSales + " :: for start :: " + start + ":: end ::" + end +  
        "::\n By ::" + currentThread.getName() + " :: # " + currentThread.getId());
    }

    private static void backUp() {
        while(true) {
            try {
                // write data in file
                Thread.sleep(1000);
                System.out.println("Writing in file by thread :: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() +  " :: has been interrupted");
                return; // THREAD WONT STOP AUTOMATICALLY INSIDE EXCEPTION, hence return
            }
        }
    }

}
