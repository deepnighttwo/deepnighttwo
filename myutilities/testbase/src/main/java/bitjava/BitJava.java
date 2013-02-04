package bitjava;

public class BitJava {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int a = -1;
		log(~a);
		log(a ^ a);
		log(a << 1);
		log(a >>> 1);
	}
	
	private static void log(Object obj){
		System.out.println(obj);
	}

}
