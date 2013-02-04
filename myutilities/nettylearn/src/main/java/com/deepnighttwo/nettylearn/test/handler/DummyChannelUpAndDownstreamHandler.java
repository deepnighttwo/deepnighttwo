package com.deepnighttwo.nettylearn.test.handler;

import org.jboss.netty.channel.ChannelDownstreamHandler;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelUpstreamHandler;

public class DummyChannelUpAndDownstreamHandler implements ChannelDownstreamHandler,
		ChannelUpstreamHandler {

	public static final DummyChannelUpAndDownstreamHandler INSTANCE = new DummyChannelUpAndDownstreamHandler();

	private DummyChannelUpAndDownstreamHandler() {

	}

	public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		System.out.println("downstream");
		ctx.sendDownstream(e);
	}

	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		System.out.println("upstream");
		ctx.sendUpstream(e);
	}

}
