import java.util.concurrent.CountDownLatch;

public class accessOneObject_CountDownLatch_way {
    public static void main(String[] args) {
        SharedObjectCreator creator = new SharedObjectCreator();

        // Create and start 5 threads
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                SharedObject sharedObject = creator.getSharedObject();
                System.out.println(Thread.currentThread().getName() + " got instance: " + sharedObject);
            });
            thread.start();
        }

        // Simulate the initialization of the shared object by one of the threads
        // In a real scenario, this might happen based on some condition or event.
        creator.initializeSharedObject();
    }
}

class SharedObject {
    // Your shared object's properties and methods go here
}

class SharedObjectCreator {
    private static SharedObject sharedObject;
    private final CountDownLatch initializationLatch = new CountDownLatch(1);

    public SharedObject getSharedObject() {
        try {
            // Wait until the shared object is initialized
            initializationLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return sharedObject;
    }

    public void initializeSharedObject() {
        if (sharedObject == null) {
            sharedObject = new SharedObject();
            // Countdown the latch, allowing waiting threads to proceed
            initializationLatch.countDown();
        }
    }
}
