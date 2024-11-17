package executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor_1 {

    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(5);

        List<Runnable> tasks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Runnable task = () -> {
                System.out.println("Task " + finalI);
            };

            tasks.add(task);
        }

        for (Runnable t: tasks){
            executor.execute(t);
        }

        //shutting down the task is important, otherwise the program won't close.
        if (executor instanceof ExecutorService){
            System.out.println("YO");
            ((ExecutorService) executor).shutdown();
        }
    }
}
