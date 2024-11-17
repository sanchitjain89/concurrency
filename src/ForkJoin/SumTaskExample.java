package chap28_concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class SumTask extends RecursiveTask<Integer> {
    private final int[] array;
    private final int start;
    private final int end;
    private static final int THRESHOLD = 10000;

    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            // Direct computation for small tasks
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            // Split into subtasks for large tasks
            int mid = (start + end) / 2;
            SumTask left = new SumTask(array, start, mid);
            SumTask right = new SumTask(array, mid, end);
            
            left.fork();  // Submit first subtask
            int rightResult=0;
			right.fork();
            int leftResult = left.join();  // Wait for first subtask
            
            return leftResult + right.join();
        }
    }
}

public class SumTaskExample {
    public static void main(String[] args) {
        // Create sample array
        int[] array = new int[10000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        
        long startTime = System.currentTimeMillis();
        
        // Method 1: Using common pool
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        SumTask task = new SumTask(array, 0, array.length);
        int result = commonPool.invoke(task);
        System.out.println("Sum is: " + result);
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("Time taken: " + (endTime - startTime) + "ms\n");

        // Method 2: Using new pool
        ForkJoinPool customPool = new ForkJoinPool(1); // 4 threads
        startTime = System.currentTimeMillis();
        try {
            int result2 = customPool.invoke(new SumTask(array, 0, array.length));
            System.out.println("Sum is: " + result2);
        } finally {
            customPool.shutdown(); // Always shutdown custom pools
        }
        
        endTime = System.currentTimeMillis();
        
        System.out.println("Time taken: " + (endTime - startTime) + "ms\n");
    }
}