package udemy.basic;

public class Simplethr_1 {
    private static int[] salesByDay = new int[] {0,23,33,44,55};
    public static void main(String[] args) {
        Thread x = new Thread(() -> {
            calculateSales(0,2);
        }, "calculation thread");
        x.start();
    }
    private static void calculateSales(int start, int end) {
        long totalSales = 0;
        for(int i = start; i <= end; i++) {
            totalSales += salesByDay[i];
        }
        Thread currentThread = Thread.currentThread();

        System.out.print("Total :: " + totalSales + " :: for start :: " + start + ":: end ::" + end +  "::\n By ::" + currentThread.getName() + " :: # " + currentThread.getId());

        // notes - 
        // BASE thread is always - main i.e. if u call this method normally (directly in main method)

    }
}
