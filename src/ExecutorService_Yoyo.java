import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorService_Yoyo {

    Runnable task = () -> System.out.println("This is my task");

    Runnable taskWithWait = () -> {
        sleepThread(1500);
        System.out.println("Thread Name in taskWithWait " +  Thread.currentThread().getName());
        System.out.println("Task with wait completed");
    };

    Callable<Integer> callableTask = () -> {
        System.out.println("Thread Name in callable task " +  Thread.currentThread().getName());
        int sum = 0;

        for (int i = 0; i < 10; i++) {
            sum+=i;
        }
        sleepThread(100);
        System.out.println("Callable task is complete");
        return sum;
    };

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        ExecutorService_Yoyo obj = new ExecutorService_Yoyo();
//        obj.example2(executorService);
//        obj.example3_invokeAll(executorService);
        obj.example4_invokeAny(executorService);

        executorService.shutdown();
    }

    public void example1_Submit(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Future<?> future = executorService.submit(taskWithWait);

        System.out.println("Thread name in main " +  Thread.currentThread().getName());
        System.out.println(future.isDone());

        executorService.shutdown();
    }

    public void example2_Submit(ExecutorService executorService){
        Future<Integer> future1 = executorService.submit(callableTask);
        Future<?> future2 = executorService.submit(taskWithWait);

        System.out.println("Future 1 complete? " + future1.isDone());
        System.out.println("Future 2 complete? " + future2.isDone());

        try {
            System.out.println("Future value of callable task " + future1.get());
            System.out.println("Future value of taskWithWait " + future2.get());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("There is an exception in the running of thread");
        }

        System.out.println("Future 1 complete? " + future1.isDone());
        System.out.println("Future 2 complete? " + future2.isDone());
    }

    public void example3_invokeAll(ExecutorService executorService){

        List<Callable<String>> tasks = getListOfCallableTasks();

        try {
            List<Future<String>> future = executorService.invokeAll(tasks);

            for (Future<String> f : future){
                System.out.println(f.isDone());
                System.out.println("Future value " + f.get());
            }

        } catch (InterruptedException | ExecutionException e) {
        }
    }

    public void example4_invokeAny(ExecutorService executorService){
        List<Callable<String>> tasks = getListOfCallableTasks();

        try {
            String result = executorService.invokeAny(tasks);
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Task interrupted!");
        }
    }

    public List<Callable<String>> getListOfCallableTasks(){
        List<Callable<String>> tasks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Callable task = () -> {
                System.out.println("Thread name " + Thread.currentThread().getName());
                sleepThread(1000);
                return "Task " + finalI;
            };
            tasks.add(task);
        }
        return tasks;
    }

    public void sleepThread(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
