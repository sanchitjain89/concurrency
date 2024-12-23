package mt_11.demo102;

public class Main {
    public static void main(String[] args) {

        // Thread 1
        Thread thread1 = new Thread(new MyRunnable());
        // Thread 2
        Thread thread2 = new Thread(new MyRunnable());

        thread1.start();
        thread2.start();
    }
}
