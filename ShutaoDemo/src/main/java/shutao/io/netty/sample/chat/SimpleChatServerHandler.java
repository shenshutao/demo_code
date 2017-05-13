package shutao.io.netty.sample.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> { 
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception { 
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " Join\n");
		}
		channels.add(ctx.channel());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { 
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " Left\n");
		}
		channels.remove(ctx.channel());
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String s) throws Exception { 
		Channel incoming = ctx.channel();
		System.out.println("Get msg: [" + incoming.remoteAddress() + "]" + s + "\n");

		/**
		 * Broadcast to all channels. 
		 */
		for (Channel channel : channels) {
			if (channel != incoming) {
				channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");
			} else {
				channel.writeAndFlush("[you]" + s + "\n");
			}
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { 
		Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient:" + incoming.remoteAddress() + " on line");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { 
		Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient:" + incoming.remoteAddress() + " off line");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) { 
		Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient:" + incoming.remoteAddress() + " exception");
		// Close the connection when exception occurs.
		System.out.println(e.getMessage());
		ctx.close();
	}
}