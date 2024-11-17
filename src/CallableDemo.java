package chap28_concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableDemo {
	
	public static void main(String[] args) {
		
		ExecutorService es = Executors.newFixedThreadPool(1);
		System.out.println("Starting the program");
		Future<Integer> f1 = es.submit(new Sum(10));
		Future<Double> f2 = es.submit(new Hypot(3,4));
		Future<Integer> f3 = es.submit(new Fact(10));
		
		try {
			System.out.println(f1.get());
			System.out.println(f2.get());
			System.out.println(f3.get());
		} catch (InterruptedException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		es.shutdown();
		System.out.println("Done");
	}
}

class Sum implements Callable<Integer>{
	int stop;
	
	public Sum(int s) {
		stop = s;
	}

	public Integer call() {
		int sum = 0;
		
		for (int i = 0; i < stop; i++) {
			sum +=i;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return sum;
	}
}

class Hypot implements Callable<Double>{
	double s1, s2;
	
	public Hypot(double side1, double side2) {
		s1 = side1;
		s2 = side2;
	}
	
	public Double call(){
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Math.sqrt(s1*s1 + s2*s2);
	}
}

class Fact implements Callable<Integer>{
	int stop;
	
	public Fact(int s) {
		stop = s;
	}
	
	public Integer call(){
		int fact = 1;
		
		for (int i = 2; i <= stop; i++) {
			fact *=i;
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return fact;
	}
}
