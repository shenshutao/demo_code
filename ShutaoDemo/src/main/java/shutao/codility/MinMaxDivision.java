package shutao.codility;

/**
 * Created by shutao on 19/11/17.
 */
public class MinMaxDivision {
    public static void main(String[] args) {
        Solution solution = new Solution();

        int K = 3;
        int M = 5;
        int[] A = new int[]{2, 1, 5, 1, 2, 2, 2 };

        System.out.println(solution.solution(K,M,A));
    }
}

/**
 * Solution Class
 */
class Solution {
    public int solution(int K, int M, int[] A) {
        int min = 0;
        int max = 0;
        for (int i = 0; i < A.length; i++) {
            max += A[i];
        }

        int mid = 0;
        int result = 0;
        while (min < max) {
            mid = (min + max) / 2;

            if (possibleDivide(mid, K, A )) {
                max = mid; // try smaller limit
            } else {
                min = mid + 1; // try greater limit
            }

            if(min == max) { // end with condition, min == max
                result = max;
            }
        }

        return result;
    }

    /**
     * Decide if it is a possible
     * To divide into K groups, every group's sum less than limit.
     *
     * @param limit every group's sum less than limit
     * @param K   divide to K groups
     * @param A   int array
     * @return possible or not
     */
    public boolean possibleDivide(int limit, int K, int[] A) {
        int tempSum = 0;
        int tempK = 0;
        // divide by every sum < limit
        for (int i = 0; i < A.length; i++) {
            if (A[i] > limit) {
                return false;
            }

            tempSum += A[i];
            if (tempSum > limit) {
                tempK += 1;
                tempSum = A[i];
            }
        }
        if(tempSum > 0) {
            tempK += 1;
        }

        // if more group need, not possible.
        if (tempK > K) {
            return false;
        }

        return true;
    }
}