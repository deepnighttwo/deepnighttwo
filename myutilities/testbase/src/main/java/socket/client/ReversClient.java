package socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ReversClient {

	private static final String EXIT_CURR = "exit";

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		Socket s = new Socket("127.0.0.1", 9978);
		BufferedReader br = null;
		BufferedReader userInput = null;
		PrintWriter pw = null;
		try {
			InputStream in = s.getInputStream();
			OutputStream out = s.getOutputStream();
			br = new BufferedReader(new InputStreamReader(in));
			userInput = new BufferedReader(new InputStreamReader(System.in));
			pw = new PrintWriter(new OutputStreamWriter(out));
			String line = null;
			while ((line = userInput.readLine()) != null) {
				pw.write(line + "\r\n");
				pw.flush();
				String serverback = br.readLine();
				if (EXIT_CURR.equals(serverback)) {
					System.out.println("Get exit command from server."
							+ " Exiting current thread.");
					break;
				}

				System.out
						.println("Recieved Message From Server:" + serverback);
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
			if (userInput != null) {
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
