package com.deepnighttwo.nioclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

import com.deepnighttwo.nioserver.NIOServer;

public class NIOClient {

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	ByteBuffer bb = ByteBuffer.allocate(1024);
	Selector s;
	SocketChannel sc;

	public NIOClient() {
		Thread th = new Thread() {
			public void run() {
				try {
					startClient();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		th.start();
	}

	public void startClient() throws Exception {
		sc = SocketChannel.open();
		sc.configureBlocking(true);
		sc.connect(new InetSocketAddress(NIOServer.PORT));

		if (sc.isConnectionPending()) {
			sc.finishConnect();
		}
		log("Client Connected!");

		Thread th = new Thread() {

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(sc.socket()
					.getOutputStream()));

			public void run() {
				while (true) {
					try {
						String line = br.readLine();
						pw.write(line);
						pw.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		};
		th.start();
		// while (true) {
		// if (s.select() <= 0) {
		// continue;
		// }
		// Set<SelectionKey> keys = s.selectedKeys();
		//
		// for (SelectionKey key : keys) {
		// if (key.isWritable()) {
		// String content = br.readLine();
		// bb.clear();
		// log(bb);
		// bb.put(content.getBytes());
		// sc.write(bb);
		// }
		// }
		// }

	}

	public void log(Object obj) {
		System.out.println(obj);
	}

	public static void main(String[] args) {
		new NIOClient();
	}
}
