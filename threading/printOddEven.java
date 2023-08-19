import java.util.*;

class HelloWorld {
    static class PrintNumber {
        int  N;
        PrintNumber(int ct) {
            N = ct;
        }
        public void even() {
            synchronized(this) {
                for(int i = 1; i <= N; i++) {
                    try {
                        if(i % 2 == 0) {
                            System.out.println(":: even :: " + i);
                            wait();
                        } else  notify();
                    } catch(Exception e) {
                        System.out.print(":: even exception :: " + e);
                    }
                }
            }
        }
        public void odd() {
            synchronized(this) {
                for(int i = 1; i <= N; i++) {
                    try {
                        if(i % 2 == 1) {
                            System.out.println(":: odd :: " + i);
                            wait();
                        } else  notify();
                    } catch(Exception e) {
                        System.out.print(":: even exception :: " + e);
                    }
                }
            }
        }
        
    }
    public static void main(String[] args) {
        PrintNumber p = new PrintNumber(20);
        Thread evenPrinter = new Thread(() -> p.even());
        Thread oddPrinter = new Thread(() -> p.odd());
        
        oddPrinter.start();
        evenPrinter.start();
        System.out.println("Hello!");
    }
}