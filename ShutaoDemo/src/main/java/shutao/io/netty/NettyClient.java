package shutao.io.netty;

import java.net.InetSocketAddress;
import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient {
	private final String host;
	private final int port;
	private EventLoopGroup group;
	private Channel channel;

	public NettyClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start() throws Exception {
		group = new NioEventLoopGroup();
			Bootstrap b = new Bootstrap(); 
			b.group(group) 
					.channel(NioSocketChannel.class) 
					.remoteAddress(new InetSocketAddress(host, port)) 
					.handler(new ChannelInitializer<SocketChannel>() { 
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();

							pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
							pipeline.addLast("decoder", new StringDecoder());
							pipeline.addLast("encoder", new StringEncoder());
							pipeline.addLast(new EchoClientHandler());
						}
					});

			ChannelFuture f = b.connect().sync(); 
			f.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
						System.out.println("Connect to server successfully");
					}
				}
			});
			this.channel = f.channel();
	}

	public static void main(String[] args) {
		final String host = "localhost";
		final int port = 8000;
		NettyClient nettyClient = new NettyClient(host, port);;
		
		try (Scanner scan = new Scanner(System.in)) {
			nettyClient.start();
			while (scan.hasNextLine()) {
				String msg = scan.nextLine();
				nettyClient.channel.writeAndFlush(msg + "\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			nettyClient.group.shutdownGracefully(); 
		}
	}
}
