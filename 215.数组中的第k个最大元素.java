/*
 * @lc app=leetcode.cn id=215 lang=java
 *
 * [215] 数组中的第K个最大元素
 */

// @lc code=start
class Solution {
    public int findKthLargest(int[] nums, int k) {
        int N = nums.length;
        int[] priorQueue = new int[N+1];
        int priorQueueLen = 0;
        for(int i=0;i<N;i++){
            insert(priorQueue,priorQueueLen++,nums[i]);
        }

        for(int i=1;i<k;i++){
            deleteMax(priorQueue,priorQueueLen--);
        }
        return priorQueue[1];
    }

    public static void insert(int[] priorQueue,int priorQueueLen,int num){
        priorQueue[++priorQueueLen] = num;
        swim(priorQueue,priorQueueLen);
    }

    public static void swim(int[] priorQueue,int k){
        while(k > 1 && less(priorQueue,k/2,k)){
            swap(priorQueue,k/2,k);
            k = k/2;
        }
    }

    public static boolean less(int[] priorQueue,int i,int j){
        return priorQueue[i] < priorQueue[j];
    }

    public static void swap(int[] priorQueue,int i,int j){
        int tmp = priorQueue[i];
        priorQueue[i] = priorQueue[j];
        priorQueue[j] = tmp;
    }

    public static void deleteMax(int[] priorQueue,int priorQueueLen){
        swap(priorQueue,1,priorQueueLen);
        priorQueue[priorQueueLen] = Integer.MAX_VALUE;
        priorQueueLen--;
        sink(priorQueue,priorQueueLen,1);
    }

    public static void sink(int[] priorQueue,int priorQueueLen,int k){
        while(2*k <= priorQueueLen){
            int maxOfChildren = 2 * k;
            if(maxOfChildren< priorQueueLen && less(priorQueue,maxOfChildren,maxOfChildren+1)){
                maxOfChildren++;
            }

            if(!less(priorQueue, k, maxOfChildren)) break;
            swap(priorQueue, maxOfChildren, k);
            k = maxOfChildren;
        }
    }
}
// @lc code=end

