package udemy.basic;

public class LiveLock_10 {
    // example - 
    // police asks crimial to release hostage first, then he will get money
    // criminal asks police to send money first, then he will release hostage
    // hence both are waiting for each other, to take action first
    // this is LIVELOCK

    private static boolean sendMoney, hostageRelease;
    public static void main(String[] args) {
        Thread criminalAction = new Thread(() -> {
            while(true) {
                if(sendMoney)
                    releaseHostage();
            }
        });
        Thread policeAction = new Thread(() -> {
            while(true) {
                if(hostageRelease)
                    moneySend();
            }
        });
        
        criminalAction.start(); policeAction.start();
    }

    private static void moneySend() {
        sendMoney = true;
    }
    private static void releaseHostage() {
        hostageRelease = true;
    }
}
