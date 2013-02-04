package com.deepnighttwo.nettylearn.test.handler;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelUpstreamHandler;

public class DummyChannelUpstreamHandler implements ChannelUpstreamHandler {

	public static final DummyChannelUpstreamHandler INSTANCE = new DummyChannelUpstreamHandler();

	private DummyChannelUpstreamHandler() {

	}

	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		System.out.println("Upstream");
		ctx.sendUpstream(e);
	}

}
