package cn.scau.springcloud;

class NumArray {
    private int length;
    private int[] dp;

    public NumArray(int[] nums) {
        this.length = nums.length;
        if (length != 0) {
            dp = new int[length];
            initDp(nums);
            doDp(nums);
        }
    }

    public int sumRange(int i, int j) {
        if (length == 0) return 0;
        return i == 0 ? dp[j] : dp[j] - dp[i - 1];
    }

    // 初始化
    private void initDp(int[] nums) {

    }

    private void doDp(int[] nums) {
        dp[0] = nums[0];
        for (int i = 1; i < length; i++) {
            dp[i] = dp[i - 1] + nums[i];
        }
    }


}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(i,j);
 */
