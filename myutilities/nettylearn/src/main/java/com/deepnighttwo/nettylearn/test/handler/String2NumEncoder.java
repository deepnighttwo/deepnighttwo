package com.deepnighttwo.nettylearn.test.handler;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class String2NumEncoder extends OneToOneEncoder {

	public static final String2NumEncoder INSTANCE = new String2NumEncoder();

	private String2NumEncoder() {

	}

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg)
			throws Exception {
		if ((msg instanceof String) == false) {
			return msg;
		}

		String str = (String) msg;
		StringBuilder str2ascii = new StringBuilder(str.length() * 6 + 5);

		int len = str.length();

		for (int i = 0; i < len; i++) {
			str2ascii.append(' ');
			char ch2int = str.charAt(i);
			str2ascii.append(ch2int);
			str2ascii.append(':');
			str2ascii.append(String.valueOf((int) ch2int));
		}

		return ChannelBuffers.copiedBuffer(str2ascii.toString().getBytes());
	}

}
