package locks;

import java.util.concurrent.locks.ReentrantLock;

public class RL_101 {
    public static void main(String[] args) throws InterruptedException {
        SharedResource resource = new SharedResource();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                resource.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                resource.increment();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final count: " + resource.getCount()); // Output: 2000
    }

    static class SharedResource {
        private int count = 0;
        private final ReentrantLock lock = new ReentrantLock();

        public void increment() {
            lock.lock();  // Acquire lock
            try {
                count++;
            } finally {
                lock.unlock();  // Always release lock in finally
            }
        }

        public int getCount() {
            return count;
        }
    }
}
