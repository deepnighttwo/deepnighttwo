package com.snda.mzang.tvtogether.server.protocol;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.snda.mzang.tvtogether.base.B;
import com.snda.mzang.tvtogether.base.JSONUtil;
import com.snda.mzang.tvtogether.server.exception.InvalidatedServerDataException;
import com.snda.mzang.tvtogether.server.handler.processor.GetChannelList;
import com.snda.mzang.tvtogether.server.handler.processor.GetProgrammeList;
import com.snda.mzang.tvtogether.server.handler.processor.GetServerResource;
import com.snda.mzang.tvtogether.server.handler.processor.Login;
import com.snda.mzang.tvtogether.server.helper.UserHelper;
import com.snda.mzang.tvtogether.server.log.L;

public class CommPackageProcessor {

	private static Map<String, IMessageProcessor> processors = new HashMap<String, IMessageProcessor>();

	private static byte[] USER_VALIDATION_FAILED;

	static {
		JSONObject failJson = new JSONObject();
		try {
			failJson.put(B.result, B.fail);
			failJson.put(B.failMsg, "用户验证失败");
		} catch (JSONException e) {
		}
		USER_VALIDATION_FAILED = failJson.toString().getBytes();
	}

	public CommPackageProcessor() {
		IMessageProcessor handler = new Login();
		processors.put(handler.getProcessorName().toLowerCase(), handler);

		handler = new GetChannelList();
		processors.put(handler.getProcessorName().toLowerCase(), handler);

		handler = new GetProgrammeList();
		processors.put(handler.getProcessorName().toLowerCase(), handler);

		handler = new GetServerResource();
		processors.put(handler.getProcessorName().toLowerCase(), handler);

	}

	public byte[] process(CommPackage commPkg) {

		if (commPkg == null) {
			L.error("No message.");
			return new byte[0];
		}
		if (StringUtils.isEmpty(commPkg.type) == true) {
			return "No hanlder".getBytes();
		}

		try {
			String handlerName = commPkg.type;
			IMessageProcessor processor = processors.get(handlerName);
			if (processor == null) {
				throw new InvalidatedServerDataException("No handler found for name \"" + handlerName + "\"");
			}

			if (processor instanceof IValidationProcessor) {
				if (doLoginValidation(JSONUtil.getString(commPkg.data, B.username), JSONUtil.getString(commPkg.data, B.password)) == false) {
					return USER_VALIDATION_FAILED;
				}
			}
			byte[] serverContent = processor.handle(commPkg.data);
			return serverContent;

		} catch (Exception e) {
			e.printStackTrace();
			return new byte[0];
		}

	}

	private static boolean doLoginValidation(String userName, String password) {
		return UserHelper.checkLogin(userName, password);
	}
	//
	// public static Map<String, byte[]> cache = new HashMap<String, byte[]>();
	//
	// public static String[] channelNames = null;
	//
	// public static String[] loadChannelInfos() {
	// if (channelNames != null) {
	// return channelNames;
	// }
	// try {
	//
	// String imageRoot = SC.resBase + B.CHANNEL_RES_DIR;
	//
	// File dir = new File(imageRoot);
	// if (dir.exists() == false || dir.isDirectory() == false) {
	// dir.mkdirs();
	// }
	//
	// File[] files = dir.listFiles();
	//
	// List<String> resNamesList = new ArrayList<String>();
	//
	// for (File dataFile : files) {
	// InputStream input = new FileInputStream(dataFile);
	// byte[] fileData = new byte[(int) dataFile.length()];
	// input.read(fileData);
	// String fileName = dataFile.getName();
	// int last = fileName.lastIndexOf('.') > 0 ? fileName.lastIndexOf('.') :
	// fileName.length();
	// String resName = fileName.substring(0, last);
	// resNamesList.add(resName);
	// cache.put(SC.resBase + B.CHANNEL_RES_DIR + resName, fileData);
	// input.close();
	// }
	// channelNames = resNamesList.toArray(new String[0]);
	// return channelNames;
	// } catch (IOException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }

}
