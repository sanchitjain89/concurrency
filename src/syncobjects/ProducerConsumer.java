package syncobjects;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ProducerConsumer {
    private static final int BUFFER_SIZE = 5; // Size of the buffer

    private final Queue<Integer> buffer = new LinkedList<>();

    // Semaphores
    private final Semaphore producerSemaphore = new Semaphore(BUFFER_SIZE); // Tracks empty slots
    private final Semaphore consumerSemaphore = new Semaphore(0);           // Tracks filled slots

    public void produce(int item) throws InterruptedException {
        producerSemaphore.acquire(); // Wait for an empty slot

        // Critical section
        try {
            buffer.offer(item);
            System.out.println("Produced: " + item);

        } finally {
            consumerSemaphore.release();  // Signal that a new item is available
        }
    }

    public void consume() throws InterruptedException {
        consumerSemaphore.acquire(); // Wait for a filled slot

        // Critical section
        try {
            int item = buffer.poll();
            System.out.println("Consumed: " + item);

        } finally {
            producerSemaphore.release(); // Signal that a slot is now empty
        }
    }

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();

        // Producer thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    pc.produce(i);
                    Thread.sleep(500); // Simulate production time
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Consumer thread
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    pc.consume();
                    Thread.sleep(800); // Simulate consumption time
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}

