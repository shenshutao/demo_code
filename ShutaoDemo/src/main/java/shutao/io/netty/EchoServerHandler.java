package shutao.io.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class EchoServerHandler extends SimpleChannelInboundHandler<String> {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		System.out.println(cause.getMessage());
		ctx.close();
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("Server received: " + msg);
		ctx.channel().writeAndFlush("Sever received " + msg + "\r\n");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { 
		Channel incoming = ctx.channel();
		System.out.println("Echo Client:" + incoming.remoteAddress() + " connected");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { 
		Channel incoming = ctx.channel();
		System.out.println("Echo Client:" + incoming.remoteAddress() + " disconnected");
	}
}