/*
 * @lc app=leetcode.cn id=92 lang=java
 *
 * [92] 反转链表 II
 */

// @lc code=start
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if(n == 1 || head == null) return head;

        ListNode nodeBeforeM = head;
        ListNode nodeAfterN = head;

        for(int i=1;i<m-1;i++){
            nodeBeforeM = nodeBeforeM.next;
        }

        for(int i=1;i<n+1;i++){
            nodeAfterN = nodeAfterN.next;
        }

        ListNode prev = null;
        ListNode cur = m == 1 ? head : nodeBeforeM.next;  //attention!
        ListNode lastNodeAfterReversed = cur;
        ListNode next;
        
        while(cur != nodeAfterN){
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }

        //re-link:
        nodeBeforeM.next = prev;
        lastNodeAfterReversed.next = nodeAfterN;

        return m == 1 ? prev : head;

    }
}
// @lc code=end

