package shutao.io.netty.sample.multiplexingclient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Shutao
 */
public class MultiThreadSimulator {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		Map<String, Future<String>> map = new HashMap<String, Future<String>>();

		for (int i = 0; i < 10; i++) {
			final String index = String.valueOf(i);
			Future<String> future = executor.submit(new Callable<String>() {
				public String call() throws Exception {
					System.out.println("Sending request: " + index);
					NettyMultiplexingMessager nettyMultiplexingMessager = new NettyMultiplexingMessager();
					String response = nettyMultiplexingMessager.sendMessage(index);
					return response;
				}
			});
			map.put(index, future);
		}
		executor.shutdown();

		for (String key : map.keySet()) {
			System.out.println(key + ":  " + map.get(key).get());
		}

		while (true) {
			Thread.sleep(1000);
		}
	}
}
