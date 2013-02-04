package testvolatile;

/**
 * @author mzang
 *
 */
public class TestVolatile implements Runnable{
	
	public static volatile int counter = 0;
	
	public static void main(String... args){
		startThread();
		startThread();
		
	}
	
	public static void startThread(){
		Thread thread = new Thread(new TestVolatile());
		thread.start();
	}

	@Override
	public void run() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i = 0;i<10000;i++){
			counter++;
		}
		System.out.println(counter);
	}

}
