package perfmonitoringusinginstrument;

public class TimeConsumeDummy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TimeConsumeDummy dummy = new TimeConsumeDummy();
		dummy.timeConsume();
	}

	public void timeConsume() {
		long t = (long) (100 * Math.random());
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
