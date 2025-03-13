package syncobjects;

import java.util.concurrent.CountDownLatch;

public class CDLDemo {

    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(5);

        new Thread(new MyThread(latch)).start();

        System.out.println("Starting Main thread ");

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Done");
    }

}

class MyThread implements Runnable{

    CountDownLatch latch;

    public MyThread(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run(){
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            latch.countDown();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
