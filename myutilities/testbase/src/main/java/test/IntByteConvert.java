package test;

public class IntByteConvert {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		log(Integer.toHexString(150));

		byte b = (byte) 150;
		log(b);

		// 留下int最后一个字节的数据给byte，是多少就是多少。如果最后一个字节最高位是1，那就是负数，不管原来int是多少。
		int i = (int) b;

		log(i);

		log(Integer.toHexString(i));

		/**
		 * 把byte转成int，就是把最高位复制到int剩下的三个字节，所以原来是负几就还是负几，原来是正数就还是正数。
		 * 所以，如果想还原原来int变byte的值
		 * ，就需要和ff（八个字节，一个byte）做算数&，这样高三个字节的1也好，0也好，都会被变成0，数字就肯定变成正数了。
		 * 
		 * int->byte->int的转化就是这样，最后一步& 0xff。这个过程仅仅对大于0，小于256的int整数有效。
		 */
		int i2 = ((int) b) & 0xff;

		log(i2);

	}

	private static void log(byte b) {
		System.out.println(b);
	}

	private static void log(int i) {
		System.out.println(i);
	}

	private static void log(Object obj) {
		System.out.println(obj);
	}

}
