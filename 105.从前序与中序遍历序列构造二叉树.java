import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=105 lang=java
 *
 * [105] 从前序与中序遍历序列构造二叉树
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
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int N = preorder.length;
        if(N == 0){
            return null;
        }

        TreeNode root = new TreeNode(preorder[0]);
        for(int i=0;i<N;i++){
            if(preorder[0] == inorder[i]){
                int[] preLeft = Arrays.copyOfRange(preorder,1 , i+1);
                int[] preRight = Arrays.copyOfRange(preorder, i+1, N);
                int[] inLeft = Arrays.copyOfRange(inorder, 0, i);
                int[] inRight = Arrays.copyOfRange(inorder, i+1, N);

                root.left = buildTree(preLeft,inLeft);
                root.right = buildTree(preRight,inRight);
            }
        }
        return root;
    }
}
// @lc code=end

