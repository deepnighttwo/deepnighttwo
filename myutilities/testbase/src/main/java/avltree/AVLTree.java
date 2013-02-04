package avltree;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AVLTree<T extends Comparable<T>, E> implements
		Iterable<AVLTreeNode<T, E>>, Map<T, E> {

	private AVLTreeNode<T, E> root;

	public AVLTree() {

	}

	public AVLTreeNode<T, E> getRoot() {
		return root;
	}

	private volatile int size = 0;

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return root == null;
	}

	@Override
	public boolean containsKey(Object key) {
		return getTreeNode(key) != null;
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException(
				"Method containsValue() is not supported in this implementation.");
	}

	@Override
	public void putAll(Map<? extends T, ? extends E> m) {
		throw new UnsupportedOperationException(
				"Method putAll() is not supported in this implementation.");

	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException(
				"Method clear() is not supported in this implementation.");

	}

	@Override
	public Set<T> keySet() {
		throw new UnsupportedOperationException(
				"Method keySet() is not supported in this implementation.");
	}

	@Override
	public Collection<E> values() {
		throw new UnsupportedOperationException(
				"Method values() is not supported in this implementation.");

	}

	@Override
	public Set<java.util.Map.Entry<T, E>> entrySet() {
		throw new UnsupportedOperationException(
				"Method entrySet() is not supported in this implementation.");
	}

	public E put(T key, E value) {
		AVLTreeNode<T, E> newNode = new AVLTreeNode<T, E>(key, value);
		if (root == null) {
			root = newNode;
			return value;
		}
		AVLTreeNode<T, E> parent = root;
		while (true) {
			if (parent.compareTo(key) == 0) {
				E oldValue = parent.value;
				parent.value = value;
				return oldValue;
			}

			if (parent.compareTo(key) > 0) {
				// parent is bigger, put it on left side
				if (parent.left == null) {
					parent.left = newNode;
					newNode.parent = parent;
					break;
				} else {
					parent = parent.left;
				}
			} else {
				// parent is smaller, put it on right side
				if (parent.right == null) {
					parent.right = newNode;
					newNode.parent = parent;
					break;
				} else {
					parent = parent.right;
				}
			}
		}
		AVLTreeNode.recountHight(newNode);

		rotate(parent);

		return null;
	}

	private void rotate(AVLTreeNode<T, E> currNode) {
		boolean rorated = false;
		while (currNode != null) {
			int hDiff = currNode.getHightDiff();
			if (hDiff < 2) {
				if (rorated == true) {
					return;
				}
				currNode = currNode.parent;
				continue;
			}
			AVLTreeNode<T, E> p1 = currNode;
			AVLTreeNode<T, E> p2 = null;
			AVLTreeNode<T, E> p3 = null;

			// tree height diff is bigger than 1, need rotate
			boolean p1LeftHigher = p1.isLeftHigher();
			if (p1LeftHigher == true) {
				p2 = p1.left;
			} else {
				p2 = p1.right;
			}
			boolean p2LeftHigher = p2.isLeftHigher();

			if (p2LeftHigher == true) {
				p3 = p2.left;
			} else {
				p3 = p2.right;
			}

			if (p1LeftHigher == true && p2LeftHigher == true) {
				// right rotation of p2
				rotateRight(p2);
				if (p2.getHightDiff() < 2) {
					currNode = p2.parent;
				} else {
					currNode = p2;
				}

			} else if (p1LeftHigher == false && p2LeftHigher == false) {
				// left rotation of p2
				rotateLeft(p2);
				if (p2.getHightDiff() < 2) {
					currNode = p2.parent;
				} else {
					currNode = p2;
				}
			} else if (p1LeftHigher == true && p2LeftHigher == false) {
				// left rotation of p3, then right rotation of new p3
				rotateLeft(p3);
				rotateRight(p3);
				if (p3.getHightDiff() < 2) {
					currNode = p3.parent;
				} else {
					currNode = p3;
				}
			} else if (p1LeftHigher == false && p2LeftHigher == true) {
				// right rotation of p3, then left rotation of new p3
				rotateRight(p3);
				rotateLeft(p3);
				if (p3.getHightDiff() < 2) {
					currNode = p3.parent;
				} else {
					currNode = p3;
				}
			}
			rorated = true;
		}
	}

	private void rotateRight(AVLTreeNode<T, E> base) {
		AVLTreeNode<T, E> bP1 = base.parent;
		AVLTreeNode<T, E> bP2 = bP1.parent;
		AVLTreeNode<T, E> bRgiht = base.right;

		// base right point to base parent
		base.right = bP1;
		bP1.parent = base;
		// base parent left point to base right
		bP1.left = bRgiht;
		if (bRgiht != null) {
			bRgiht.parent = bP1;
		}
		// base parent point to base parent parent
		base.parent = bP2;
		// base parent parent left/right point to base
		if (bP2 == null) {
			// base parent parent is root;
			root = base;
		} else {
			if (bP2.left == bP1) {
				bP2.left = base;
			} else {
				bP2.right = base;
			}
		}

		AVLTreeNode.recountHight(bP1);
	}

	private void rotateLeft(AVLTreeNode<T, E> base) {
		AVLTreeNode<T, E> bP1 = base.parent;
		AVLTreeNode<T, E> bP2 = bP1.parent;
		AVLTreeNode<T, E> bLeft = base.left;

		// base left point to base parent
		base.left = bP1;
		bP1.parent = base;
		// base parent right point to base left
		bP1.right = bLeft;
		if (bLeft != null) {
			bLeft.parent = bP1;
		}
		// base parent point to base parent parent
		base.parent = bP2;
		// base parent parent left/right point to base
		if (bP2 == null) {
			root = base;
		} else {
			if (bP1 == bP2.left) {
				bP2.left = base;
			} else {
				bP2.right = base;
			}
		}

		AVLTreeNode.recountHight(bP1);
	}

	private E deleteLeaf(AVLTreeNode<T, E> del) {
		AVLTreeNode<T, E> p = del.parent;
		E ret = del.value;

		if (p == null) {
			root = null;
		} else {
			if (p.left == del) {
				p.left = null;
			} else {
				p.right = null;
			}
			AVLTreeNode.recountHight(p);
			rotate(p);
		}
		del.left = null;
		del.right = null;
		del.parent = null;
		del.key = null;
		del.value = null;

		return ret;
	}

	public E remove(Object key) {
		final AVLTreeNode<T, E> del = getTreeNode(key);

		if (del == null) {
			return null;
		}

		E ret = del.value;

		// leaf node, delete it, count height, rotate.
		if (del.left == null && del.right == null) {
			return deleteLeaf(del);
		}

		AVLTreeNode<T, E> delHC = del;
		if (del.left != null && del.right != null) {
			delHC = successor(del);
			// assign delHC value to del, as if del is replaced by delHC. then
			// delete delHC.
			del.value = delHC.value;
			del.key = delHC.key;
		}

		AVLTreeNode<T, E> dr = delHC.right;
		AVLTreeNode<T, E> dl = delHC.left;
		AVLTreeNode<T, E> p = delHC.parent;

		if (dr == null && dl == null) {
			return deleteLeaf(del);
		}
		if (dl == null) {
			// have right child
			dr.parent = p;
			if (p != null) {
				if (p.left == delHC) {
					p.left = dr;
				} else {
					p.right = dr;
				}
				AVLTreeNode.recountHight(p);
				rotate(p);
			} else {
				root = dr;
				AVLTreeNode.recountHight(dr);
				rotate(dr);
			}

		} else if (dr == null) {
			// have left child
			dl.parent = p;
			if (p != null) {
				if (p.left == delHC) {
					p.left = dl;
				} else {
					p.right = dl;
				}
				AVLTreeNode.recountHight(p);
				rotate(p);
			} else {
				root = dl;
				AVLTreeNode.recountHight(dl);
				rotate(dl);
			}
		}
		delHC.key = null;
		delHC.value = null;
		delHC.left = null;
		delHC.right = null;
		delHC.parent = null;
		return ret;

	}

	public E get(Object key) {
		AVLTreeNode<T, E> valueNode = getTreeNode(key);
		if (valueNode == null) {
			return null;
		} else {
			return valueNode.value;
		}
	}

	public AVLTreeNode<T, E> getTreeNode(Object key) {
		if (key == null) {
			return null;
		}
		AVLTreeNode<T, E> currNode = root;
		while (currNode != null) {
			int cmp = currNode.compareTo(key);
			if (cmp == 0) {
				return currNode;
			}
			if (cmp > 0) {
				currNode = currNode.left;
			} else {
				currNode = currNode.right;
			}
		}
		return null;
	}

	public void preoderTraversal(AVLTreeNode<T, E> node,
			ITraverseAVLTree<T, E> handler) {
		handler.traverse(node);

		if (node.left != null) {
			preoderTraversal(node.left, handler);
		}

		if (node.right != null) {
			preoderTraversal(node.right, handler);
		}

	}

	public void inoderTraversal(AVLTreeNode<T, E> node,
			ITraverseAVLTree<T, E> handler) {

		if (node.left != null) {
			inoderTraversal(node.left, handler);
		}

		handler.traverse(node);

		if (node.right != null) {
			inoderTraversal(node.right, handler);
		}
	}

	public void postoderTraversal(AVLTreeNode<T, E> node,
			ITraverseAVLTree<T, E> handler) {
		if (node.left != null) {
			postoderTraversal(node.left, handler);
		}

		if (node.right != null) {
			postoderTraversal(node.right, handler);
		}
		handler.traverse(node);

	}

	public Iterator<AVLTreeNode<T, E>> iterator() {
		return new AVLTreeIterator();
	}

	public AVLTreeNode<T, E> successor(AVLTreeNode<T, E> node) {
		if (node.right != null) {
			AVLTreeNode<T, E> s = node.right;
			while (s.left != null) {
				s = s.left;
			}
			return s;
		}
		AVLTreeNode<T, E> p = node.parent;
		AVLTreeNode<T, E> s = node;
		while (p != null && p.right == s) {
			s = p;
			p = s.parent;
		}
		return p;
	}

	class AVLTreeIterator implements Iterator<AVLTreeNode<T, E>> {

		private AVLTreeNode<T, E> current = null;

		public AVLTreeIterator() {
			if (root == null) {
				current = null;
				return;
			}
			AVLTreeNode<T, E> tmp = root;
			while (tmp.left != null) {
				tmp = tmp.left;
			}
			current = tmp;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public AVLTreeNode<T, E> next() {
			AVLTreeNode<T, E> ret = current;
			current = successor(current);
			return ret;
		}

		@Override
		public void remove() {
			AVLTree.this.remove(current.key);
		}

	}

}
