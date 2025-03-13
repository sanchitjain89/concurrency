package itc;

import java.util.LinkedList;
import java.util.Queue;

public class SharedQueue {

    private final Queue<Integer> queue = new LinkedList<>();
    private final int CAPACITY = 5;

    public synchronized void produce (int item) throws InterruptedException {
        System.out.println(" Inside producer synchronized method ");
        while (queue.size() == CAPACITY){
            System.out.println("Queue is full, producer is waiting");
            wait();
        }

        queue.add(item);
        System.out.println("Produced " + item);
        notify();
    }

    public synchronized void consume() throws InterruptedException {
        System.out.println(" Inside consumer synchronized method ");
        while (queue.isEmpty()){
            System.out.println("Queue is empty. Consumer is waiting");
            wait();
        }

        int item = queue.poll();
        System.out.println("Consumed " + item);
        notify();
    }

}
