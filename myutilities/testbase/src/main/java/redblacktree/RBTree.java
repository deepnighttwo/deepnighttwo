package redblacktree;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class RBTree<T extends Comparable<T>, E> implements Map<T, E> {

	public static final boolean RED = true;
	public static final boolean BLACK = false;

	private RBTreeNode<T, E> root;

	private int size;

	public RBTreeNode<T, E> getRoot() {
		return root;
	}

	public static class RBTreeNode<T extends Comparable<T>, E> {

		RBTreeNode<T, E> parent;

		RBTreeNode<T, E> left;

		RBTreeNode<T, E> right;

		boolean color = RED;

		T key;

		E value;

		public RBTreeNode(T key, E value) {
			this.key = key;
			this.value = value;
		}

		public int compareTo(T another) {
			return key.compareTo(another);
		}

		public String toString() {
			String p = (parent == null) ? "NULL" : parent.key.toString();
			String l = (left == null) ? "NULL" : left.key.toString();
			String r = (right == null) ? "NULL" : right.key.toString();

			return "node: " + key + ", p: " + p + ", left: " + l + ", right: "
					+ r;
		}

	}

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException(
				"Method containsValue() is not supported in this implementation.");
	}

	@SuppressWarnings("unchecked")
	@Override
	public E get(Object key) {
		T nkey = (T) key;
		RBTreeNode<T, E> node = getNodeByKey(nkey);
		if (node == null) {
			return null;
		}
		return node.value;
	}

	private RBTreeNode<T, E> getNodeByKey(T key) {
		RBTreeNode<T, E> current = root;
		while (current != null) {
			int cmp = current.compareTo(key);
			if (cmp > 0) {
				current = current.left;
			} else if (cmp < 0) {
				current = current.right;
			} else {
				return current;
			}
		}
		return null;
	}

	@Override
	public E put(T key, E value) {
		RBTreeNode<T, E> node = new RBTreeNode<T, E>(key, value);
		if (root == null) {
			root = node;
			root.color = BLACK;
			size++;
			return null;
		}

		RBTreeNode<T, E> curr = root;
		int cmp;
		while (true) {
			cmp = curr.compareTo(key);
			if (cmp == 0) {
				E oldValue = curr.value;
				curr.value = value;
				return oldValue;
			} else if (cmp < 0) {
				if (curr.right == null) {
					break;
				}
				curr = curr.right;
			} else {
				if (curr.left == null) {
					break;
				} else {
					curr = curr.left;
				}
			}
		}

		if (cmp < 0) {
			curr.right = node;
		} else if (cmp > 0) {
			curr.left = node;
		}
		node.parent = curr;

		adjustAfterAdd(node);

		root.color = BLACK;
		return null;
	}

	private boolean colorOf(RBTreeNode<T, E> node) {
		if (node == null) {
			return BLACK;
		}
		return node.color;
	}

	private boolean isLeft(RBTreeNode<T, E> node) {
		if (node == null || node.parent == null) {
			throw new IllegalArgumentException("Node or node parent is null");
		}
		return node.parent.left == node;
	}

	private boolean colorOfBrother(RBTreeNode<T, E> node) {
		if (node == null || node.parent == null) {
			throw new IllegalArgumentException("Node or node parent is null");
		}
		if (isLeft(node)) {
			return colorOf(node.parent.right);
		} else {
			return colorOf(node.parent.left);
		}
	}

	private void toRed(RBTreeNode<T, E> node) {
		if (node == null) {
			return;
		}
		node.color = RED;
	}

	private void toBlack(RBTreeNode<T, E> node) {
		if (node == null) {
			return;
		}
		node.color = BLACK;
	}

	private RBTreeNode<T, E> brotherOf(RBTreeNode<T, E> node) {
		if (node == null || node.parent == null) {
			return null;
		}
		if (isLeft(node)) {
			return node.parent.right;
		}
		return node.parent.left;
	}

	// private void checkCircular(RBTreeNode<?, ?> root, HashSet<Object> nodes)
	// {
	// if (root == null) {
	// return;
	// }
	// if (nodes == null) {
	// nodes = new HashSet<Object>();
	// }
	// if (nodes.contains(root)) {
	// throw new RuntimeException("Circular! ");
	// }
	// nodes.add(root);
	// checkCircular(root.left, nodes);
	// checkCircular(root.right, nodes);
	// }
	//
	// private void printStructure(RBTreeNode<?, ?> node) {
	// System.out.println("node\t" + node.toString());
	// System.out.println("nodeP\t"
	// + (node.parent == null ? "NULL" : node.parent.toString()));
	// System.out.println("nodeL\t"
	// + (node.left == null ? "NULL" : node.left.toString()));
	// System.out.println("nodeR\t"
	// + (node.right == null ? "NULL" : node.right.toString()));
	// System.out.println("=============");
	// }

	private void adjustAfterAdd(RBTreeNode<T, E> node) {
		while (node != null && colorOf(node) == RED
				&& colorOf(node.parent) == RED) {
			RBTreeNode<T, E> p = node.parent;
			if (colorOfBrother(p) == RED) {
				toBlack(p);
				toBlack(brotherOf(p));
				toRed(p.parent);
				node = p.parent;
				continue;
			}
			// now, handle this condition: parent is red, brother of parent is
			// black
			boolean l = isLeft(node);
			boolean pl = isLeft(p);
			if (l != pl) {
				if (l == true) {
					rotateRight(node);
				} else {
					rotateLeft(node);
				}
				node = p;
				p = node.parent;
			}

			pl = isLeft(p);
			RBTreeNode<T, E> pp = p.parent;
			if (pl == true) {
				rotateRight(p);
				toBlack(p);
				toRed(pp);
			} else {
				rotateLeft(p);
				toBlack(p);
				toRed(pp);
			}
			return;
		}
	}

	private void rotateRight(RBTreeNode<T, E> node) {
		RBTreeNode<T, E> p = node.parent;
		if (p == null || p.left != node) {
			throw new IllegalArgumentException("Can not rotate root node");
		}
		RBTreeNode<T, E> pp = p.parent;
		p.left = node.right;
		if (node.right != null) {
			node.right.parent = p;
		}

		node.parent = pp;
		if (pp == null) {
			root = node;
		} else if (isLeft(p)) {
			pp.left = node;
		} else {
			pp.right = node;
		}

		node.right = p;
		p.parent = node;

	}

	private void rotateLeft(RBTreeNode<T, E> node) {
		RBTreeNode<T, E> p = node.parent;
		// if (p == null) {
		// throw new IllegalArgumentException("Can not rotate root node");
		// }
		RBTreeNode<T, E> pp = p.parent;
		p.right = node.left;
		if (node.left != null) {
			node.left.parent = p;
		}

		node.parent = pp;
		if (pp == null) {
			root = node;
		} else if (isLeft(p) == true) {
			pp.left = node;
		} else {
			pp.right = node;
		}

		node.left = p;
		p.parent = node;

	}

	/**
	 * the smallest element which is bigger than node
	 * 
	 * @param node
	 * @return
	 */
	private RBTreeNode<T, E> successor(RBTreeNode<T, E> node) {
		if (node.right == null) {
			return null;
		}
		node = node.right;
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	/**
	 * the biggest element which is smaller than node
	 * 
	 * @param node
	 * @return
	 */
	private RBTreeNode<T, E> previous(RBTreeNode<T, E> node) {
		if (node.parent == null) {
			return null;
		}
		RBTreeNode<T, E> p = node.parent;
		while (p != null) {
			node = node.right;
		}
		return node;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E remove(Object key) {
		T nkey = (T) key;
		// references to the one to delete
		RBTreeNode<T, E> node = getNodeByKey(nkey);
		if (node == null) {
			return null;
		}
		if (node.left != null && node.right != null) {
			RBTreeNode<T, E> s = successor(node);
			node.value = s.value;
			node.key = s.key;
			node = s;
		}

		RBTreeNode<T, E> replace = null;
		if (node.left != null) {
			replace = node.left;
		} else if (node.right != null) {
			replace = node.right;
		}

		if (replace != null) {
			// node to delete is not leaf node
			replace.parent = node.parent;
			if (node.parent == null) {
				root = replace;
			} else if (isLeft(node) == true) {
				node.parent.left = replace;
			} else {
				node.parent.right = replace;
			}
			node.parent = node.left = node.right = null;
			if (node.color == BLACK) {
				adjustAfterDeletion(replace);
			}
		} else if (node == root) {
			// root is the only node and it will be deleted
			root = null;
		} else {
			if (node.color == BLACK) {
				adjustAfterDeletion(node);
			}
			// node to delete is leaf node;
			if (isLeft(node) == true) {
				node.parent.left = null;
			} else {
				node.parent.right = null;
			}
			node.parent = node.left = node.right = null;
		}

		return null;
	}

	private void adjustAfterDeletion(RBTreeNode<T, E> node) {

	}

	@Override
	public void putAll(Map<? extends T, ? extends E> m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<T> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<E> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<T, E>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
