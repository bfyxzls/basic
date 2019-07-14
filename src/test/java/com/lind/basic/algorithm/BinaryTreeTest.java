package com.lind.basic.algorithm;

import java.util.Arrays;
import lombok.ToString;
import org.junit.Test;

public class BinaryTreeTest {
  static int[] numDemo = {1, 2, 3, 4, 5, 6};
  TreeNode treeNode = new ListToBST().sortedArrayToBST(numDemo);

  @Test
  public void bstTraversing() {
    treeNode.print(Direct.after);
  }

  @Test
  public void bstFind() {
    TreeNode find3 = treeNode.search(3);
    TreeNode find10 = treeNode.search(10);
    TreeNode find5 = treeNode.search(5);

    System.out.println(find3);
    System.out.println(find10);
    System.out.println(find5);

  }

  @Test
  public void bstMaxMin() {
    TreeNode max = treeNode.maximum();
    TreeNode min = treeNode.minimum();
    System.out.println("max=" + max);
    System.out.println("min=" + min);
  }

  /**
   * 遍历的方向.
   */
  enum Direct {
    /**
     * 中序.
     */
    middle,
    /**
     * 前序.
     */
    before,
    /**
     * 后序.
     */
    after;
  }

  /**
   * 有序数据转为二叉查找树.
   */
  class ListToBST {
    public TreeNode sortedArrayToBST(int[] nums) {
      nums = Arrays.stream(nums).sorted().toArray();
      return sortedArrayToBST(nums, 0, nums.length - 1);

    }

    private TreeNode sortedArrayToBST(int[] nums, int left, int right) {
      if (left > right) {
        return null;                                        //边界条件，注意是left>right
      }
      int mid = (left + right) / 2;
      TreeNode root = new TreeNode(nums[mid]);
      root.left = sortedArrayToBST(nums, left, mid - 1);        //递归向左探索，范围变成left~mid-1;
      root.right = sortedArrayToBST(nums, mid + 1, right);
      return root;
    }
  }

  @ToString
  class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }

    /**
     * 遍历.
     */
    public void print(Direct direct) {
      StringBuffer stringBuffer = new StringBuffer();
      print(stringBuffer, this, direct, "ROOT:");
      System.out.println(stringBuffer.toString());
    }

    private void print(StringBuffer stringBuffer, TreeNode treeNode, Direct direct, String node) {
      if (treeNode != null) {

        if (direct == Direct.before) {
          stringBuffer.append(node + treeNode.val + "\n");
          print(stringBuffer, treeNode.left, direct, "L:");
          print(stringBuffer, treeNode.right, direct, "R:");
        } else if (direct == Direct.middle) {
          print(stringBuffer, treeNode.left, direct, "L:");
          stringBuffer.append(node + treeNode.val + "\n");
          print(stringBuffer, treeNode.right, direct, "R:");
        } else {
          print(stringBuffer, treeNode.left, direct, "L:");
          print(stringBuffer, treeNode.right, direct, "R:");
          stringBuffer.append(node + treeNode.val + "\n");
        }
      }
    }

    /*
     * 二分查找，最优时间复杂度OLog(n).
     */
    private TreeNode search(TreeNode x, int key) {
      if (x == null) {
        return x;
      }

      int cmp = key - x.val;
      if (cmp < 0) {
        return search(x.left, key);
      } else if (cmp > 0) {
        return search(x.right, key);
      } else {
        return x;
      }
    }

    public TreeNode search(int key) {
      return search(this, key);
    }

    /*
     * 查找最大结点：返回tree为根结点的二叉树的最大结点。
     */
    private TreeNode maximum(TreeNode tree) {
      if (tree == null) {
        return null;
      }

      while (tree.right != null) {
        tree = tree.right;
      }
      return tree;
    }

    public TreeNode maximum() {
      TreeNode p = maximum(this);
      if (p != null) {
        return p;
      }

      return null;
    }

    /*
     * 查找最大结点：返回tree为根结点的二叉树的最大结点。
     */
    private TreeNode minimum(TreeNode tree) {
      if (tree == null) {
        return null;
      }

      while (tree.left != null) {
        tree = tree.left;
      }
      return tree;
    }

    public TreeNode minimum() {
      TreeNode p = minimum(this);
      if (p != null) {
        return p;
      }

      return null;
    }
  }
}