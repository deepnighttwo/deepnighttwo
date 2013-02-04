package com.snda.mzang.tvtogether.utils.comm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import com.snda.mzang.tvtogether.base.B;
import com.snda.mzang.tvtogether.base.JSONUtil;
import com.snda.mzang.tvtogether.exceptions.CommunicationException;
import com.snda.mzang.tvtogether.exceptions.InvalidatedClientDataException;
import com.snda.mzang.tvtogether.exceptions.InvalidatedServerDataException;
import com.snda.mzang.tvtogether.utils.C;
import com.snda.mzang.tvtogether.utils.UserSession;

public class ServerCommSocket implements IServerComm {

	private InetSocketAddress address;

	public ServerCommSocket(String serviceIp, int serverPort) {
		InetAddress addressHost = null;
		try {
			addressHost = InetAddress.getByName(serviceIp);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		address = new InetSocketAddress(addressHost, serverPort);
	}

	public <T> T sendMsg(JSONObject msg, IContentConverter<T> converter) {

		if (msg == null) {
			throw new InvalidatedClientDataException("Message is null");
		}
		if (msg.has(C.processor) == false) {
			throw new InvalidatedClientDataException("Message has no handler");
		}
		try {
			msg.put(C.userId, UserSession.getUserId());
			msg.put(C.username, UserSession.getUserName());
			msg.put(C.password, UserSession.getPassword());
		} catch (JSONException e1) {
			throw new InvalidatedClientDataException();
		}

		String handlerName = JSONUtil.getString(msg, B.processor);
		byte[] msgData = getClientPkg(msg);

		return this.sendMsg(handlerName, msgData, converter);

	}

	@SuppressWarnings("unchecked")
	public <T> T sendMsg(String handlerName, byte[] message, IContentConverter<T> converter) {
		Socket socket = new Socket();
		DataOutputStream out = null;
		DataInputStream in = null;
		try {
			socket.connect(address);
			out = new DataOutputStream(socket.getOutputStream());
			out.writeInt(message.length);
			out.write(message);
			out.flush();
			in = new DataInputStream(socket.getInputStream());

			while (in.available() < B.MSG_INT_LEN) {
				Thread.sleep(10);
			}
			int len = in.readInt();

			byte[] retData = new byte[len];

			in.read(retData);

			if (converter == null) {
				return (T) retData;
			}

			return converter.convert(retData);
		} catch (IOException e) {
			throw new CommunicationException(e);
		} catch (JSONException e) {
			throw new InvalidatedServerDataException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidatedServerDataException(e);
		} finally {

			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}

		}
	}

	private static byte[] getClientPkg(JSONObject msg) {
		StringBuilder handler = new StringBuilder(JSONUtil.getString(msg, C.processor));
		while (handler.length() < B.MSG_HANDLER_NAME_LEN) {
			handler.append(' ');
		}
		handler.append(msg.toString());
		byte[] msgData = handler.toString().getBytes();
		return msgData;
	}

	public JSONObject sendMsg(JSONObject msg) {

		return sendMsg(msg, C.jsonc);

	}

}
