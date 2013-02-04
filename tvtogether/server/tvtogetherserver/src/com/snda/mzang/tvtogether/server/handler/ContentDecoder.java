package com.snda.mzang.tvtogether.server.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.json.JSONObject;

import com.snda.mzang.tvtogether.base.B;
import com.snda.mzang.tvtogether.server.protocol.CommPackage;

public class ContentDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		int avaliable = buffer.readableBytes();

		if (avaliable < B.MSG_CLIENT_HEADER_LEN) {
			return null;
		}

		int len = buffer.getInt(buffer.readerIndex());

		if (avaliable >= len + B.MSG_INT_LEN) {
			buffer.readInt();
			String type = readType(buffer);
			byte[] currContent = new byte[len - B.MSG_HANDLER_NAME_LEN];
			buffer.readBytes(currContent);
			CommPackage commPackage = new CommPackage();
			commPackage.type = type;
			commPackage.data = new JSONObject(new String(currContent));
			return commPackage;
		} else {
			return null;
		}
	}

	private static String readType(ChannelBuffer buffer) {
		ChannelBuffer typeBytes = buffer.readBytes(B.MSG_HANDLER_NAME_LEN);
		String typeStr = new String(typeBytes.array());
		return typeStr.trim().toLowerCase();

	}
}
