package com.deepnighttwo.nettylearn.test;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.deepnighttwo.nettylearn.test.handler.DummyChannelDownstreamHandler;
import com.deepnighttwo.nettylearn.test.handler.DummyChannelUpstreamHandler;
import com.deepnighttwo.nettylearn.test.handler.PrintEventChannelHandler;
import com.deepnighttwo.nettylearn.test.handler.SingleBytesCharactorEchoHandler;

public class UncontextHandlerDemoMain {

	public static void main(String[] args) {
		ChannelFactory channelFact = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors.newCachedThreadPool());

		ServerBootstrap bootstrap = new ServerBootstrap(channelFact);

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(DummyChannelDownstreamHandler.INSTANCE,
						DummyChannelUpstreamHandler.INSTANCE, PrintEventChannelHandler.INSTANCE,
						SingleBytesCharactorEchoHandler.INSTANCE);
			}

		});

		bootstrap.setOption("child.tcpNoDelay", "true");
		bootstrap.setOption("child.keepAlive", "true");

		bootstrap.bind(new InetSocketAddress(6363));

	}

}
