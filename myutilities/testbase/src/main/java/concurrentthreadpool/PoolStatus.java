package concurrentthreadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PoolStatus {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		final ThreadPoolExecutor exe = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(3);
		Runnable r = new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("aaaaaaaa");
					Thread.sleep(1000);
					System.out.println("bbbbbbbbb");

				} catch (InterruptedException e) {
					// e.printStackTrace();
				}
			}

		};
		exe.submit(r);
		exe.submit(r);
		exe.submit(r);
		System.out
				.println(exe.awaitTermination(900000L, TimeUnit.SECONDS));
		System.out.println(exe.isShutdown());
		System.out.println(exe.isTerminated());

	}
}
