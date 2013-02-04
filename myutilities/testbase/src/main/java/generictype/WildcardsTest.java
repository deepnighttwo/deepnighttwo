package generictype;

import java.awt.Rectangle;
import java.util.List;
import java.util.Map;

import javax.swing.text.DefaultCaret;

public class WildcardsTest {

	public void testWildcard(List<?> list) {
		// list.add(new Object());
	}

	public void testWildcard2(List<? extends Rectangle> list) {
		// list.add(new Rectangle());

		for (Rectangle rect : list) {

		}
	}

	public void testWildcardMap(Map<String, ? extends Rectangle> map) {

	}

	public void testWildcardMap2() {
		Map<String, DefaultCaret> map = null;
		testWildcardMap(map);

	}

}
