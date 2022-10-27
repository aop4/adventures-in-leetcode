package com.andrewpuglionesi.datastructures;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryTreeTest {
    @Test
    void buildFromLevelOrderTraversalNullInput() {
        NullPointerException e = assertThrows(NullPointerException.class, () -> {
            BinaryTree.buildFromLevelOrderTraversal(null);
        });
        assertEquals("levelOrderTraversal is marked non-null but is null", e.getMessage());
    }

    @Test
    void buildFromLevelOrderTraversalEmptyList() {
        BinaryTree<Integer> tree = BinaryTree.buildFromLevelOrderTraversal(Collections.emptyList());
        assertNull(tree.getRoot());
        assertEquals(0, tree.getSize());
    }

    @Test
    void buildFromLevelOrderTraversalOneNullItem() {
        BinaryTree<Integer> tree = BinaryTree.buildFromLevelOrderTraversal(Collections.singletonList(null));
        assertNull(tree.getRoot());
        assertEquals(0, tree.getSize());
    }

    @Test
    void buildFromLevelOrderTraversalMultipleNullItems() {
        assertThrows(UnsupportedOperationException.class, () ->
                BinaryTree.buildFromLevelOrderTraversal(Arrays.asList(null, null))
        );
    }

    @Test
    void buildFromLevelOrderTraversalNullRootWithNonNullChildren() {
        assertThrows(UnsupportedOperationException.class, () ->
            BinaryTree.buildFromLevelOrderTraversal(Arrays.asList(null, 2))
        );
    }

    @Test
    void buildFromLevelOrderTraversalOnlyTheRoot() {
        BinaryTree<Integer> tree = BinaryTree.buildFromLevelOrderTraversal(Collections.singletonList(
                7
        ));
        assertEquals(7, tree.getRoot().getValue());
        assertNull(tree.getRoot().getLeft());
        assertNull(tree.getRoot().getRight());
        assertEquals(1, tree.getSize());
    }

    @Test
    void buildFromLevelOrderTraversalOnlyNullItemsAfterRoot() {
        BinaryTree<Integer> tree = BinaryTree.buildFromLevelOrderTraversal(Arrays.asList(
                7,
          null,   null
        ));
        assertEquals(7, tree.getRoot().getValue());
        assertNull(tree.getRoot().getLeft());
        assertNull(tree.getRoot().getRight());
        assertEquals(1, tree.getSize());
    }

    @Test
    void buildFromLevelOrderTraversalNullLeftChildOfRoot() {
        BinaryTree<Integer> tree = BinaryTree.buildFromLevelOrderTraversal(Arrays.asList(
                7,
          null,    6
        ));

        BinaryTree.Node<Integer> root = tree.getRoot();
        assertEquals(7, root.getValue());

        BinaryTree.Node<Integer> left = root.getLeft();
        BinaryTree.Node<Integer> right = root.getRight();

        assertNull(left);

        assertEquals(6, right.getValue());
        assertNull(right.getLeft());
        assertNull(right.getRight());

        assertEquals(2, tree.getSize());
    }

    @Test
    void buildFromLevelOrderTraversalExplicitlyNullRightChildOfRoot() {
        BinaryTree<Integer> tree = BinaryTree.buildFromLevelOrderTraversal(Arrays.asList(
                7,
           6,      null
        ));

        BinaryTree.Node<Integer> root = tree.getRoot();
        assertEquals(7, root.getValue());

        BinaryTree.Node<Integer> left = root.getLeft();
        BinaryTree.Node<Integer> right = root.getRight();

        assertNull(right);

        assertEquals(6, left.getValue());
        assertNull(left.getLeft());
        assertNull(left.getRight());

        assertEquals(2, tree.getSize());
    }

    @Test
    void buildFromLevelOrderTraversalImplicitlyNullRightChildOfRoot() {
        // test list terminates its 2nd level early, without null reference
        BinaryTree<Integer> tree = BinaryTree.buildFromLevelOrderTraversal(Arrays.asList(
                7,
             6
        ));

        BinaryTree.Node<Integer> root = tree.getRoot();
        assertEquals(7, root.getValue());

        BinaryTree.Node<Integer> left = root.getLeft();
        BinaryTree.Node<Integer> right = root.getRight();

        assertNull(right);

        assertEquals(6, left.getValue());
        assertNull(left.getLeft());
        assertNull(left.getRight());

        assertEquals(2, tree.getSize());
    }

    @Test
    void buildFromLevelOrderTraversalThreeNodesSymmetric() {
        BinaryTree<Integer> tree = BinaryTree.buildFromLevelOrderTraversal(Arrays.asList(
                7,
            6,     5
        ));

        BinaryTree.Node<Integer> root = tree.getRoot();
        assertEquals(7, root.getValue());

        BinaryTree.Node<Integer> left = root.getLeft();
        BinaryTree.Node<Integer> right = root.getRight();

        assertEquals(6, left.getValue());
        assertNull(left.getLeft());
        assertNull(left.getRight());

        assertEquals(5, right.getValue());
        assertNull(right.getLeft());
        assertNull(right.getRight());

        assertEquals(3, tree.getSize());
    }

    @Test
    void buildFromLevelOrderTraversalLinearLeftLeft() {
        BinaryTree<Integer> tree = BinaryTree.buildFromLevelOrderTraversal(Arrays.asList(
                7,
            6,     null,
        5
        ));

        BinaryTree.Node<Integer> root = tree.getRoot();
        assertEquals(7, root.getValue());

        BinaryTree.Node<Integer> left = root.getLeft();
        BinaryTree.Node<Integer> right = root.getRight();

        assertNull(right);

        assertEquals(6, left.getValue());
        assertEquals(5, left.getLeft().getValue());
        assertNull(left.getRight());

        assertEquals(3, tree.getSize());
    }

    @Test
    void buildFromLevelOrderTraversalLinearRightRight() {
        BinaryTree<Integer> tree = BinaryTree.buildFromLevelOrderTraversal(Arrays.asList(
                  7,
          null,       6,
                 null,   5
        ));

        BinaryTree.Node<Integer> root = tree.getRoot();
        assertEquals(7, root.getValue());

        BinaryTree.Node<Integer> left = root.getLeft();
        BinaryTree.Node<Integer> right = root.getRight();

        assertNull(left);

        assertEquals(6, right.getValue());
        assertEquals(5, right.getRight().getValue());
        assertNull(right.getLeft());

        assertEquals(3, tree.getSize());
    }

    @Test
    void buildFromLevelOrderTraversalCompleteThreeLevelTree() {
        BinaryTree<Integer> tree = BinaryTree.buildFromLevelOrderTraversal(Arrays.asList(
                  1,
             2,       3,
          4,   5,   6,   7
        ));

        BinaryTree.Node<Integer> root = tree.getRoot();
        assertEquals(1, root.getValue());

        BinaryTree.Node<Integer> left = root.getLeft();
        BinaryTree.Node<Integer> right = root.getRight();

        assertEquals(2, left.getValue());
        assertEquals(3, right.getValue());

        assertEquals(4, left.getLeft().getValue());
        assertEquals(5, left.getRight().getValue());

        assertEquals(6, right.getLeft().getValue());
        assertEquals(7, right.getRight().getValue());

        assertEquals(7, tree.getSize());
    }

    @Test
    void buildFromLevelOrderTraversalSparseFourLevelTree() {
        BinaryTree<Integer> tree = BinaryTree.buildFromLevelOrderTraversal(Arrays.asList(
                  1,
             2,           3,
          null,null,   6,    7,
                    null, 8
        ));

        BinaryTree.Node<Integer> root = tree.getRoot();
        assertEquals(1, root.getValue());

        BinaryTree.Node<Integer> left = root.getLeft();
        BinaryTree.Node<Integer> right = root.getRight();

        assertEquals(2, left.getValue());
        assertEquals(3, right.getValue());

        assertNull(left.getLeft());
        assertNull(left.getRight());

        assertEquals(6, right.getLeft().getValue());
        assertEquals(7, right.getRight().getValue());

        assertNull(right.getLeft().getLeft());
        assertEquals(8, right.getLeft().getRight().getValue());

        assertEquals(6, tree.getSize());
    }

    @Test
    void buildFromLevelOrderTraversalDetectsAddingChildToNonexistentParent() {
        assertThrows(UnsupportedOperationException.class, () -> {
            BinaryTree.buildFromLevelOrderTraversal(Arrays.asList(
                         1,
                  2,         null,
              null, null,   6,   7 // there are no valid parents to add 6 and 7 to
            ));
        });
    }
}
