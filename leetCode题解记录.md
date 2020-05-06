# 数组与字符串

## [4. 寻找两个有序数组的中位数](https://leetcode-cn.com/problems/median-of-two-sorted-arrays/)（二分



## [5. 最长回文子串](https://leetcode-cn.com/problems/longest-palindromic-substring/)（重点，以多种方法处理）

回文类型的题目，主要考虑的是奇数和偶数的两种情况的判断处理。

### 解法一：回文类型的一般解法：中心扩展法



### 解法二：回文类型的第二种一般解法：翻转找匹配



### 解法三：



# 链表

## 翻转链表

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode cur = head;
        ListNode prev = null;
        ListNode next;
        while(cur != null){
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
}
```

## 合并两个有序链表

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode res = new ListNode(-1);
        ListNode cur = res;
        while(l1 != null && l2 != null){
            if(l1.val < l2.val){
                cur.next = l1;
                l1 = l1.next;
            }else{
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        if(l1 == null){
            cur.next = l2;
        }else if(l2 == null){
            cur.next = l1;
        }
        
        return res.next;
    }
}
```

## 合并K个有序链表

### 解法一：先实现一个合并两个链表的函数，再逐次遍历所有的链表

```java
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode res = null;
        for(int i=0;i<lists.length;i++){
            res = mergeTwoLists(res,lists[i]);
        }
        return res;
    }
    
    public ListNode mergeTwoLists(ListNode l1,ListNode l2){
        ListNode res = new ListNode(-1);
        ListNode cur = res;
        
        while(l1 != null && l2 != null){
            if(l1.val < l2.val){
                cur.next = l1;
                l1 = l1.next;
            }else{
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        if(l1 == null){
            cur.next = l2;
        }else if(l2 == null){
            cur.next = l1;
        }
        return res.next;
    }
}
```



## [2. 两数相加](https://leetcode-cn.com/problems/add-two-numbers/)

（每次都下意识想到翻转链表、链表转整数，再分割数字，转为链表的操作，忘记10^n会溢出的问题。

### 错误思路：翻转链表、链表转整数，分割数字，转为链表

```java
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1 == null){
            return l2;
        }
        if(l2 == null){
            return l1;
        }
        ListNode reversedL1 = reverseList(l1);
        ListNode reversedL2 = reverseList(l2);

        int n1 = listNodeToNumber(reversedL1);
        int n2 = listNodeToNumber(reversedL2);
        int n3 = n1 + n2;
        if(n3 == 0) return new ListNode(0);

        ListNode res = new ListNode(0);
        ListNode cur = res;
        int tmp = 0;
        while(n3 != 0){
            tmp = n3 % 10;
            n3 = n3 / 10;
            ListNode tmpRes = new ListNode(tmp);
            cur.next = tmpRes;
            cur = tmpRes;
        }
        return res.next;
    }
    
    
    public ListNode reverseList(ListNode head){
        if(head == null) return null;
        
        ListNode cur = head;
        ListNode prev = null;
        ListNode next;
        while(cur != null){
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
    
    public int listNodeToNumber(ListNode head){
        int num = 0;
        int N = 0;
        ListNode cur = head;
        while(cur != null){
            N++;
            cur = cur.next;
        }
        
        cur = head;
        for(int i=N-1;i>=0;i--){
            num += cur.val * Math.pow(10,i);
            cur = cur.next;
        }
        return num;
    }
}
```

### 正确解法：直接逐位处理，考虑进位

