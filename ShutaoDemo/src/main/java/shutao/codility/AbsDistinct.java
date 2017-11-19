package shutao.codility;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by shutao on 19/11/17.
 *
 * https://codility.com/programmers/lessons/15-caterpillar_method/abs_distinct/
 */
public class AbsDistinct {
    public static void main(String[] args) {
        Solution6 solution6 = new Solution6();
        int[] A = new int[] {-5, -3, -1, 0, 3, 6};
        System.out.println(solution6.solution(A));
    }
}

class Solution6 {
    public int solution(int[] A) {
        // write your code in Java SE 8
        Set set = new HashSet<>();

        for(int i=0; i<A.length; i++) {
            set.add(Math.abs(A[i]));
        }

        return set.size();
    }
}