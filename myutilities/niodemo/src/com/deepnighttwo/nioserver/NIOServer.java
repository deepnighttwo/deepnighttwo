package com.deepnighttwo.nioserver;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NIOServer {

	public static final int PORT = 19191;

	ByteBuffer bb = ByteBuffer.allocate(1024);

	public NIOServer() {
		Thread th = new Thread() {
			public void run() {
				try {
					startServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		th.start();
	}

	public void startServer() throws Exception {

		Selector s = Selector.open();

		ServerSocketChannel ssc = ServerSocketChannel.open();

		ssc.configureBlocking(false);

		ssc.register(s, SelectionKey.OP_ACCEPT);

		ssc.socket().bind(new InetSocketAddress(PORT));

		log("System initialized");
		while (true) {
			if (s.select() <= 0) {
				continue;
			}

			Set<SelectionKey> keys = s.selectedKeys();

			for (SelectionKey key : keys) {
				if (key.isAcceptable() == true) {
					SocketChannel sc = ssc.accept();
					sc.configureBlocking(false);
					log("Connected from client: "
							+ sc.socket().getRemoteSocketAddress());
					sc.register(s, SelectionKey.OP_READ);
				}

				if (key.isReadable()) {
					bb.clear();
					SocketChannel sc = (SocketChannel) key.channel();
					sc.read(bb);
					bb.flip();
					byte[] data = new byte[bb.remaining()];
					bb.get(data);
					String msg = new String(data);
					log("Message from client: " + msg);
				}
			}

			keys.clear();
		}

	}

	public void log(Object obj) {
		System.out.println(obj);
	}

	public static void main(String[] args) {
		new NIOServer();
	}

}
