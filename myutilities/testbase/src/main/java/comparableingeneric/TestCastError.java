package comparableingeneric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestCastError {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		convert(7, new Object());
		List<Comparable> go = new ArrayList<Comparable>();
		go.add("");
		go.add(7);
		Collections.sort(go);
	}

	public static <T> T convert(T t, Object obj) {
		return (T) obj;
	}

}
