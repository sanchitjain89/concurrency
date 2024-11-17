package callables;

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class Callable1 {
    public static void main(String[] args) {
        // Create a thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        // Single Callable example
        Callable<Integer> calculateTask = () -> {
            // Simulate some computation
            Thread.sleep(2000);
            return 42;
        };

        System.out.println("Submitting single calculation task...");
        Future<Integer> future = executor.submit(calculateTask);
        
        try {
            // Get the result with a timeout of 3 seconds
            Integer result = future.get(3, TimeUnit.SECONDS);
            System.out.println("Calculation result: " + result);
        } catch (TimeoutException e) {
            System.out.println("Task took too long!");
            future.cancel(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Multiple Callable tasks example
        List<Callable<String>> tasks = new ArrayList<>();
        
        // Create three tasks
        tasks.add(() -> {
            Thread.sleep(1000);
            return "Task 1 completed";
        });
        
        tasks.add(() -> {
            Thread.sleep(2500);
            return "Task 2 completed";
        });
        
        tasks.add(() -> {
            Thread.sleep(3000);
            return "Task 3 completed";
        });

        try {
            System.out.println("\nSubmitting multiple tasks...");
            // Submit all tasks and get Future objects
            List<Future<String>> futures = executor.invokeAll(tasks);

            // Process the results as they complete
            for (Future<String> f : futures) {
                String result = f.get(2, TimeUnit.SECONDS); // Blocks until the result is available
                System.out.println(result);
            }
        }catch (TimeoutException e) {
            System.out.println("Task took too long!");
            future.cancel(true);
        }  
        catch (Exception e) {
            e.printStackTrace();
        }

        // Shutdown the executor
        executor.shutdown();
    }
}