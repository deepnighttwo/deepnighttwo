package socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ReversServer {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(9978);
		while (true) {
			Socket s = ss.accept();
			new ReversServerHandler(s).start();
		}
	}

}

class ReversServerHandler extends Thread {

	private static final String EXIT_CURR = "exit";

	private static final String EXIT_ALL = "bye";

	private Socket s;

	ReversServerHandler(Socket s) {
		this.s = s;
	}

	public void run() {

		int port = s.getPort();
		System.out.println("Connected to port " + port);
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			InputStream in = s.getInputStream();
			OutputStream out = s.getOutputStream();
			br = new BufferedReader(new InputStreamReader(in));
			pw = new PrintWriter(new OutputStreamWriter(out));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("Recieved Message from Port \"" + port
						+ "\" :" + line);

				if (EXIT_CURR.equals(line)) {

					System.out.println("Get exit command from client."
							+ " Exiting current thread.");
					pw.write(EXIT_CURR + "\r\n");
					pw.flush();
					break;
				}
				if (EXIT_ALL.equals(line)) {
					// the command sender can exit correctly;
					pw.write(EXIT_CURR + "\r\n");
					pw.flush();
					System.out.println("Get exit ALL command from client."
							+ " Exiting Server.");
					System.exit(0);
				}
				StringBuilder sb = new StringBuilder(line);

				pw.write(sb.reverse().toString() + "\r\n");
				pw.flush();
			}
			if (line == null) {
				System.out.println("No Content from client, exiting");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
		}
	}
}
