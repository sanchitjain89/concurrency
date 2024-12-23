package mt_11.demo102;

class MyRunnable implements Runnable {

    int i = 0;

    @Override
    public void run() {
        while (true) {
            // Log before trying to call the synchronized method
            System.out.println(Thread.currentThread().getName()
                    + " - Attempting to acquire CLL; in run method");

            // Call the synchronized static method
            ClassLevelLockExample.greeting();

            // Log after coming out of the synchronized method
            System.out.println(Thread.currentThread().getName() + " - After synchronized method call, in run method");

            if (i > 4) break;

            // Waiting message
            System.out.println(Thread.currentThread().getName() + " - Waiting before next iteration...");

            try {
                Thread.sleep(1000); // Wait before retrying
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Value of i "
                    + Thread.currentThread().getName()
                    + " -- " + (++i));
        }
    }
}
