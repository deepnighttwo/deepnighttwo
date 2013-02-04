package test;

public class VolatileTest {

	public int member = -100;

	public volatile int volatileMember = -100;

	public static void main(String[] args) {
		int testloop = 10;
		for (int i = 1; i <= testloop; i++) {
			System.out.println("Round:" + i);
			VolatileTest vt = new VolatileTest();
			vt.runTest();
			System.out.println();
		}
	}

	public void runTest() {
		int local = -100;

		int loop = 1;
		int loop2 = Integer.MAX_VALUE;
		long startTime;

		startTime = System.currentTimeMillis();
		for (int i = 0; i < loop; i++) {
			for (int j = 0; j < loop2; j++) {
			}
			for (int j = 0; j < loop2; j++) {
			}
		}
		System.out.println("Empty:" + (System.currentTimeMillis() - startTime));

		startTime = System.currentTimeMillis();
		for (int i = 0; i < loop; i++) {
			for (int j = 0; j < loop2; j++) {
				local++;
			}
			for (int j = 0; j < loop2; j++) {
				local--;
			}
		}
		System.out.println("Local:" + (System.currentTimeMillis() - startTime));

		startTime = System.currentTimeMillis();
		for (int i = 0; i < loop; i++) {
			for (int j = 0; j < loop2; j++) {
				member++;
			}
			for (int j = 0; j < loop2; j++) {
				member--;
			}
		}
		System.out.println("Member:" + (System.currentTimeMillis() - startTime));

		startTime = System.currentTimeMillis();
		for (int i = 0; i < loop; i++) {
			for (int j = 0; j < loop2; j++) {
				volatileMember++;
			}
			for (int j = 0; j < loop2; j++) {
				volatileMember--;
			}
		}
		System.out.println("VMember:" + (System.currentTimeMillis() - startTime));

	}
}
