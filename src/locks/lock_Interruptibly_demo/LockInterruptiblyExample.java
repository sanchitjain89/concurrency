package locks.lock_Interruptibly_demo;

public class LockInterruptiblyExample {

    public static void main(String[] args) throws InterruptedException {

        Task task = new Task();

        Thread t1 = new Thread(task, "T-1");
        Thread t2 = new Thread(task, "T-2");

        t1.start();
//        Thread.sleep(100);
        t2.start();
        Thread.sleep(1000);
        t2.interrupt();

        t1.join();
        t2.join();

    }
}

