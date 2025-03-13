package locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Thread t1 = new Thread(() -> resource.accessResource("Thread-1"));
        Thread t2 = new Thread(() -> resource.accessResourceWithTimeout("Thread-2"));

        t1.start();
        t2.start();
    }

    static class SharedResource {
        private final Lock lock = new ReentrantLock();

        public void accessResource(String threadName) {
            if (lock.tryLock()) { // Try to acquire the lock without waiting
                try {
                    System.out.println(threadName + " acquired the lock.");
                    // Simulate some work with the shared resource
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(threadName + " was interrupted.");
                } finally {
                    System.out.println(threadName + " is releasing the lock.");
                    lock.unlock();
                }
            } else {
                System.out.println(threadName + " could not acquire the lock and is moving on.");
            }
        }

        public void accessResourceWithTimeout(String threadName) {
            try {
                if (lock.tryLock(500, java.util.concurrent.TimeUnit.MILLISECONDS)) { // Try to acquire lock with timeout
                    try {
                        System.out.println(threadName + " acquired the lock.");
                        // Simulate some work with the shared resource
                        Thread.sleep(1000);
                    } finally {
                        System.out.println(threadName + " is releasing the lock.");
                        lock.unlock();
                    }
                } else {
                    System.out.println(threadName + " could not acquire the lock within the timeout.");
                }
            } catch (InterruptedException e) {
                System.out.println(threadName + " was interrupted while waiting for the lock.");
            }
        }
    }
}
