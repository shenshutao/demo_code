package shutao.io.netty.sample.chat;

import java.io.IOException;
import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class SimpleChatClient {
	private final String host;
	private final int port;
	public Channel channel;

	public SimpleChatClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try (Scanner scan = new Scanner(System.in)) {
			/**
			 * Init channel
			 */
			Bootstrap bootstrap = new Bootstrap()
					.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();

							pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
							pipeline.addLast("decoder", new StringDecoder());
							pipeline.addLast("encoder", new StringEncoder());
							pipeline.addLast("handler", new SimpleChatClientHandler());
						}
					});
			this.channel = bootstrap.connect(host, port).sync().channel();

			while (scan.hasNextLine()) {
				String msg = scan.nextLine();
				sendMsg(msg + "\r\n");  // Use \r\n to separate the message.
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}

	public void sendMsg(String message) throws IOException {
		this.channel.writeAndFlush(message);
	}

	public static void main(String[] args) throws Exception {
		new SimpleChatClient("localhost", 8080).start();
	}
}

class SimpleChatClientHandler extends  SimpleChannelInboundHandler<String> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String s) throws Exception {
        System.out.println(s);
    }
}