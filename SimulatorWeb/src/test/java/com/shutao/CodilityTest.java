package com.shutao;

import java.util.*;

/**
 * Created by shutao on 15/11/17.
 */
public class CodilityTest {

    // you can also use imports, for example:
// import java.util.*;

    // you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");
//    public static void main(String[] args) {
//        Sol3 s = new Sol3();
//        // System.out.println(s.solution(new int[]{20,10}));
//
//        System.out.println(s.solution("{[()()]}"));
//
//
//    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Long sum = 0L; // uses Long, not long
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum += i;
        }
        System.out.println(sum);

        System.out.println(System.currentTimeMillis() - start);
    }

}

class Solution2 {
    public int solution(int[] A) {
        // write your code in Java SE 8
        Set<Integer> a = new HashSet<Integer>();
        for (int i = 0; i < A.length; i++) {
            if (a.contains(A[i])) {
                a.remove(A[i]);
            } else {
                a.add(A[i]);
            }
        }

        return (int) a.toArray()[0];
    }
}

class Solution {
    public int solution(int N) {
        // write your code in Java SE 8

        int maxGap = 0;
        boolean start = false;
        int tempGap = 0;
        while (N >= 1) {
            int num = N % 2;
            if (num == 1 && start == false) {
                start = true;
            }

            if (num == 0 && start == true) {
                tempGap++;
            }

            if (num == 1 && start == true) {
                if (tempGap != 0) {
                    if (tempGap > maxGap) {
                        maxGap = tempGap;
                    }
                }
                tempGap = 0;
            }
            N /= 2;
        }

        return maxGap;
    }
}


//class a {
//    int aaa() {
//        int index = 0;
//        int CONTAIN_FLAG = A.length + 2;
//        for (int i = 0; i < A.length; i++) {
//            if (index < A.length) {
//                int value = A[index];
//                if (value < A.length) {
//                    index = A[value - 1];
//                    A[value - 1] = CONTAIN_FLAG;
//                } else {
//                    for (int j = 0; j < A.length; j++) {
//                        if (A[j] < A.length) {
//                            index = j;
//                        }
//                    }
//                }
//            } else {
//                for (int j = 0; j < A.length; j++) {
//                    if (A[j] < A.length) {
//                        index = j;
//                    }
//                }
//            }
//        }
//
//        System.out.println(java.util.Arrays.toString(A));
//
//        for (int i = 0; i < A.length; i++) {
//            if (A[i] != CONTAIN_FLAG) {
//                return i;
//            }
//        }
//        return 0;
//
//
//    }
//
//    public int[] solution(int N, int[] A) {
//        int[] B = new int[N];
//
//        int max = 0;
//        int curMax = 0;
//        boolean hit = false;
//        for (int i = 0; i < A.length; i++) {
//            if (A[i] != N + 1) {
//                if (hit && B[A[i] - 1] < max) {
//                    B[A[i] - 1] = max + 1;
//                } else {
//                    B[A[i] - 1] += 1;
//                }
//
//                // find the max value
//                if (B[A[i] - 1] > curMax) {
//                    curMax = B[A[i] - 1];
//                }
//            } else if (A[i] == N + 1) {
//                hit = true;
//                max = curMax;
//            }
//        }
//
//        if (hit) {
//            for (int i = 0; i < A.length; i++) {
//                if (A[i] < max) {
//                    A[i] = max;
//                }
//            }
//        }
//
//        return B;
//        // https://codility.com/demo/results/trainingP4QB3X-963/
//    }
//
//    public int solution(int A, int B, int K) {
//        // write your code in Java SE 8
//        int base = (B - A) / K;
//
//        if ((B - A) % K == 0) {
//            if (B % K == 0) {
//                base += 2;
//            }
//        } else {
//            if (B % K == 0 || A % K == 0) {
//                base += 1;
//            }
//        }
//
//        return base;
//    }

//    public int solut(int N, int[] A) {
//        // write your code in Java SE 8
//        int[] B = new int[N];
//        int i = A.length;
//        for (; i >= 0; i--) {
//            if (A[i] != N + 1) {
//                B[A[i] - 1] += 1;
//            } else {
//                break;
//            }
//        }
//
//        for (; i >= 0; i--) {
//            if ()
//        }
//
//
//        return
//    }

//
//    public int solution(int[] A) {
//        // write your code in Java SE 8
//        Set set = new HashSet();
//        for ( int i=0;i<A.length;i++) {
//            set.add(A[i]);
//        }
//
//        return set.size();
//    }
//
//
//}

class Sol3 {
    public int solution(String S) {
        // write your code in Java SE 8
        Stack stack = new Stack();
        char[] charArr = S.toCharArray();

        Map open_close_Map = new HashMap<>();
        open_close_Map.put('{', '}');
        open_close_Map.put('[', ']');
        open_close_Map.put('(', ')');

        int middle = charArr.length / 2;
        for (int i = 0; i < charArr.length; i++) {
            System.out.println(charArr[i]);
            if (open_close_Map.containsKey(charArr[i])) {
                stack.push(charArr[i]);
            } else {
                if (stack.size() == 0) {
                    return 0;
                }
                char c = (char) stack.pop();
                char c_close = (char) open_close_Map.get(c);
                if (c_close != charArr[i]) {
                    return 0;
                }
            }
        }
        if (stack.size() != 0) {
            return 0;
        }
        return 1;
    }
}

class Solution5 {
        public int solution(int[] A) {
            // write your code in Java SE 8
            Map numberMap = new HashMap();
            for(int i=0; i<A.length; i++) {
                if(numberMap.containsKey(A[i])) {
                    int val = (int)numberMap.get(A[i]);
                    numberMap.put(A[i], val+1);
                } else {
                    numberMap.put(A[i], 1);
                }
            }

            Iterator<Integer> itr2 = numberMap.keySet().iterator();
            while (itr2.hasNext()) {
                int key = itr2.next();
                if((int)numberMap.get(key) > A.length/2) {
                    for(int i=0; i<A.length; i++) {
                        if(A[i] == key) {
                            return i;
                        }
                    }
                }
            }

            return -1;


        }
    }