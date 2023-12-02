package udemy.basic;

public class JoinAndDaemon_2 {
    // join
    // - in case of normal user created threads, join will wait to execute after thread.join()
    // - in case of daemon thread, join does not exist, as daemon thread can wait until user created threads are done

    private static int[] salesByDay = new int[] {0,23,33,44,55};
    public static void main(String[] args) {
        Thread calcuThread = new Thread(() -> {
            calculateSales(0,2);
        }, "calculation thread");
        
        Thread backupThr = new Thread(() -> {
            backUp();
        }, "backup thread");
        calcuThread.start();

        // dependent on execution of other user threads
        backupThr.setDaemon(true);
        backupThr.start();  // this infinite thread will stop, once other user defined threads have stopped

        try {
            calcuThread.join();
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
                e.printStackTrace();
            }
        }
    }
}
