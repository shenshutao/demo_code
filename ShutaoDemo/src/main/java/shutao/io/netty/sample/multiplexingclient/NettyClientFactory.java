package shutao.io.netty.sample.multiplexingclient;

public class NettyClientFactory {

	private static NettyClient nettyClient = null;

	private NettyClientFactory() {
		// defeat instantiation.
	}

	public synchronized static NettyClient getInstance() {
		if (nettyClient == null) {
			try {
				nettyClient = new NettyClient();
			} catch (Exception e) {
				return null;
			}
		}
		return nettyClient;
	}
}
