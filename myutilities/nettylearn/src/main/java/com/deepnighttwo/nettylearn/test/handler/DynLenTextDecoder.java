package com.deepnighttwo.nettylearn.test.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class DynLenTextDecoder extends FrameDecoder {

	public DynLenTextDecoder() {

	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer)
			throws Exception {

		int len = ((char) (buffer.getByte(buffer.readerIndex()))) - '0';

		int avaliable = buffer.readableBytes();

		if (avaliable >= len + 1) {
			buffer.readByte();
			byte[] currContent = new byte[len];
			buffer.readBytes(currContent);
			String content = new String(currContent);
			return content;
		} else {
			return null;
		}
	}
}
