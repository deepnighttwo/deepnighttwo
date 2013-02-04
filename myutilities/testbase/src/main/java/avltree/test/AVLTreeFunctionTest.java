package avltree.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import avltree.AVLTree;
import avltree.AVLTreeNode;
import avltree.ITraverseAVLTree;

public class AVLTreeFunctionTest extends TreeTestBase {

	@Test
	public void testAVLTreeIterator() {
		for (int j = 0; j < loop; j++) {
			AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
			String dummyValue = "";

			for (int i = 0; i < unit; i++) {
				int a = getRamdomInt();
				tree.put(a, dummyValue);
			}

			int value = Integer.MIN_VALUE;
			for (AVLTreeNode<Integer, String> node : tree) {
				Assert.assertTrue(value < node.key);
				value = node.key;
			}
		}
	}

	@Test
	public void testAVLTreeAddLooply() {
		for (int j = 0; j < loop; j++) {
			AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
			String dummyValue = "";

			for (int i = 0; i < unit; i++) {
				int a = getRamdomInt();
				tree.put(a, dummyValue);
				logc(a + "   ");
			}
			AVLTreeNode<Integer, String> root = tree.getRoot();
			checkAVLHight(root);
			log();
		}
	}

	@Test
	public void testAVLTreeFindLooply() {
		for (int j = 0; j < loop; j++) {
			Set<Integer> keys = new HashSet<Integer>();
			AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
			String dummyValue = "";
			for (int i = 0; i < unit; i++) {
				int a = getRamdomInt();
				tree.put(a, dummyValue);
				keys.add(a);
			}

			checkAVLHight(tree.getRoot());

			Integer[] arr = keys.toArray(new Integer[0]);
			for (Integer key : arr) {
				Assert.assertNotNull(tree.get(key));
			}

		}
	}

	@Test
	public void testAVLTreeDeleteLooply() {
		for (int j = 0; j < loop; j++) {
			Set<Integer> keys = new HashSet<Integer>();
			AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
			String dummyValue = "";
			for (int i = 0; i < unit; i++) {
				int a = getRamdomInt();
				tree.put(a, dummyValue);
				keys.add(a);
				logc(a + ", ");
			}
			log();
			checkAVLHight(tree.getRoot());

			Integer[] arr = keys.toArray(new Integer[0]);
			for (Integer key : arr) {
				logc(key + ", ");
			}
			for (Integer key : arr) {
				try {
					Assert.assertNotNull(tree.remove(key));
					checkAVLHight(tree.getRoot());
				} catch (Throwable e) {
					break;
				}
			}
		}
	}

	@Test
	public void testAVLTreeInorderTraveralLooply() {
		for (int j = 0; j < loop; j++) {
			AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
			String dummyValue = "";
			for (int i = 0; i < unit; i++) {
				int a = getRamdomInt();
				tree.put(a, dummyValue);
				logc(a + ", ");
			}
			log();
			checkAVLHight(tree.getRoot());

			tree.inoderTraversal(tree.getRoot(),
					new ITraverseAVLTree<Integer, String>() {

						int value = Integer.MIN_VALUE;

						@Override
						public void traverse(AVLTreeNode<Integer, String> node) {
							int currValue = node.key;
							logc(node.key + ", ");
							Assert.assertTrue(value <= currValue);
							value = currValue;
						}

					});
		}
	}

	@Test
	public void testAVLTreeAddandDeleteLooply() {
		for (int j = 0; j < loop; j++) {
			Set<Integer> keys = new HashSet<Integer>();
			AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
			String dummyValue = "";
			for (int i = 0; i < unit; i++) {
				int a = getRamdomInt();
				tree.put(a, dummyValue);
				if (i % 3 != 0) {
					keys.add(a);
				}
				logc(a + ", ");
			}
			log();
			checkAVLHight(tree.getRoot());

			Integer[] arr = keys.toArray(new Integer[0]);
			for (Integer key : arr) {
				logc(key + ", ");
			}
			for (int i = 0; i < arr.length; i++) {
				try {
					Assert.assertNotNull(tree.remove(arr[i]));
					checkAVLHight(tree.getRoot());

					if (i % 3 == 0) {
						int add = (int) (Math.random() * 3) + 1;
						for (int a = 0; a < add; a++) {
							int newA = getRamdomInt();
							tree.put(newA, dummyValue);
						}
					}

				} catch (Throwable e) {
					break;
				}
			}
		}
	}

	@Test
	public void testAVLTreeDeleteOneCase() {
		Set<Integer> keys = new HashSet<Integer>();
		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
		int[] arrr = getSimpleArray(new int[] { 9874212, 5714712, 3145063,
				1751412, 9273885, 1261300, 3032062, 5455552, 1673563, 8388361 });
		String dummyValue = "";
		for (int i = 0; i < arrr.length; i++) {
			tree.put(arrr[i], dummyValue);
			keys.add(arrr[i]);
		}
		System.out.println();

		for (Integer key : keys) {
			Assert.assertNotNull(tree.remove(key));
			checkAVLHight(tree.getRoot());
		}
	}

	@Test
	public void testAVLTreeAddOneCase() {
		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
		String dummyValue = "";

		int[] test = new int[] { 3, 7, 4, 0, 6, 2, 5, 1, 9, 8 };
		for (int i = 0; i < test.length; i++) {
			tree.put(test[i], dummyValue);
		}
		AVLTreeNode<Integer, String> root = tree.getRoot();
		checkAVLHight(root);
	}

	private void checkAVLHight(AVLTreeNode<Integer, String> root) {
		if (root == null) {
			return;
		}

		// leaf node, check all height is 0
		if (root.left == null && root.right == null) {
			if (root.lh != 0 || root.rh != 0) {
				throw new RuntimeException("Leaf node hight is not 0.");
			}
			return;
		}

		if (root.left == null) {
			// check left height is 0 when left node is null
			if (root.lh != 0) {
				throw new RuntimeException("Left node height is not 0.");
			}
		} else {
			// check left height is left child height + 1 when left node is not
			// null
			if (root.lh != root.left.getHight() + 1) {
				throw new RuntimeException("Left node height is not correct.");
			}
		}

		if (root.right == null) {
			// check right height is 0 when right node is null
			if (root.rh != 0) {
				throw new RuntimeException("Right node height is not 0.");
			}
		} else {
			// check right height is right child height + 1 when right node is
			// not null
			if (root.rh != root.right.getHight() + 1) {
				throw new RuntimeException("Right node height is not correct.");
			}
		}

		// check left right height diff is no bigger than 1
		if (root.getHightDiff() > 1) {
			throw new RuntimeException("Tree is not balanced.");
		}

	}

}
