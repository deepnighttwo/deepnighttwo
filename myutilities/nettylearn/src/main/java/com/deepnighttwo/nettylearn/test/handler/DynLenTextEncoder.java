package com.deepnighttwo.nettylearn.test.handler;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class DynLenTextEncoder extends OneToOneEncoder {

	public static final DynLenTextEncoder INSTANCE = new DynLenTextEncoder();

	private DynLenTextEncoder() {

	}

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg)
			throws Exception {
		return null;
	}

}
