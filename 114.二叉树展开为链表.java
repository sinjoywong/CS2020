/*
 * @lc app=leetcode.cn id=114 lang=java
 *
 * [114] 二叉树展开为链表
 */

// @lc code=start
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public void flatten(TreeNode root) {
       if (root == null) return; 

       flatten(root.left);
       flatten(root.right);
       TreeNode rightTmp = root.right;
       root.right = root.left;
       root.left = null; //don't forget it
       while(root.right != null){
           root = root.right;
       }
       root.right = rightTmp;

    }
}
// @lc code=end

