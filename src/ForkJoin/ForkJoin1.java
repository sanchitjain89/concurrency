package chap28_concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.RecursiveAction;
import java.util.Arrays;

public class ForkJoin1 {

    // Example 1: RecursiveTask for computing array sum
    static class ArraySumTask extends RecursiveTask<Long> {
        private final int[] array;
        private final int start;
        private final int end;
        private static final int THRESHOLD = 1000; // Threshold for splitting

        public ArraySumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            int length = end - start;
            if (length <= THRESHOLD) {
                // Direct computation for small tasks
                return computeDirectly();
            }

            // Split the task into two subtasks
            int mid = start + length / 2;
            ArraySumTask leftTask = new ArraySumTask(array, start, mid);
            ArraySumTask rightTask = new ArraySumTask(array, mid, end);

            // Fork right task
            rightTask.fork();

            // Directly compute left task
            Long leftResult = leftTask.compute();
            
            // Join right task
            Long rightResult = rightTask.join();

            // Combine results
            return leftResult + rightResult;
        }

        private long computeDirectly() {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        }
    }

    // Example 2: RecursiveAction for array transformation
    static class ArrayTransformTask extends RecursiveAction {
        private final int[] array;
        private final int start;
        private final int end;
        private static final int THRESHOLD = 1000;

        public ArrayTransformTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= THRESHOLD) {
                computeDirectly();
                return;
            }

            int mid = start + (end - start) / 2;
            ArrayTransformTask leftTask = new ArrayTransformTask(array, start, mid);
            ArrayTransformTask rightTask = new ArrayTransformTask(array, mid, end);

            invokeAll(leftTask, rightTask);
        }

        private void computeDirectly() {
            for (int i = start; i < end; i++) {
                // Example transformation: square each element
                array[i] = array[i] * array[i];
            }
        }
    }

    public static void main(String[] args) {
        // Create a large array for testing
        int[] numbers = new int[100_000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        // Example 1: Using RecursiveTask to sum array
        ForkJoinPool pool = ForkJoinPool.commonPool();
        ArraySumTask sumTask = new ArraySumTask(numbers, 0, numbers.length);
        long sum = pool.invoke(sumTask);
        System.out.println("Sum of array: " + sum);

        // Example 2: Using RecursiveAction to transform array
        int[] numbers2 = Arrays.copyOf(numbers, numbers.length);
        ArrayTransformTask transformTask = new ArrayTransformTask(numbers2, 0, numbers2.length);
        pool.invoke(transformTask);
        System.out.println("First 5 elements after transformation: " + 
            Arrays.toString(Arrays.copyOf(numbers2, 5)));

        // Example 3: Using parallel streams (built on Fork/Join)
        long streamSum = Arrays.stream(numbers)
                              .parallel()
                              .sum();
        System.out.println("Sum using parallel stream: " + streamSum);
    }
}