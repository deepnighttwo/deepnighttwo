package test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.TimeZone;

public class AAAAMain {

	public static String a = "asfdasdfasdf";

	public static void main(String[] args) {
		
		System.out.println(System.getProperty("java.class.path"));
		TimeZone tz = TimeZone.getTimeZone("US/Pacific");
		TimeZone.setDefault(tz);
		System.out.println(tz);
		copyArray();
	}

	public static char[] NUM_CHARS = new char[] { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String intToString(int n, int radix) {
		if (radix > 4) {
			return "Not Supported";
		}
		char[] intStr = new char[32];
		int mask = (1 << radix) - 1;

		int pos = 32;
		do {
			pos--;
			int idx = n & mask;
			n >>>= radix;
			intStr[pos] = NUM_CHARS[idx];
		} while (n > 0);
		return new String(intStr, pos, 32 - pos);
	}

	public static void copyArray() {
		int[] arr = new int[] { 1, 2, 3, 4, 5, 6, 7 };
		System.arraycopy(arr, 0, arr, 1, 3);
		System.out.print(arr);
	}

	public static void testOverAdded() {
		log(Integer.toBinaryString(5));
		int t = Integer.MAX_VALUE;
		log(Integer.toBinaryString(t));
		System.out.println(++t);
		log(Integer.toBinaryString(t));

		int c = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < Integer.MAX_VALUE; j++) {
				c++;
			}
			System.out.println(c);
		}
	}

	public static void testCRLF(String[] args) {
		System.out.print("AAA " + (int) '\r');
		System.out.print("AAA " + (int) '\n');
		System.out.print("AAA ");

		log(cmp("asfdasdfasdf", "asfdasdfasdf"));
		log(cmp(a, "asfdasdfasdf"));
		log(cmp(getString(), "asfdasdfasdf"));
		log(cmp(FinalFoo.str, "asfdasdfasdf"));

		if (args.length == 2) {
			log(cmp(args[0], args[1]));
		}
	}

	public static String getString() {
		return "asfdasdfasdf";
	}

	private static String cmp(String str1, String str2) {
		return (str1 == str2) + "\t" + (str1.equals(str2));
	}

	// =============================================================
	public static void log(Object obj) {
		System.out.println(obj);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void testHMFF() {
		HashMap hm = new HashMap();
		hm.put("1", "asdf");
		hm.put("2", "asdf");
		hm.put("3", "asdf");
		hm.put("4", "asdf");
		hm.put("5", "asdf");

		for (Object obj : hm.keySet()) {
			System.out.println(obj);
			hm.put("6", "asdf");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void testHTFF() {
		Hashtable hm = new Hashtable();
		hm.put("1", "asdf");
		hm.put("2", "asdf");
		hm.put("3", "asdf");
		hm.put("4", "asdf");
		hm.put("5", "asdf");

		for (Object obj : hm.keySet()) {
			System.out.println(obj);
			hm.put("6", "asdf");
		}
	}

}
