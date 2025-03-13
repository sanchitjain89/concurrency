package sync;

public class MonitorLockDemo {
    // Static (class-level) variable
    private static int classLevelCounter = 0;

    // Instance-level variable
    private int instanceCounter = 0;

    // Static synchronized method (class-level lock)
    public static synchronized void incrementClassLevelCounter() {
        classLevelCounter++;
        System.out.println("Class Counter: " + classLevelCounter +
                " - Thread: " + Thread.currentThread().getName());
    }

    // Static synchronized block (class-level lock)
    public static void incrementClassLevelCounterWithBlock() {
        synchronized (MonitorLockDemo.class) {
            classLevelCounter++;
            System.out.println("Class Counter (Block): " + classLevelCounter +
                    " - Thread: " + Thread.currentThread().getName());
        }
    }

    // Instance-level synchronized method
    public synchronized void incrementInstanceCounter() {
        instanceCounter++;
        System.out.println("Instance Counter: " + instanceCounter +
                " - Thread: " + Thread.currentThread().getName());
    }

    // Instance-level synchronized block
    public void incrementInstanceCounterWithBlock() {
        synchronized (this) {
            instanceCounter++;
            System.out.println("Instance Counter (Block): " + instanceCounter +
                    " - Thread: " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        // Demonstrating class-level and instance-level locking
        MonitorLockDemo obj1 = new MonitorLockDemo();
        MonitorLockDemo obj2 = new MonitorLockDemo();

        // Threads for class-level lock demonstration
        Thread classLockThread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                incrementClassLevelCounter();
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }, "ClassLockThread-1");

        Thread classLockThread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                incrementClassLevelCounterWithBlock();
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }, "ClassLockThreadBlock-2");

        // Threads for instance-level lock demonstration
        Thread instanceLockThread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                obj1.incrementInstanceCounter();
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }, "InstanceLockThread-1");

        // Threads for instance-level lock demonstration
        Thread instanceLockThreadBlock1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                obj1.incrementInstanceCounterWithBlock();
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }, "InstanceLockThread-1-Block");

        Thread instanceLockThread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                obj2.incrementInstanceCounter();
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }, "InstanceLockThread-2");

        Thread instanceLockThreadBlock2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                obj2.incrementInstanceCounterWithBlock();
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }, "InstanceLockThread-2-Block");

        // Start threads
        classLockThread1.start();
        classLockThread2.start();
        instanceLockThread1.start();
        instanceLockThread2.start();
        instanceLockThreadBlock1.start();
        instanceLockThreadBlock2.start();
    }
}