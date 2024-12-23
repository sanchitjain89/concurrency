package forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinMethodDemo {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int[] numbers = createArray(1000);

        // Demo different methods
        System.out.println("Demonstrating different ForkJoin methods:");
        
        // 1. Using invoke()
        demoInvoke(pool, numbers);
        
        // 2. Using fork() and join()
        demoForkJoin(pool, numbers);
        
        // 3. Using fork() with multiple tasks
        demoMultipleForkJoin(pool, numbers);
        
        pool.shutdown();
    }

    // Simple task that sums an array of numbers
    static class SumTask extends RecursiveTask<Integer> {
        private final int[] array;
        private final int start;
        private final int end;
        private final int threshold = 100;
        private final String taskName;

        public SumTask(int[] array, int start, int end, String taskName) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.taskName = taskName;
        }

        @Override
        protected Integer compute() {
            if (end - start <= threshold) {
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                System.out.println(taskName + ": Computed sum from index " + start + " to " + (end-1));
                return sum;
            }

            int mid = (start + end) / 2;
            System.out.println(taskName + ": Splitting task at " + mid);
            
            SumTask leftTask = new SumTask(array, start, mid, taskName + "-Left");
            SumTask rightTask = new SumTask(array, mid, end, taskName + "-Right");

            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();

            return leftResult + rightResult;
        }
    }

    // 1. Demonstrate invoke()
    private static void demoInvoke(ForkJoinPool pool, int[] numbers) {
        System.out.println("\n=== Invoke Demo ===");
        System.out.println("invoke(): Submits and executes task, waiting for the result");
        
        long startTime = System.currentTimeMillis();
        int result = pool.invoke(new SumTask(numbers, 0, numbers.length, "InvokeTask"));
        long endTime = System.currentTimeMillis();
        
        System.out.println("Result: " + result);
        System.out.println("Time taken: " + (endTime - startTime) + "ms\n");
    }

    // 2. Demonstrate fork() and join()
    private static void demoForkJoin(ForkJoinPool pool, int[] numbers) {
        System.out.println("\n=== Fork and Join Demo ===");
        System.out.println("fork(): Submits task asynchronously");
        System.out.println("join(): Waits for the result");
        
        long startTime = System.currentTimeMillis();
        
        SumTask task = new SumTask(numbers, 0, numbers.length, "ForkJoinTask");
        task.fork();  // Asynchronous execution
        int result = task.join();  // Wait for result
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("Result: " + result);
        System.out.println("Time taken: " + (endTime - startTime) + "ms\n");
    }

    // 3. Demonstrate multiple fork() operations
    private static void demoMultipleForkJoin(ForkJoinPool pool, int[] numbers) {
        System.out.println("\n=== Multiple Fork-Join Demo ===");
        System.out.println("Demonstrating parallel execution with multiple forks");
        
        long startTime = System.currentTimeMillis();
        
        // Split array into four parts
        int quarter = numbers.length / 4;
        
        SumTask task1 = new SumTask(numbers, 0, quarter, "Task1");
        SumTask task2 = new SumTask(numbers, quarter, quarter*2, "Task2");
        SumTask task3 = new SumTask(numbers, quarter*2, quarter*3, "Task3");
        SumTask task4 = new SumTask(numbers, quarter*3, numbers.length, "Task4");
        
        // Fork first three tasks
        task1.fork();
        task2.fork();
        task3.fork();
        
        // Compute the fourth task directly
        int result4 = task4.compute();
        
        // Join the results
        int result = task1.join() + task2.join() + task3.join() + result4;
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("Result: " + result);
        System.out.println("Time taken: " + (endTime - startTime) + "ms\n");
    }

    private static int[] createArray(int size) {
        int[] numbers = new int[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = i + 1;
        }
        return numbers;
    }
}