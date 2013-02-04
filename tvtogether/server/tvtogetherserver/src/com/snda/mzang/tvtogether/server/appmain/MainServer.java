package com.snda.mzang.tvtogether.server.appmain;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.snda.mzang.tvtogether.server.handler.ContentDecoder;
import com.snda.mzang.tvtogether.server.handler.RequestHandler;

public class MainServer {

	public static void main(String[] args) {
		start();
	}

	public static void start() {
		ChannelFactory channelFact = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());

		ServerBootstrap bootstrap = new ServerBootstrap(channelFact);

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new ContentDecoder(), RequestHandler.INSTANCE);
			}
			// DummyChannelUpAndDownstreamHandler.INSTANCE
		});

		bootstrap.setOption("child.tcpNoDelay", "true");
		bootstrap.setOption("child.keepAlive", "true");

		bootstrap.bind(new InetSocketAddress(17171));

	}

}
