public class Solution {
    public static int maxSubArray(int[] nums) {
        if (nums.length == 1) { return nums[0]; }
        int champion = 0;
        int i = 0; // pointer to current position
        int j = 0; // pointer to current negative
        int k = 0; // pointer to start of current subarray

        int iSum = 0;
        int jSum = 0;
        while (true) {
        while (nums[i] >= 0) {
            iSum += nums[i];
            i++;
            if (i == nums.length) {
                if (iSum > champion) {
                return iSum;
            } else {
                return champion;
            }
            }
        }
        if (iSum > champion) {
            champion = iSum;
        }
        j = i;
        jSum = 0;
        while (nums[j] <= 0) {
            jSum += Math.abs(nums[j]);
            if (i == 0) {
                // case where only negatives exist so far
                if (j != 0 && nums[j] > nums[j-1]) {
                    if (nums[j] > champion) {
                        champion = nums[j];
                    } 
                }
                if (j == 0) {
                    champion = nums[j];
                }
                if (j == nums.length - 1) {
                    return champion;
                }
            }
            j++;
            if (j == nums.length) {
                break;
            }
        }
        if (jSum >= iSum) {
            if (iSum > champion) {
                champion = iSum;
            }
            i = j;
            k = j;
            iSum = 0;
            jSum = 0;
        } else {
            iSum -= jSum;
            i = j;
        }
        if (i == nums.length) {
            if (iSum > champion) {
                return iSum;
            } else {
                return champion;
            }
        }

        }
    }

    public static void main(String[] args) {
        // int[] nums = {31,-41,59,26,-53,58,97,-93,-23,84};
        // int[] nums = {2,-1,2,1,3,-2,1,2,1,-2};
        int[] nums = {-84,-87,-78,-16,-94,-36,-87,-93,-50,-22,-63,-28,-91,-60,-64,-27,-41,-27,-73,-37,-12,-69,-68,-30,-83,-31,-63,-24,-68,-36,-30,-3,-23,-59,-70,-68,-94,-57,-12,-43,-30,-74,-22,-20,-85,-38,-99,-25,-16,-71,-14,-27,-92,-81,-57,-74,-63,-71,-97,-82,-6,-26,-85,-28,-37,-6,-47,-30,-14,-58,-25,-96,-83,-46,-15,-68,-35,-65,-44,-51,-88,-9,-77,-79,-89,-85,-4,-52,-55,-100,-33,-61,-77,-69,-40,-13,-27,-87,-95,-40 };
        int ans = maxSubArray(nums);
    }
}