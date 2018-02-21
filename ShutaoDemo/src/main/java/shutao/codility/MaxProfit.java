package shutao.codility;

/**
 * Created by shutao on 19/11/17.
 */
public class MaxProfit {
    public static void main(String[] args) {
        Solution9 solution9 = new Solution9();
        System.out.println(solution9.solution(new int[] {23171, 21011, 21123, 21366, 21013, 21367}));
        System.out.println(solution9.solution(new int[] {8, 9, 3, 6, 1, 2}));
    }
}

class Solution9 {
    public int solution(int[] A) {
        int max_ending = 0, max_slice = 0;

        for(int i=1; i<A.length; i++) {
            if(max_ending + A[i] - A[i - 1] > 0 ) {
                max_ending =  max_ending + A[i] - A[i - 1];
            } else {
                max_ending = 0; // 如果A[i]比当前值先，移动到A[i]处再计算。
            }

            System.out.println(max_ending + " " + max_slice);
            if( max_ending > max_slice ) {
                max_slice = max_ending;
            }
        }

        return max_slice;
    }
}