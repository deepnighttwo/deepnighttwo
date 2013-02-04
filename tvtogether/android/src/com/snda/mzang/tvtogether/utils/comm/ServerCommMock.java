package com.snda.mzang.tvtogether.utils.comm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.snda.mzang.tvtogether.base.JSONUtil;
import com.snda.mzang.tvtogether.exceptions.InvalidatedClientDataException;
import com.snda.mzang.tvtogether.exceptions.InvalidatedServerDataException;
import com.snda.mzang.tvtogether.utils.C;
import com.snda.mzang.tvtogether.utils.UserSession;

public class ServerCommMock implements IServerComm {

	private Map<String, IMessageHandler> handlers = new HashMap<String, IMessageHandler>();

	public ServerCommMock() {
		IMessageHandler handler = new LoginHandlerMockup();
		handlers.put(handler.getHandlerName(), handler);

		handler = new GetChannelListHandlerMockup();
		handlers.put(handler.getHandlerName(), handler);

		handler = new GetServerResourceMockup();
		handlers.put(handler.getHandlerName(), handler);

	}

	public JSONObject sendMsg(JSONObject msg) {
		return sendMsg(msg, C.jsonc);
	}

	public <T> T sendMsg(JSONObject msg, IContentConverter<T> converter) {
		if (msg == null) {
			throw new InvalidatedClientDataException("Message is null");
		}
		if (msg.has(C.processor) == false) {
			throw new InvalidatedClientDataException("Message has no handler");
		}
		try {
			msg.put(C.username, UserSession.getUserName());
			msg.put(C.password, UserSession.getPassword());
		} catch (JSONException e1) {
			throw new InvalidatedClientDataException();
		}
		String handlerName = null;
		try {
			handlerName = (String) msg.get(C.processor);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sendMsg(handlerName, msg.toString().getBytes(), converter);
	}

	@SuppressWarnings("unchecked")
	public <T> T sendMsg(String handlerName, byte[] message, IContentConverter<T> converter) {

		try {
			IMessageHandler handler = handlers.get(handlerName);
			if (handler == null) {
				throw new InvalidatedServerDataException("No handler found for name \"" + handlerName + "\"");
			}
			try {
				// mock waiting :)
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			byte[] serverContent = handler.handle(message);
			if (converter == null) {
				return (T) serverContent;
			}
			return converter.convert(serverContent);
		} catch (JSONException e) {
			throw new InvalidatedClientDataException(e);
		} catch (Exception e) {
			throw new InvalidatedServerDataException(e);
		}
	}

	interface IMessageHandler {

		String getHandlerName();

		byte[] handle(byte[] message);

	}

	abstract class BaseJSONMessageHandler implements IMessageHandler {
		public byte[] handle(byte[] message) {
			String jsonStr = new String(message);
			JSONObject json = null;
			try {
				json = new JSONObject(jsonStr);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return handle(json);
		}

		public abstract byte[] handle(JSONObject message);
	}

	class LoginHandlerMockup extends BaseJSONMessageHandler {

		public String getHandlerName() {
			return C.login;
		}

		public byte[] handle(JSONObject msg) {
			JSONObject ret = new JSONObject();
			try {
				ret.put(C.result, C.success);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return ret.toString().getBytes();
		}

	}

	private static Map<String, byte[]> cache = new HashMap<String, byte[]>();

	private static String[] channelNames = null;

	private static String[] loadChannelInfos(String oneRes) {
		if (channelNames != null) {
			return channelNames;
		}
		try {

			String imageRoot = C.CHANNEL_RES_LOCAL_DIR;

			File dir = new File(imageRoot);
			if (dir.exists() == false || dir.isDirectory() == false) {
				dir.mkdirs();
			}

			URL url = GetChannelListHandlerMockup.class.getClassLoader().getResource(oneRes);

			String path = url.getPath();

			String jarFilePath = path.substring(path.indexOf('/'), path.indexOf('!'));

			List<String> resName = new ArrayList<String>();

			JarFile file = new JarFile(jarFilePath);

			Enumeration<JarEntry> ets = file.entries();

			String imageDir = oneRes.substring(0, oneRes.lastIndexOf('/') + 1);

			while (ets.hasMoreElements() == true) {
				JarEntry jarEntry = ets.nextElement();
				String jarEntryPath = jarEntry.getName();
				if (jarEntryPath.startsWith(imageDir) == false) {
					continue;
				}
				InputStream input = file.getInputStream(jarEntry);
				String imgFileName = jarEntryPath.substring(jarEntryPath.lastIndexOf('/') + 1, jarEntryPath.lastIndexOf('.'));
				resName.add(imgFileName);
				File dataFile = new File(dir, imgFileName);
				if (dataFile.exists() == false || dataFile.isFile() == false) {
					dataFile.createNewFile();
				}
				OutputStream os = new FileOutputStream(dataFile);
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = input.read(buffer)) != -1) {
					os.write(buffer, 0, len);
				}
				input.close();
				os.close();
			}

			channelNames = resName.toArray(new String[0]);

			// BitmapFactory.decodeFile()
			for (int i = 0; i < resName.size(); i++) {
				String filePath = imageRoot + resName.get(i);
				File f = new File(filePath);
				int len = (int) f.length();
				byte[] fileData = new byte[len];
				InputStream input = new FileInputStream(f);
				input.read(fileData);

				cache.put(filePath, fileData);
			}

			return channelNames;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	class GetChannelListHandlerMockup extends BaseJSONMessageHandler {

		public String getHandlerName() {
			return C.getChannelList;
		}

		public byte[] handle(JSONObject msg) {
			JSONObject ret = new JSONObject();
			try {

				String[] channelNames = loadChannelInfos("com/snda/mzang/tvtogether/res/吉林电视台.png");

				ret.put(C.result, C.success);
				JSONArray channels = new JSONArray();
				for (String channelName : channelNames) {
					channels.put(channelName);
				}
				ret.put(C.channels, channels);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return ret.toString().getBytes();
		}
	}

	class GetServerResourceMockup extends BaseJSONMessageHandler {

		public String getHandlerName() {
			return C.getServerResource;
		}

		public byte[] handle(JSONObject msg) {
			String resourceName = JSONUtil.getString(msg, C.resPathOnServ);
			return cache.get(C.sdroot + resourceName);
		}
	}

}
