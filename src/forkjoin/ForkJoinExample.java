package forkjoin;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinExample {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        SumTask task = new SumTask(numbers, 0, numbers.length);

        int result = pool.invoke(task);
        System.out.println("Total Sum: " + result);
    }

    static class SumTask extends RecursiveTask<Integer> {
        private final int[] numbers;
        private final int start, end;
        private static final int THRESHOLD = 5; // Task size limit for splitting

        SumTask(int[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= THRESHOLD) {
                // Base case: directly compute sum
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += numbers[i];
                }
                return sum;
            } else {
                // Split the task
                int mid = (start + end) / 2;
                SumTask leftTask = new SumTask(numbers, start, mid);
                SumTask rightTask = new SumTask(numbers, mid, end);

                // Fork subtasks
                leftTask.fork();
                rightTask.fork();

                // Join results of subtasks
                return leftTask.join() + rightTask.join();
            }
        }
    }
}
