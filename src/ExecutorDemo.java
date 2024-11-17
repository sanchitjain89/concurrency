package chap28_concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {

	public static void main(String[] args) {
		
		CountDownLatch cdl = new CountDownLatch(5);
		CountDownLatch cdl2 = new CountDownLatch(5);
		CountDownLatch cdl3 = new CountDownLatch(5);
		CountDownLatch cdl4 = new CountDownLatch(5);
		
		ExecutorService es = Executors.newFixedThreadPool(2);
		
		es.execute(new MyExecutorThread(cdl, "A"));
		es.execute(new MyExecutorThread(cdl2, "B"));
		es.execute(new MyExecutorThread(cdl3, "C"));
		es.execute(new MyExecutorThread(cdl4, "D"));
		
		try {
			cdl.await();
			cdl2.await();
			cdl3.await();
			cdl4.await();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		
		es.shutdown();
		System.out.println("Done");
	}
}


class MyExecutorThread implements Runnable{
	
	String name; 
	CountDownLatch latch;
	
	public MyExecutorThread(CountDownLatch c, String n) {
		latch = c;
		name = n;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println(name + ": " + i);
			latch.countDown();
		}
		
	}
}