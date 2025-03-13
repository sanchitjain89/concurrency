package volatiledemo;


public class VolatileExample {

    private boolean running = true; // Volatile variable

    public void startThread() {
        Thread thread = new Thread(() -> {
            while (running) {
                // Perform some work
                System.out.println("Thread is running...");
                try {
                    Thread.sleep(500); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Thread stopped.");
        });
        thread.start();
    }

    public void stopThread() {
        running = false; // Change the volatile variable
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileExample example = new VolatileExample();
        example.startThread();

        // Let the thread run for a while
        Thread.sleep(3000);

        // Stop the thread
        example.stopThread();

        //Let the main thread wait for the other thread to stop.
        Thread.sleep(1000);
        System.out.println("Main thread finished");
    }
}