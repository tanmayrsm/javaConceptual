
public class accessOneObject_synchronized_way {
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

    public synchronized SharedObject getSharedObject() {
        while (sharedObject == null) {
            try {
                // Wait until the shared object is initialized
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return sharedObject;
    }

    public synchronized void initializeSharedObject() {
        if (sharedObject == null) {
            sharedObject = new SharedObject();
            // Notify all waiting threads that the shared object is now initialized
            notifyAll();
        }
    }
}
