package com.andrewpuglionesi.leetcode.sametree;

import com.andrewpuglionesi.datastructures.BinaryTree;
import com.andrewpuglionesi.datastructures.BinaryTree.Node;
import lombok.NonNull;

/**
 * What can I say, it's a classic.
 */
public class SameTree {
    /**
     * Determines whether two binary trees are equivalent. They'll be considered equivalent if all nodes are in
     * corresponding locations with corresponding values.
     * @param tree1 a binary tree.
     * @param tree2 another binary tree.
     * @return true if the trees are equivalent, false otherwise.
     * @param <T> data type of the nodes' values in each tree. Both trees must have exactly the same type.
     */
    public <T> boolean areTreesEqual(@NonNull final BinaryTree<T> tree1, @NonNull final BinaryTree<T> tree2) {
        if (tree1 == tree2) {
            return true;
        }
        return this.areSubTreesEqual(tree1.getRoot(), tree2.getRoot());
    }

    /**
     * Determines whether two subtrees, with roots {@code a} and {@code b}, are equivalent.
     * @param a the root of a subtree.
     * @param b the root of another subtree.
     * @return true if the subtrees are equivalent, false otherwise.
     * @param <T>  data type of the nodes' values in each subtree.
     */
    private <T> boolean areSubTreesEqual(final Node<T> a, final Node<T> b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.getValue().equals(b.getValue())
                && this.areSubTreesEqual(a.getLeft(), b.getLeft())
                && this.areSubTreesEqual(a.getRight(), b.getRight());
    }
}
