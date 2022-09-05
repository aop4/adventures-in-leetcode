package com.andrewpuglionesi.leetcode.validatebinarysearchtree;

import com.andrewpuglionesi.datastructures.BinaryTree;
import lombok.NonNull;

@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class ValidateBinarySearchTree {
    /**
     * Represents statistics describing a subtree of a {@link BinaryTree}.
     */
    private static class SubTreeStats {
        /**
         * The minimum value among the nodes in the subtree.
         */
        private int min;
        /**
         * The maximum value among the nodes in the subtree.
         */
        private int max;
        /**
         * Whether the subtree is a valid binary search tree (i.e., for every node, its left subtree contains values
         * strictly less than it, and its right subtree contains values strictly greater than it.)
         */
        private boolean isValidSubtree;

        public SubTreeStats(boolean isValidSubtree) {
            this.isValidSubtree = isValidSubtree;
        }

        public SubTreeStats(int min, int max, boolean isValidSubtree) {
            this.min = min;
            this.max = max;
            this.isValidSubtree = isValidSubtree;
        }
    }

    private static final SubTreeStats INVALID_SUBTREE = new SubTreeStats(false);

    /**
     * Checks whether {@code tree} is a valid binary search tree.
     * @param tree a {@link BinaryTree}.
     * @return true if the tree is empty or meets these criteria for a valid binary search tree: for every node,
     * its left subtree contains values strictly less than it, and its right subtree contains values strictly greater
     * than it. Returns false if this is not the case for any subtree of {@code tree} or if any node in the tree has a
     * null {@code value}.
     */
    public boolean isValidBst(@NonNull BinaryTree<Integer> tree) {
        if (tree.getRoot() == null) {
            return true;
        }
        SubTreeStats stats = this.collectSubTreeStats(tree.getRoot());
        return stats.isValidSubtree;
    }

    /**
     * Recursively collects min and max data for the left and right subtrees of {@code curr}. If any subtree is found
     * to be invalid (it defies the restrictions on binary search trees), a flag indicating an invalid subtree
     * (a {@link SubTreeStats} with isValidSubtree == false) will be returned. The min and max values in the returned
     * object will not be reliable if isValidSubtree is false.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    private SubTreeStats collectSubTreeStats(BinaryTree.Node<Integer> curr) {
        // disqualify tree if any node has a null value attribute
        if (curr.getValue() == null) {
            return INVALID_SUBTREE;
        }

        SubTreeStats leftSubTreeStats = null;
        if (curr.getLeft() != null) {
            leftSubTreeStats = this.collectSubTreeStats(curr.getLeft());
            if (!leftSubTreeStats.isValidSubtree || leftSubTreeStats.max >= curr.getValue()) {
                return INVALID_SUBTREE;
            }
        }

        SubTreeStats rightSubTreeStats = null;
        if (curr.getRight() != null) {
            rightSubTreeStats = this.collectSubTreeStats(curr.getRight());
            if (!rightSubTreeStats.isValidSubtree || rightSubTreeStats.min <= curr.getValue()) {
                return INVALID_SUBTREE;
            }
        }

        return new SubTreeStats(
                leftSubTreeStats != null ? leftSubTreeStats.min : curr.getValue(),
                rightSubTreeStats != null ? rightSubTreeStats.max : curr.getValue(),
                true
        );
    }
}
