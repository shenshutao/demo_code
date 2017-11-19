package shutao.concurrency;

import java.util.concurrent.*;

/**
 * Created by shutao on 18/11/17.
 */
public class ThreadSamples {

    public static void main(String[] args) {
        // 1
        RunnableSample runnableSample = new RunnableSample(0.05);
        runnableSample.run();

        // 2
        Runnable r = () -> System.out.println("Hello World");
        r.run();

        Callable<String> c = () -> {
            System.out.println("Hello World");
            return "Haha";
        };
        try {
            String s = c.call();
            System.out.println(s);
        } catch (Exception e) {
        }

        // 3
        ThreadSample threadSample = new ThreadSample(0.10);
        threadSample.run();

        // 4
        ExecutorService service = null;
        long start = System.currentTimeMillis();
        try {
            /*
            Creates a single-threaded executor that uses a single worker thread operating off an unbounded queue.
            Results are processed sequentially in the order in which they are submitted.
             */
            //service = Executors.newSingleThreadExecutor();

            /*
             Unbounded size.
             Commonly used for pools that require executing many short-lived asynchronous tasks.
             For long-lived processes, usage of this executor is strongly discouraged, as it could grow to encompass a large number of threads over the application life cycle.
             */
            // service = Executors.newCachedThreadPool();

            /*
            Most Commonly used.
            Creates a thread pool that reuses a fixed number of threads operating off a shared unbounded queue.
             */
            service = Executors.newFixedThreadPool(2);

            System.out.println("begin");
            service.execute(() -> System.out.println("Printing zoo inventory 1"));
            Runnable runnable = () -> {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
                System.out.println("Runnable Thread 1");
            };
            service.execute(runnable);

            try {
                Callable callable = () -> {
                    Thread.sleep(1000);

                    return "Callable Thread 2";
                };
                Future<?> future = service.submit(callable);
                System.out.println("Result: " + future.get(100, TimeUnit.NANOSECONDS));
            } catch (Exception e) {
                e.printStackTrace();
            }

//            // Runnable doesn't support checked exception
//            service.submit(() -> {
//                Thread.sleep(1000);
//            });
            // Callable support checked exception
            service.submit(() -> {
                Thread.sleep(1000);
                return null;
            });

            service.execute(() -> System.out.println("Printing zoo inventory 2"));
            System.out.println("end");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (service != null) service.shutdown();
        }
        System.out.println(service.isShutdown());
        System.out.println(service.isTerminated());

        try {
            service.awaitTermination(10, TimeUnit.MINUTES);
            System.out.println(service.isShutdown());
            System.out.println(service.isTerminated());
            if (service.isTerminated())
                System.out.println("All tasks finished");
            else
                System.out.println("At least one task is still running");
        } catch (Exception e) {
            // Do Nothing
        }
        System.out.println("Used time: " + (System.currentTimeMillis() - start));

        // 5
        /**
         * Creates a single-threaded executor that uses a single worker thread operating off an unbounded queue.
         * Results are processed sequentially in the order in which they are submitted.
         */
        Runnable taskA = () -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            System.out.println("Task A");
        };
        ScheduledExecutorService service2 = Executors.newSingleThreadScheduledExecutor();
        service2.scheduleAtFixedRate(taskA, 3, 5, TimeUnit.SECONDS);
        service2.scheduleAtFixedRate(taskA, 3, 5, TimeUnit.SECONDS);
        service2.scheduleAtFixedRate(taskA, 3, 5, TimeUnit.SECONDS);

        /**
         * Creates a thread pool that can schedule commands to run after a given delay or to execute periodically.
         */
        Runnable taskB = () -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            System.out.println("Task B");
        };
        service2 = Executors.newScheduledThreadPool(10);
        service2.scheduleAtFixedRate(taskB, 3, 2, TimeUnit.SECONDS);
        service2.scheduleAtFixedRate(taskB, 3, 2, TimeUnit.SECONDS);
        service2.scheduleAtFixedRate(taskB, 3, 2, TimeUnit.SECONDS);

    }

}


class RunnableSample implements Runnable {
    private double scores;

    public RunnableSample(double scores) {
        this.scores = scores;
    }

    @Override
    public void run() {
        System.out.println(scores);
    }
}

class ThreadSample extends Thread {
    private double scores;

    public ThreadSample(double scores) {
        this.scores = scores;
    }

    @Override
    public void run() {
        System.out.println(scores);
    }
}
