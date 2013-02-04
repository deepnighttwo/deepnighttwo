package avltree;

public interface ITraverseAVLTree<T extends Comparable<T>, E> {

	void traverse(AVLTreeNode<T, E> node);

}
