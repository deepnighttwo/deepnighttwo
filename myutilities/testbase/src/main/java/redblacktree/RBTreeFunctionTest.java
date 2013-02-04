package redblacktree;

import java.util.HashMap;

import org.junit.Test;

import redblacktree.RBTree.RBTreeNode;
import avltree.test.TreeTestBase;

public class RBTreeFunctionTest extends TreeTestBase {

	@Test
	public void testAdd() {
		String dummy = "";
		for (int j = 0; j < loop; j++) {
			// TreeMap<Integer, String> tree = new TreeMap<Integer, String>();

			RBTree<Integer, String> tree = new RBTree<Integer, String>();
			for (int i = 0; i < unit; i++) {
				int a = getRamdomInt();
				tree.put(a, dummy);
			}

			checkBlackHeight(tree.getRoot());
			map.clear();
		}
	}

	HashMap<Object, Integer> map = new HashMap<Object, Integer>();

	private void checkBlackHeight(RBTreeNode<?, ?> root) {
		int bhL = 0;
		int bhR = 0;
		if (root.left != null) {
			checkBlackHeight(root.left);
			bhL = map.get(root.left);
		}
		if (root.right != null) {
			checkBlackHeight(root.right);
			bhR = map.get(root.right);
		}
		if (bhL != bhR) {
			throw new RuntimeException("Black Height not balanced now!");
		}
		if (root.color == RBTree.BLACK) {
			bhL++;
		}
		map.put(root, bhL);
	}
}
