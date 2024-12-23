package mt_11.demo101;

public class CLLock {

    public static void main(String[] args) {
        Counter counter = new Counter();

        // Create multiple threads to access the counter
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                counter.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                counter.increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final count:" + counter.getCount());
    }
}


