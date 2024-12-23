package mt_11;

public class LockDemo {
    private static final Object LOCK_1 = new Object();
    private static final Object LOCK_2 = new Object();

    public void demonstrateLocking() {
        Thread t1 = new Thread(() -> {
            synchronized(LOCK_1) {
                System.out.println("Thread 1: Holding lock 1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}

                synchronized(LOCK_2) {
                    System.out.println("Thread 1: Holding lock 1 & 2");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized(LOCK_2) {
                System.out.println("Thread 2: Holding lock 2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}

                synchronized(LOCK_1) {
                    System.out.println("Thread 2: Holding lock 2 & 1");
                }
            }
        });

        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        LockDemo demo = new LockDemo();

        demo.demonstrateLocking();
    }
}