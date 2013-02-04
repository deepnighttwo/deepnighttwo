package avltree.test;

import java.util.Map;

import org.junit.Test;

public abstract class TreePerfBase extends TreeTestBase {


	public abstract Map<Integer, String> createTestTree();

	@Test
	public void testAVLTreePerformance() {
		for (int j = 0; j < loop; j++) {
			Map<Integer, String> tree = createTestTree();
			String dummyValue = "";

			for (int i = 0; i < unit; i++) {
				int a = getRamdomInt();
				tree.put(a, dummyValue);
			}
		}
	}
}
