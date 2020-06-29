import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=106 lang=java
 *
 * [106] 从中序与后序遍历序列构造二叉树
 */

// @lc code=start
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int N = inorder.length;
        if(N == 0) return null;

        TreeNode root = new TreeNode(postorder[N-1]);
        for(int i=0;i<N;i++){
            if(inorder[i] == root.val){
                int[] inorderLeft = Arrays.copyOfRange(inorder, 0, i);
                int[] inorderRight = Arrays.copyOfRange(inorder, i+1, N);
                int[] postorderLeft = Arrays.copyOfRange(postorder, 0, i);
                int[] postorderRight = Arrays.copyOfRange(postorder, i, N-1);

                root.left = buildTree(inorderLeft,postorderLeft);
                root.right = buildTree(inorderRight,postorderRight);
            }
        }
        return root;
    }
}
// @lc code=end

