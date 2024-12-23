package itc;

public class WaitNotifyExample {

    public static void main(String[] args) {

        SharedQueue sharedQueue = new SharedQueue();

        Thread producer = new Thread(() -> {
           try {
               for (int i = 1; i <= 5; i++){
                   System.out.println("In producer thread. i = " + i);
                   sharedQueue.produce(i);
//                   Thread.sleep(500);
               }
           } catch (InterruptedException e) {
               System.out.println("OOOps exception");
               throw new RuntimeException(e);
           }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("In consumer thread. i = " + i);
                    sharedQueue.consume();
//                    Thread.sleep(1000);
                }
            } catch (InterruptedException e){
                System.out.println("ooops exception");
                throw new RuntimeException(e);
            }
        });

        producer.start();
        consumer.start();
    }
}
