package shutao.io.netty.sample.multiplexingclient;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

public class NettyMultiplexingMessager {
	private NettyClient nettyClient;
	private Long timeoutInMills = 60000L;

	public String sendMessage(String request) throws Exception {
		nettyClient = NettyClientFactory.getInstance(); // In Spring, you can use scope = 'singleton'
		nettyClient.sendRequest(request);
		System.out.println("Send out request: " + request);

		LocalDateTime start = LocalDateTime.now();
		while (true) {
			System.out.println("waiting for the result for " + request);
			Set<String> responseSet = nettyClient.getResponseSet();
			for (String resp : responseSet) {
				if (resp.contains(request)) { // In this sample, use contains to map request & response.
					responseSet.remove(resp);
					return resp;
				}
			}

			// Check timeout, throw TimeoutException.
			LocalDateTime now = LocalDateTime.now();
			Duration p = Duration.between(start, now);
			if (p.toMillis() > timeoutInMills) {
				throw new IOException("Time Out");
			}
			Thread.sleep(300);
		}
	}
}
