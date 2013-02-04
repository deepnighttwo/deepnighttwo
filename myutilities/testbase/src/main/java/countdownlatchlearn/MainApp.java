package countdownlatchlearn;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApp {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(5);
		final CountDownLatch lock = new CountDownLatch(5);

		for (int i = 0; i < 5; i++) {
			es.submit(new Runnable() {

				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName()
							+ " executing");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()
							+ " executed");
					lock.countDown();
					System.out.println(Thread.currentThread().getName()
							+ " countdowned");
				}
			});

		}

		Thread.sleep(5000);

		Thread th = new Thread() {
			public void run() {
				System.out.println("Thread waiting for the count down latch");
				try {
					lock.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out
						.println("Thread waiting for the count down latch executed!");
			}
		};
		th.start();
	}

}
