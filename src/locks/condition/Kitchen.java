package locks.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Kitchen {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private String food = null;

    public void cook(String dish) throws InterruptedException {
        lock.lock();
        try {
            while (food != null){
                System.out.println("Chef waiting... Previous dish still on counter.");
                notEmpty.await();
            }
            food = dish;
            System.out.println("Chef prepared: " + dish);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void serve() throws InterruptedException {
        lock.lock();
        try{
            while (food == null){
                System.out.println("Waiter waiting... No food to serve.");
                notEmpty.await();
            }
            System.out.println("Waiter served: " + food);
            food = null;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
}
