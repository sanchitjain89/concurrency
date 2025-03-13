package syncobjects;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SemaphoreDemo {
    // Limit to 10 simultaneous connections
    private final Semaphore semaphore = new Semaphore(10);

    public void connect() {
        try {
            semaphore.acquire(); // Acquire a connection permit

            // Simulate database connection
            System.out.println(Thread.currentThread().getName() + " connected to database");
            Thread.sleep(2000); // Simulate connection time
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(); // Release the connection
        }
    }

    public static void main(String[] args) {
        SemaphoreDemo pool = new SemaphoreDemo();
        ExecutorService executor = Executors.newFixedThreadPool(15);

        for (int i = 0; i < 15; i++) {
            executor.submit(pool::connect);
        }

        executor.shutdown();
    }
}