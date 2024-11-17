package callables;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Callable3 {

    static class SumTask implements Callable<Integer>{

        List<Integer> list;

        public SumTask(List<Integer> list) {
            this.list = list;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println("Current thread " + Thread.currentThread().getName());

            return list.stream().mapToInt(Integer::intValue).sum();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<SumTask> tasks = Arrays.asList(
          new SumTask(Arrays.asList(3,6,7,8)),
          new SumTask(Arrays.asList(13,16,17,18)),
          new SumTask(Arrays.asList(30,60,70,80))
        );

        try {
            List<Future<Integer>> results = executorService.invokeAll(tasks);

            for (Future f : results){
                System.out.println("The result of task is " + f.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        executorService.shutdown();
    }
}
