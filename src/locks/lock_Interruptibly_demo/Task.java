package locks.lock_Interruptibly_demo;

import java.util.concurrent.locks.ReentrantLock;

class Task implements Runnable {

    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " trying to acquire lock...");
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName() + " acquired lock.");
                Thread.sleep(5000); // Simulating long-running task
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " released lock.");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " was interrupted while waiting for lock.");
        }
    }
}
