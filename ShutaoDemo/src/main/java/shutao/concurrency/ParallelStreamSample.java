package shutao.concurrency;

import java.util.Arrays;

/**
 * Created by shutao on 18/11/17.
 */
public class ParallelStreamSample {
    public static void main(String[] args) {
        Arrays.asList("jackal", "kangaroo", "lemur").parallelStream()
                .map(s -> s.toUpperCase()).forEach(System.out::println);

        // always 1
        System.out.println(Arrays.asList(1,2,3,4,5,6).stream().findAny().get());

        // random
        System.out.println(Arrays.asList(1,2,3,4,5,6).parallelStream().findAny().get());

    }
}
