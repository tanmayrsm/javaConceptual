import java.util.*;

class HelloWorld {
    static class G {
        int  N;
        G(int ct) {
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
        G amba = new G(20);
        Thread eveni = new Thread(() -> amba.even());
        Thread oddi = new Thread(() -> amba.odd());
        
        oddi.start();
        eveni.start();
        System.out.println("Hello, World!");
    }
}