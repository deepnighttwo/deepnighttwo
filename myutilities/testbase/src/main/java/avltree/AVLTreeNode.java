package avltree;

public class AVLTreeNode<T extends Comparable<T>, E> {

	public T key;
	public E value;

	public AVLTreeNode<T, E> parent;

	public AVLTreeNode<T, E> left;

	public AVLTreeNode<T, E> right;

	public int lh;

	public int rh;

	public AVLTreeNode(T key, E value) {
		this.key = key;
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	public int compareTo(Object another) {
		return key.compareTo((T) another);
	}

	public int getHightDiff() {
		return Math.abs(lh - rh);
	}

	public int getHight() {
		return Math.max(lh, rh);
	}

	public boolean isLeftHigher() {
		return lh > rh;
	}

	public static void recountHight(AVLTreeNode<?, ?> currNode) {
		if (currNode == null) {
			// throw new IllegalArgumentException("Child is null.");
		}

		if (currNode.left != null) {
			currNode.lh = currNode.left.getHight() + 1;
		} else {
			currNode.lh = 0;
		}
		if (currNode.right != null) {
			currNode.rh = currNode.right.getHight() + 1;
		} else {
			currNode.rh = 0;
		}

		AVLTreeNode<?, ?> p = currNode.parent;
		int tempH;
		while (p != null) {

			if (p.left == currNode) {
				tempH = currNode.getHight() + 1;
				if (p.lh == tempH) {
					break;
				}
				p.lh = tempH;
			} else if (p.right == currNode) {
				tempH = currNode.getHight() + 1;
				if (p.rh == tempH) {
					break;
				}
				p.rh = tempH;
			} else {
				throw new IllegalArgumentException(
						"Argument is not a child of current node.");
			}
			currNode = p;
			p = p.parent;
		}

	}

	public void recountHightForRotation() {

	}

	public String toString() {
		return key + "  " + "LH=" + lh + "; RH=" + rh;
	}
}