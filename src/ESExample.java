package chap28_concurrent;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ESExample {

	public static void main(String[] args) {
		
		ESExample obj = new ESExample();
		obj.example2();

	}
	
	public void example1() {
		
		ExecutorService service = Executors.newFixedThreadPool(4);

		Runnable task = () -> {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
//				e.printStackTrace();
				System.out.println("Interrupted ny another thread");
			}
			System.out.println("Task completed");
		};

		service.execute(task);
		List<Runnable> list = service.shutdownNow();
		System.out.println(list.isEmpty());
		System.out.println("Main thread completed");
		
	}

	public void example2() {
		ExecutorService executor = Executors.newFixedThreadPool(5);

		// 1. Submitting a Runnable task:
		Runnable task1 = () -> {
			sleep(1000);
			System.out.println("Task 1 executed.");
		};
		Future<?> future1 = executor.submit(task1);

		// 2. Submitting a Callable task:
		Callable<String> task2 = () -> {
			System.out.println("Performing task 2");
			return "Task 2 completed.";
		};
		Future<String> future2 = executor.submit(task2);

		// 3. Submitting a Runnable task with a result:
		Runnable task3 = () -> {
			System.out.println("Task 3 executed.");
		};
		Future<Integer> future3 = executor.submit(task3, 42);

		// Retrieve results:
		try {
			
//			System.out.println("Future 2 is done " + future2.isDone());
			
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
	
	public void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
