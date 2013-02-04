package com.deepnighttwo.nettylearn.test.handler;

import org.jboss.netty.channel.ChannelDownstreamHandler;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;

public class DummyChannelDownstreamHandler implements ChannelDownstreamHandler {

	public static final DummyChannelDownstreamHandler INSTANCE = new DummyChannelDownstreamHandler();

	private DummyChannelDownstreamHandler() {

	}

	public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		System.out.println("downstream");
		ctx.sendDownstream(e);
	}

}
