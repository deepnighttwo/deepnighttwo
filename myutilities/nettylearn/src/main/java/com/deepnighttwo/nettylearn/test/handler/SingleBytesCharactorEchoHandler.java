package com.deepnighttwo.nettylearn.test.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class SingleBytesCharactorEchoHandler extends SimpleChannelHandler {

	public static final SingleBytesCharactorEchoHandler INSTANCE = new SingleBytesCharactorEchoHandler();

	private SingleBytesCharactorEchoHandler() {

	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Channel channel = e.getChannel();
		StringBuilder recv = new StringBuilder();
		ChannelBuffer buff = (ChannelBuffer) e.getMessage();
		while (buff.readable()) {
			recv.append((char) buff.readByte());
		}
		String recvStr = recv.toString();
		System.out.println("Message Recieved:" + recvStr);
		channel.write(ChannelBuffers.copiedBuffer(recvStr.getBytes()));
		// future.addListener(new ChannelFutureListener() {
		//
		// public void operationComplete(ChannelFuture future) throws Exception
		// {
		// future.getChannel().close();
		// }
		//
		// });
		super.messageReceived(ctx, e);
	}
	// @Override
	// public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
	// throws Exception {
	// super.exceptionCaught(ctx, e);
	// }

}
