package sync;

public class LockingDemo {
    // Class-level (static) variable
    private static int classLevelCounter = 0;

    // Instance-level variable
    private int instanceLevelCounter = 0;

    // Class-level synchronized method
    public static synchronized void incrementClassLevelCounter() {
        // This method is synchronized on the class object (LockingDemo.class)
        classLevelCounter++;
        System.out.println("Class Level Counter: " + classLevelCounter +
                " - Thread: " + Thread.currentThread().getName());
    }

    // Instance-level synchronized method
    public synchronized void incrementInstanceLevelCounter() {
        // This method is synchronized on the current instance (this)
        instanceLevelCounter++;
        System.out.println("Instance Level Counter: " + instanceLevelCounter +
                " - Thread: " + Thread.currentThread().getName());
    }

    // Demonstration method
    public static void main(String[] args) {
        // Demonstrating Class-level Locking
        Runnable classLockTask = () -> {
            for (int i = 0; i < 5; i++) {
                incrementClassLevelCounter();
            }
        };

        // Demonstrating Instance-level Locking
        Runnable instanceLockTask = () -> {
            LockingDemo instance = new LockingDemo();
            for (int i = 0; i < 5; i++) {
                instance.incrementInstanceLevelCounter();
            }
        };

        // Create multiple threads to show locking behavior
        Thread thread1 = new Thread(classLockTask, "Thread-Class-1");
        Thread thread2 = new Thread(classLockTask, "Thread-Class-2");
        Thread thread3 = new Thread(instanceLockTask, "Thread-Instance-1");
        Thread thread4 = new Thread(instanceLockTask, "Thread-Instance-2");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}