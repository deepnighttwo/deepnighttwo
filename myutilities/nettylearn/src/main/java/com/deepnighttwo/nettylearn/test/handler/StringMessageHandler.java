package com.deepnighttwo.nettylearn.test.handler;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class StringMessageHandler extends SimpleChannelHandler {

	public static final StringMessageHandler INSTANCE = new StringMessageHandler();

	private StringMessageHandler() {

	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Channel channel = e.getChannel();
		String msg = (String) e.getMessage();
		System.out.println("Message Recieved:" + msg);
		ChannelFuture future = channel.write(msg);
		if (msg.length() == 0) {
			future.addListener(new ChannelFutureListener() {

				public void operationComplete(ChannelFuture future) throws Exception {
					future.getChannel().close();
				}

			});
		}
		super.messageReceived(ctx, e);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		ctx.getChannel().close();
	}

}
