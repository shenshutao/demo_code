package shutao.io.netty.sample.multiplexingclient;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient {
	private Channel channel;
	private Bootstrap bootstrap;
	private Set<String> respSet = new ConcurrentSkipListSet<String>();
	private SimpleNettyClientHandler simpleNettyClientHandler = new SimpleNettyClientHandler(respSet);

	private String host = "localhost";
	private int port = 8001;
	private Long timeoutInMills = 60000L;
	
	protected NettyClient() throws Exception {
		init();
	}

	private void init() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline()
								.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))  // Separate with \r\n in this sample
								.addLast("decoder", new StringDecoder())
								.addLast("encoder", new StringEncoder())
								.addLast("handler", simpleNettyClientHandler);
					}
				});
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		channel = bootstrap.connect(host, port).sync().channel();

		/**
		 * Start house keeping thread, clean all timeout response very 2
		 * minutes.
		 */
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		Runnable houseKeepingThread = () -> {
			for (String msg : respSet) {
				/**
				 * Clean time out msg, can build an class with msg & time.
				 */
				System.out.println("Clean timeout msg which exist more than " + timeoutInMills);
			}
		};
		service.scheduleWithFixedDelay(houseKeepingThread, 2, 2, TimeUnit.MINUTES);
	}

	public Channel getChannel() {
		return this.channel;
	}

	public synchronized void sendRequest(String request) throws Exception {
		if (!this.channel.isActive()) {
			init();
		}
		this.channel.writeAndFlush(request + "\r\n");
	}

	public synchronized Set<String> getResponseSet() {
		return respSet;
	}
}

class SimpleNettyClientHandler extends SimpleChannelInboundHandler<String> {
	public Set<String> respSet;

	public SimpleNettyClientHandler(Set<String> respSet) {
		this.respSet = respSet;
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
		respSet.add(msg);
	}
}
