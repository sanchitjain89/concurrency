package chap28_concurrent;

import java.util.concurrent.CountDownLatch;

public class CDLDemo {

	public static void main(String[] args) {
		CountDownLatch cdl = new CountDownLatch(2);
		
		System.out.println("Start");
		
		new Thread(new MyThread(cdl)).start();
		
		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Done");
	}
}

class MyThread implements Runnable{
	
	CountDownLatch latch;
	
	public MyThread(CountDownLatch c) {
		latch = c;
	}

	@Override
	public void run() {
		
		for (int i = 1; i <=5; i++) {
			try {
				Thread.sleep(10);
				System.out.println(i);
				latch.countDown();
			} catch (InterruptedException e) {}
			
		}
	}
}
