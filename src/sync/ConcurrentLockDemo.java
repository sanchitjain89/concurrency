package sync;

public class ConcurrentLockDemo {
    // Static variable
    private static int staticCounter = 0;

    // Instance variable
    private int instanceCounter = 0;

    // Static synchronized method
    public static synchronized void incrementStaticCounter() {
        staticCounter++;
        System.out.println("Static Counter: " + staticCounter +
                " - Thread: " + Thread.currentThread().getName());
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    // Instance synchronized method
    public synchronized void incrementInstanceCounter() {
        instanceCounter++;
        System.out.println("Instance Counter: " + instanceCounter +
                " - Thread: " + Thread.currentThread().getName());
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    public static void main(String[] args) {
        ConcurrentLockDemo obj1 = new ConcurrentLockDemo();
        ConcurrentLockDemo obj2 = new ConcurrentLockDemo();

        // Thread working on obj1's synchronized method
        Thread thread1 = new Thread(() -> {
            obj1.incrementInstanceCounter();
        }, "Thread-1");

        // Thread working on obj2's synchronized method
        Thread thread2 = new Thread(() -> {
            obj2.incrementInstanceCounter();
        }, "Thread-2");

        // Thread working on static synchronized method
        Thread thread3 = new Thread(() -> {
            incrementStaticCounter();
        }, "Thread-3");

        // Start all threads
        thread1.start();
        thread2.start();
        thread3.start();
    }
}