package atomic;

import java.util.concurrent.atomic.AtomicInteger;

class AtomicDemo {
    public static void main(String[] args) {
        new Thread(new AtomThread("A")).start();
        new Thread(new AtomThread("B")).start();
        new Thread(new AtomThread("C")).start();
    }

    static class Shared {
        static AtomicInteger ai = new AtomicInteger(0);
    }

    // A thread of execution that increments count.
    static class AtomThread implements Runnable {
        String name;

        AtomThread(String n) {
            name = n;
        }

        @Override
        public void run() {
            System.out.println("Starting " + name);

            for (int i = 1; i <= 3; i++) {
                System.out.println(name + " iteration " + i + " got: " + Shared.ai.getAndAdd(1));
            }

            System.out.println(name + " " + Shared.ai.get());
        }
    }
}