package itc.revise;

import java.util.ArrayList;
import java.util.List;

public class ProdConWaitNotify {

    List<Integer> list = new ArrayList<>();
    public final int SIZE = 10;

    public synchronized void produce(int i) throws InterruptedException {

        if (list.size() == SIZE){
            System.out.println("List is full, waiting..");
            wait();
        }

        System.out.println("Produce " + i);
        list.add(i);
        notify();
    }

    public synchronized void consume() throws InterruptedException {

        if (list.isEmpty()){
            System.out.println("List it empty, waiting ..... ");
            wait();
        }

        System.out.println("Consume " + list.removeLast());
        notify();
    }

    public static void main(String[] args) {

        ProdConWaitNotify obj = new ProdConWaitNotify();
        Runnable producer = () -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    obj.produce(i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Runnable consume = () -> {
            for (int i = 0; i < 5; i++) {
                try {
                    obj.consume();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        new Thread(consume).start();
        new Thread(producer).start();
    }
}