题目已经是要求链表逆序相加了，其实这时候已经表明了思路为从头到尾遍历，然后逐位相加，需要特殊处理的是进位操作：addtion = num / 10，初始化为0，需要在每次计算相加的结果的时候加上去，而该位的直接数字为num % 10。

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int addtion = 0;
        ListNode res = new ListNode(-1);
        ListNode cur = res;
        
        while(l1 != null || l2 != null){
            int n1 = l1 != null ? l1.val : 0;
            int n2 = l2 != null ? l2.val : 0;
            int n = n1 + n2 + addtion;
            
            int newVal = n % 10;
            addtion = n / 10;
            
            ListNode tmp = new ListNode(newVal);
            cur.next = tmp;
            cur = tmp;
            
            l1 = l1 != null ? l1.next : null;
            l2 = l2 != null ? l2.next : null;
        }
      //此处需要考虑最后一位的进一操作。两数相加最大进1，但此处还是通用地写为进addtion，更为本质
        if(addtion != 0){
            ListNode tmp = new ListNode(addtion);
            cur.next = tmp;
        }
        return res.next;
    }
}
```



## [61. 旋转链表](https://leetcode-cn.com/problems/rotate-list/)

既然是旋转，就意味着循环，就可以先将其连成循环链表，再找到新的头，断掉尾。 

```java
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        if(head == null) return null;
        if(head.next == null) return head;
        
        ListNode cur = head;
        int length = 1;
        while(cur.next != null){
            length++;
            cur = cur.next;
        }
        cur.next = head;
        
        cur = head;
        for(int i =0;i< length-k % length -1;i++){
            cur = cur.next;
        }
        ListNode newHead = cur.next;
        cur.next = null;
        return newHead;
    }
}
```

## [141. 环形链表](https://leetcode-cn.com/problems/linked-list-cycle/)

快慢指针的思路，注意边界的处理：`fastNode != null && fastNode.next != null`

```java
public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode fastNode = head;
        ListNode slowNode = head;
        while(fastNode != null && fastNode.next != null){
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;
            if(fastNode == slowNode){
                return true;
            }
        }
        return false;
    }
}
```



## [142. 环形链表 II](https://leetcode-cn.com/problems/linked-list-cycle-ii/)

与环形链表思想类似，需要一对快慢指针来判定是否是环。

有意思的一点是，如何来确定哪个节点是入环的第一个节点？这在环形链表中是无需处理的，因此也没有深入地去思考究竟快慢指针差了多少，才第一次相遇。

以一个一般场景为例，一个链表前一段（长度为x）为简单链表，从x+1处开始为环的起点，然后在经过了y个节点快慢指针相遇，然后又经过z个节点，回到了环的起点。

另外，可以知道快慢指针的行进速度刚好差一倍，因此有快指针的行进距离为慢指针的2倍。

这样就有以下等式：

```java
fast的行进距离: x + y + z + y
slow的行进距离: x + y
根据两者的2倍关系，有： x = z.
```

这样就意味着，环的起始位置距离head的距离与从相遇点到环起点的距离相同。

因此当快慢指针相遇后，可以将慢指针指向head，然后快慢指针以+1的形式向后移动，若二者相遇（因为x==z)，则二者都可以指向环的起始节点。

```java
public class Solution {
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow){
                slow = head;//use slow to find cycle first node
                while(fast != slow){
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }
}
```

## [160. 相交链表](https://leetcode-cn.com/problems/intersection-of-two-linked-lists/)

既然有可能相交，那也得对齐了才可能相交。可以先获得两个链表的长度，比较长的链表先走二者差异化的长度，等二者对齐了，再一块走。如果二者能够相同，则说明有相交。

```java
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lengthA = 0, lengthB = 0;
        ListNode curA = headA;
        while(curA != null){
            lengthA++;
            curA = curA.next;
        }
        
        ListNode curB = headB;
        while(curB != null){
            lengthB++;
            curB = curB.next;
        }
        
        int lengthSub;
        curA = headA;
        curB = headB;
        if(lengthA >= lengthB){
            lengthSub = lengthA - lengthB;
            for(int i=0;i<lengthSub;i++){
                curA = curA.next;
            }
            while(curA != null && curB != null){
                if(curA == curB){
                    return curA;
                }
                curA = curA.next;
                curB = curB.next;
            }
            return null;
        }else{
            lengthSub = lengthB - lengthA;
            for(int i=0;i<lengthSub;i++){
                curB = curB.next;
            }
            while(curA != null && curB != null){
                if(curA == curB){
                    return curA;
                }
                curA = curA.next;
                curB = curB.next;
            }
            return null;
        }
    }
}
```

## [237. 删除链表中的节点](https://leetcode-cn.com/problems/delete-node-in-a-linked-list/)

题目中只给了待删除的node，而没有给原有的完成链表，因此不能按照常规的删除思路。

可以直接将该节点与后面节点的值进行替换，指针指向下下个节点即可。

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
```

# 图论

## [130. 被围绕的区域](https://leetcode-cn.com/problems/surrounded-regions/)

难度中等

给定一个二维的矩阵，包含 `'X'` 和 `'O'`（**字母 O**）。

找到所有被 `'X'` 围绕的区域，并将这些区域里所有的 `'O'` 用 `'X'` 填充。

**示例:**

```
X X X X
X O O X
X X O X
X O X X
```

运行你的函数后，矩阵变为：

```
X X X X
X X X X
X X X X
X O X X
```

**解释:**

被围绕的区间不会存在于边界上，换句话说，任何边界上的 `'O'` 都不会被填充为 `'X'`。 任何不在边界上，或不与边界上的 `'O'` 相连的 `'O'` 最终都会被填充为 `'X'`。如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。

### 解题思路

还是图的遍历.
有什么区别呢?这类似于扫雷,或者围棋. 有完全被包围的才能被翻转为X,只要有一个位置没有被包围,则不能翻转.边界也被认为不被包围的情况.
怎么来表示"被包围"呢? 直接考虑是不好考虑的, 图论里好像也没见过考虑包围的算法.那么可不可以考虑反着来呢?
既然要把包围的翻转,那么可以先把不能被包围的标记为不能翻转,剩下的就都是能被翻转的--即被包围的了.
那什么样的是"不能被包围"的呢?
就是从图的四个边界开始的,为'O'的. 那么就可以以这里开始进行遍历连着都为'O'的,把他们标记为"不可翻转",然后再遍历一遍,剩余的就能被翻转了,把不能翻转的再给变回去即可.

