package avltree.test;

import avltree.AVLTree;

public class AVLTreePerfTest extends TreePerfBase {

	@Override
	public AVLTree<Integer, String> createTestTree() {
		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
		return tree;
	}

}
