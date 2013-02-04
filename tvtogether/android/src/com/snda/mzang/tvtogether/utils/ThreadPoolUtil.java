package com.snda.mzang.tvtogether.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolUtil {

	private static ExecutorService executor = Executors.newCachedThreadPool();

	public static Future<?> execute(Runnable runnable) {
		Future<?> future = executor.submit(runnable);
		return future;
	}

	public static void shutDown() {
		executor.shutdown();
	}

	public static void executeInThread(Runnable runnable) {
		new Thread(runnable).start();
	}

}
