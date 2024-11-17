package locks101;

import java.util.concurrent.locks.ReentrantLock;

public class Locks101 {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        // Creating multiple threads that try to increment the counter
        Thread t1 = new Thread(() -> counter.increment());
        Thread t2 = new Thread(counter::increment);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final count: " + counter.getCount());
    }

    // Synchronization tools for shared access to prevent race condition.
    static class Counter {
        private int count = 0;
        private final ReentrantLock lock = new ReentrantLock();

        public void increment() {
            System.out.println(Thread.currentThread().getName() + " trying to acquire lock ");

            /*In case of try-lock, if the lock is not available, the thread will proceed. Final outcome of count will be 10.
            In case of lock, thread will wait until it gets the lock*/

            if (lock.tryLock()) {
//            lock.lock();  // Acquiring the lock
                try {
                    for (int i = 0; i < 10; i++) {
                        System.out.println(Thread.currentThread().getName());
                        count++;
                    }
                } finally {
                    lock.unlock();  // Critical, Always release the lock in finally block
                }
            } else {
                System.out.println("Thread could not acquire the lock");
            }
        }

        public int getCount() {
            return count;
        }
    }
}

