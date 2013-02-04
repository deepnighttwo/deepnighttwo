package singalinstance;

public class SingalInstance {
	
	static{
		System.out.println("Initialing class SingalInstance...");
	}
	
	private SingalInstance(){
		System.out.println("Instance Created.");
	}

	private static class Inner {
		static{
			System.out.println("Initialing Inner Class...");
		}
		static SingalInstance s = new SingalInstance();
	}

	public static SingalInstance getInstance() {
		return Inner.s;
	}
	
	public static void test(){
		System.out.println("Called!");
	}
}
