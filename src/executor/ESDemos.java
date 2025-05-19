package executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ESDemos {

	public static void main(String[] args) {
		ESDemos obj = new ESDemos();
		obj.example3();
	}
	
	public void example2() {
		ExecutorService executor = Executors.newFixedThreadPool(5);

		// 1. Submitting a Runnable task:
		Runnable task1 = () -> {
			sleep(1000);
			System.out.println(Thread.currentThread().getName() + "  task 1 executed.");
		};
		Future<?> future1 = executor.submit(task1);

		// 2. Submitting a Callable task:
		Callable<String> task2 = () -> {
			System.out.println(Thread.currentThread().getName() + "  performing task 2");
			return "Task 2 completed.";
		};
		Future<String> future2 = executor.submit(task2);

		// 3. Submitting a Runnable task with a result:
		Runnable task3 = () -> {
			System.out.println(Thread.currentThread().getName() + "  task 3 executed.");
		};
		Future<Integer> future3 = executor.submit(task3, 42);

		// Retrieve results:
		try {
			
			System.out.println("Future 2 is done " + future2.isDone());
			
			System.out.println("Result from task 1 " + future1.get());
			
			String result2 = future2.get();
			System.out.println("Result from task 2: " + result2);

			Integer result3 = future3.get();
			System.out.println("Result from task 3: " + result3);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		executor.shutdown();
	}

	public void example3() {
		try (ExecutorService executor = Executors.newFixedThreadPool(5)) {

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
		}
	}
	
	public void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			System.out.println("Ooopsss, interrupted!");
		}
	}
}
