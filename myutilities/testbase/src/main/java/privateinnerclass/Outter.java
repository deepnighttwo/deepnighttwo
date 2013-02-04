package privateinnerclass;

public class Outter {

	@SuppressWarnings("unused")
	private static class Inner {

		private Inner() {

		}

		private void printsth() {
			System.out.println("This is a private instance method in a "
					+ "private static inner class which have a private constructor.");
		}
	}

}
