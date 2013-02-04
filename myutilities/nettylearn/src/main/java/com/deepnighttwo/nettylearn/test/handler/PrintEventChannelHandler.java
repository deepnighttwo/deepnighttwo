package com.deepnighttwo.nettylearn.test.handler;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ChildChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.WriteCompletionEvent;

public class PrintEventChannelHandler extends SimpleChannelHandler {

	private PrintEventChannelHandler() {

	}

	public final static PrintEventChannelHandler INSTANCE = new PrintEventChannelHandler();

	private void printCaller() {

		Throwable th = new Throwable();
		StackTraceElement st = th.getStackTrace()[1];
		System.out.println(st.getMethodName());

	}

	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		printCaller();
		super.handleUpstream(ctx, e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		printCaller();
		super.messageReceived(ctx, e);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		printCaller();
		super.exceptionCaught(ctx, e);
	}

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		printCaller();
		super.channelOpen(ctx, e);
	}

	@Override
	public void channelBound(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		printCaller();
		super.channelBound(ctx, e);
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		printCaller();
		super.channelConnected(ctx, e);
	}

	@Override
	public void channelInterestChanged(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		printCaller();
		super.channelInterestChanged(ctx, e);
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		printCaller();
		super.channelDisconnected(ctx, e);
	}

	@Override
	public void channelUnbound(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		printCaller();
		super.channelUnbound(ctx, e);
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		printCaller();
		super.channelClosed(ctx, e);
	}

	@Override
	public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e) throws Exception {
		printCaller();
		super.writeComplete(ctx, e);
	}

	@Override
	public void childChannelOpen(ChannelHandlerContext ctx, ChildChannelStateEvent e)
			throws Exception {
		printCaller();
		super.childChannelOpen(ctx, e);
	}

	@Override
	public void childChannelClosed(ChannelHandlerContext ctx, ChildChannelStateEvent e)
			throws Exception {
		printCaller();
		super.childChannelClosed(ctx, e);
	}

	@Override
	public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		printCaller();
		super.handleDownstream(ctx, e);
	}

	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		printCaller();
		super.writeRequested(ctx, e);
	}

	@Override
	public void bindRequested(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		printCaller();
		super.bindRequested(ctx, e);
	}

	@Override
	public void connectRequested(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		printCaller();
		super.connectRequested(ctx, e);
	}

	@Override
	public void setInterestOpsRequested(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		printCaller();
		super.setInterestOpsRequested(ctx, e);
	}

	@Override
	public void disconnectRequested(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		printCaller();
		super.disconnectRequested(ctx, e);
	}

	@Override
	public void unbindRequested(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		printCaller();
		super.unbindRequested(ctx, e);
	}

	@Override
	public void closeRequested(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		printCaller();
		super.closeRequested(ctx, e);
	}

}
