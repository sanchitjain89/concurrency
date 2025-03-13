package itc.ex;

class SharedBuffer {
    private int data = 0; // Shared resource
    private boolean hasData = false; // Indicates if the buffer is full or empty

    // Producer method
    public synchronized void produce(int value) throws InterruptedException {
        while (hasData) {
            wait(); // Wait if the buffer already has data
        }
        data = value;
        hasData = true;
        System.out.println("Produced: " + data);
        notify(); // Notify the consumer
    }

    // Consumer method
    public synchronized int consume() throws InterruptedException {
        while (!hasData) {
            wait(); // Wait if the buffer is empty
        }
        hasData = false;
        System.out.println("Consumed: " + data);
        notify(); // Notify the producer
        return data;
    }
}
