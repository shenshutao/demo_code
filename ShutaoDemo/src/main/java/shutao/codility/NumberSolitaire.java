package shutao.codility;

import java.util.Arrays;

/**
 * Created by shutao on 19/11/17.
 *
 * Question: https://codility.com/programmers/lessons/17-dynamic_programming/number_solitaire/
 *
 * Dynamic programming
 * 将问题分解成依次计算到达每一步的最优值，当前这一步的最优值建立在前面六步的最优值的基础上。
 */
public class NumberSolitaire {
    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
        int[] A = new int[] {1, -2, 0, 9, -1, -2};
        System.out.println(solution2.solution(A));
    }
}

class Solution2 {
    public int solution(int[] A) {
        // write your code in Java SE 8
        int sum = 0;
        int[] optimize = new int[A.length];

        for(int i=0; i<optimize.length; i++) {
            optimize[i] = bestInPrevious6Steps(optimize, i) + A[i];
        }

        //System.out.println(Arrays.toString(optimize));
        return optimize[A.length-1];
    }

    public int bestInPrevious6Steps(int[] optimize, int i) {
        if(i == 0) {
            return 0;
        }
        int start = i - 6 > 0 ? i - 6 : 0;
        int best = Integer.MIN_VALUE;
        for(int a=start; a<i; a++) {
            if(optimize[a] > best) {
                best = optimize[a];
            }
        }

        return best;
    }
}