package shutao.codility;

import java.util.Arrays;

/**
 * Created by shutao on 19/11/17.
 * https://codility.com/programmers/lessons/15-caterpillar_method/min_abs_sum_of_two/
 */
public class MinAbsSumOfTwo {
    public static void main(String[] args) {
        Solution1st solution1st = new Solution1st();
        System.out.println(solution1st.solution(new int[]{1, 4, -3}));
        System.out.println(solution1st.solution(new int[]{-8, 4, 5, -10, 3}));

        // https://codesays.com/2014/solution-to-min-abs-sum-of-two-by-codility/
        Solution2nd solution2nd = new Solution2nd();
        System.out.println(solution2nd.solution(new int[]{1, 4, -3}));
        System.out.println(solution2nd.solution(new int[]{-8, 4, 5, -10, 3}));
    }
}

/**
 * Not fullfill the time complexity !!!
 */
class Solution1st {
    public int solution(int[] A) {
        int globalMinAbs = Integer.MAX_VALUE;

        for(int i=0;i<A.length;i++){
            for(int j=i; j<A.length; j++) {
                int abs = Math.abs(A[i] + A[j]);
                if(abs < globalMinAbs) {
                    globalMinAbs = abs;
                }
            }
        }

        return globalMinAbs;
    }
}

/**
 * 1. 先排序 stream的sort 时间复杂度也是 O(n log n)，为了代码简洁，就直接用了。
 * 2. 如果全部正数或者负数，直接返回
 * 3. 从两端向中间移动
 */
class Solution2nd {
    public int solution(int[] A) {
        // 排序
        int[] sortedA = Arrays.stream(A).sorted().toArray();

        if(sortedA[0] >= 0) return sortedA[0] + sortedA[0]; // 全部 >=0
        if(sortedA[sortedA.length-1] <= 0) return (sortedA[sortedA.length-1] + sortedA[sortedA.length-1]) * -1; // 全部 <=0

        int left = 0;
        int right = sortedA.length-1;
        int minAbs = sortedA[sortedA.length-1] + sortedA[sortedA.length-1];

        // 从两端往中间移动,直到0，寻找最小绝对值
        while(left <= right) {
            int temp = Math.abs(sortedA[left] + sortedA[right]);

            if(temp < minAbs) {
                minAbs = temp;
            }

            if(sortedA[left] >= 0 || sortedA[right] <= 0) {
                break;
            }

            if(Math.abs(sortedA[left+1] + sortedA[right]) <= temp){
                left += 1;
            } else if(Math.abs(sortedA[left] + sortedA[right-1]) <= temp){
                right -= 1;
            } else {
                left += 1;
                right -= 1;
            }
        }

        return minAbs;
    }
}