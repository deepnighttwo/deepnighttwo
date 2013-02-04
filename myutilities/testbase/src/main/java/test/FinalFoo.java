package test;

import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Mark Zang
 * 
 */

public class FinalFoo {

	public static String str = "asdfadf";
	
	static{
		str = "asfdasdfasdf";
	}

	public static void main(String[] asdf) throws NoSuchAlgorithmException {

		System.out.println(System.currentTimeMillis() / 1000);

		String r = "\\$\\{AAA\\}";
		String s = "asdfasdf" + r + "asdfasdf";
		String s2 = "${AAA}asdfasdf${AAA}asdfasdf";

		String[] rrr = s.split(r);
		String[] rrrasdf = s2.split(r);

		System.out.println("d033e22ae348aeb5660fc2140aec35850c4da997".length());
		System.out.println("d033e22ae348aeb5660fc2140aec35850c4da997");

		String ser = "\r\n";
		System.out.println((int) '\r');
		System.out.println((int) '\n');

		System.out.println(getSHAPassword("admin"));
		System.out.println(getSHAPassword("admasdfasdfin"));
		System.out.println(getSHAPassword("a"));

		// for (int i = 0; i < 999999999; i++) {
		// float a = 0.999f;
		// byte b = (byte) a;
		// b++;
		// a = (float) b;
		// if (b == 1) {
		// System.out.println((float) b);
		// }
		// System.out.println((float) b);
		//
		// }
		// try {
		// System.out.print("Finally.asdfasdf");
		// System.exit(0);
		// } finally {
		// System.out.print("Finally.");
		// }
	}

	/**
	 * Get the SHA value for given password. We must make sure the String
	 * instance is using the same encoding.
	 * 
	 * @param originalPassword
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSHAPassword(String originalPassword)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA");
		md.update(originalPassword.getBytes());
		byte[] bytes = md.digest();
		String encryption = bytes2Hex(bytes);
		return encryption;
	}

	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	public void test(Object a, Object b) {
		synchronized (a) {
			synchronized (b) {
				new Object() {
					public String toString() {
						return "";
					}
				};
			}
		}
	}

	public void testttt(List<? extends FileInputStream> asdf) {
		asdf.add(null);
	}

}