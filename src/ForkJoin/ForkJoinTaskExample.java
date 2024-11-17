package chap28_concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTaskExample {

	public static void main(String[] args) {

		ForkJoinPool commonPool = ForkJoinPool.commonPool();

		System.out.println("Parallelism level: " + commonPool.getParallelism());
		System.out.println("Pool size: " + commonPool.getPoolSize());
		System.out.println("Active thread count: " + commonPool.getActiveThreadCount());
		MyTask mytask = new MyTask(3);
		System.out.println("Is my task complete " + mytask.isDone());
		commonPool.invoke(mytask);
		System.out.println("Is my task cancelled " + mytask.isCancelled());
		System.out.println("Is my task complete " + mytask.isDone());
		mytask.cancel(true);
	}
}

class MyTask extends RecursiveTask<Integer>{
	
	int run;
	
	public MyTask(int run) {
		System.out.println("Run: " + run);
		this.run = run;
	}

	@Override
	protected Integer compute() {
		if (run == 0 || run == 18) return 0;
		
		MyTask one = new MyTask(run-1);
		sleepThread(100);
		
		MyTask two = new MyTask(run+1);
		sleepThread(100);

		System.out.println("The result of fork is " );
		
		ForkJoinTask<Integer> f = two.fork();
		
		f.join();
		f.complete(5);
	
		
		return run;
	}
	
	public void sleepThread(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