```java
class Solution {
     public void solve(char[][] board) {
        int M = board.length;
        if(M == 0) return;
        int N = board[0].length;
        for(int i=0;i<M;i++){
            if(board[i][0] == 'O'){
                dfs(board,i,0,M,N);
            }
            if(board[i][N-1] == 'O'){
                dfs(board,i,N-1,M,N);
            }
        }
        for(int j=0;j<N;j++){
            if(board[0][j] == 'O'){
                dfs(board,0,j,M,N);
            }
            if(board[M-1][j] == 'O'){
                dfs(board,M-1,j,M,N);
            }
        }
        for(int i=0;i<M;i++){
            for(int j=0;j<N;j++){
                if(board[i][j] == '#'){
                    board[i][j] = 'O';
                }else if(board[i][j] == 'O'){
                    board[i][j] = 'X';
                }
            }
       }
}

public void dfs(char[][] board,int x,int y,int M,int N){
    if(x < 0 || x >= M || y < 0 || y >= N || board[x][y] == '#' || board[x][y] == 'X' ){
        return;
    }

    board[x][y] = '#';
    dfs(board,x+1,y,M,N);
    dfs(board,x-1,y,M,N);
    dfs(board,x,y+1,M,N);
    dfs(board,x,y-1,M,N);
}
```
}

作者：sinjoywong
链接：https://leetcode-cn.com/problems/surrounded-regions/solution/dfs-bei-bao-wei-zen-yao-gao-fan-bao-wei-by-sinjoyw/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。





# 动态规划

##  [面试题 17.16. 按摩师](https://leetcode-cn.com/problems/the-masseuse-lcci/)

典型的动态规划题目。从数学角度上考虑是找到一个数组中最少相隔距离为1的和为最大的子序列。

那么对于第i个数字，我们可以选，也可以不选。若选的话，得到的最大的值应该是这个值加上第i-2个位置处得到的最大值；若不选的话，得到的最大值应该是第i-1个位置处的值。那么第i个位置处的值则为这两者取较大值的结果。

```java
class Solution {
    public int massage(int[] nums) {
        int N = nums.length;
        if(N == 0) return 0;
        if(N == 1) return nums[0];
        int[] dp = new int[N];
        dp[0] = nums[0];
        dp[1] = Math.max(dp[0],nums[1]);

        for(int i=2;i<N;i++){
            dp[i] = Math.max(dp[i-1],dp[i-2]+nums[i]);
        }
        return dp[N-1];
    }
}
```

## [838. 推多米诺](https://leetcode-cn.com/problems/push-dominoes/)

## [53. 最大子序和](https://leetcode-cn.com/problems/maximum-subarray/)

题目要求是连续子数组，因此可以使用动态规划的思路处理。~~对于一个位置，可以选，也可以不选。~~（不是这样的思路）

对于一个索引`i`，要计算`dp[i]`的话，`i`处的值是一定要的，不是之前的“可以选或不选”的思路。

而由于可能在`i`之前的`dp[i-1]`可能是负值，从而影响整体的最大值，因此可以“只选第`i`个”，也可以“同时选第`i`个和第`i`个之前的子序和”。

最后，需要一个变量记录最大值即可。

```java
class Solution {
    public int maxSubArray(int[] nums) {
        int N = nums.length;
        int[] dp = new int[N+1];

        dp[0] = nums[0];
        int res = dp[0];
        for(int i=1;i<N;i++){
            dp[i] = Math.max(nums[i],dp[i-1]+nums[i]);
            res = Math.max(res,dp[i]);
        }
        return res;
    }
}
```



# 滑动窗口

## [3. 无重复字符的最长子串](https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/)

滑动窗口，给一个从0开始的start，边界条件为start<N。

给一个从-1开始的end，当满足start<N时，且end+1<N，同时end处的元素未在当前频率表中出现，则可以将滑动窗口扩大，即向右移动end++。否则从频率表中去掉该start处的值，并让start++从而让滑动窗口缩小。

过程中使用一个res来记录整个过程的最大值。

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int[] freq = new int[128];
        int res = 0;
        int N = s.length();
        int start = 0, end = -1;
        while(start < N){
            if(end + 1 < N && freq[s.charAt(end+1)] == 0){
                end++;
                freq[s.charAt(end)]++;
            }else{
                freq[s.charAt(start)]--;
                start++;
            }
            res = Math.max(res,end-start+1);
        }
        return res;
    }
}
```



# 二分搜索

## [69. x 的平方根](https://leetcode-cn.com/problems/sqrtx/)

由于原题目只要求返回整数，且可以知道x的平方根一定是在[0,x/2]之间，因此可以在其中间进行二分查找。

```java
class Solution {
    public int mySqrt(int x) {
        if(x<2) return x;
        long num;
        int pivot, left = 2,right = x/2;
        while(left <= right){
            pivot = left + (right - left ) /2 ;
            num = (long)pivot * pivot;
            if(num > x){
                right = pivot - 1;
            }else if(num < x){
                left = pivot + 1;
            }else{
                return pivot;
            }
        }
        return right;
    }
}
```

