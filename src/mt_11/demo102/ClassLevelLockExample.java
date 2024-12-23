package mt_11.demo102;

class ClassLevelLockExample {

    public static synchronized void greeting() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " - Acquired CLL and entered static synchronized method");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {}

        System.out.println(threadName + " - Exiting static synchronized method and releasing CLL");
    }
}
