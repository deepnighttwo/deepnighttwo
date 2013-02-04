package com.snda.mzang.tvtogether.utils.res;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONObject;

import com.snda.mzang.tvtogether.utils.C;
import com.snda.mzang.tvtogether.utils.comm.IContentConverter;

public class ResUtil {

	public static <T> T getResAs(String resFullPath, IContentConverter<T> converter) throws Exception {
		return getResAs(resFullPath, converter, true);
	}

	public static <T> T getResAs(String resFullPath, IContentConverter<T> converter, boolean useLocalCache) throws Exception {
		File resFile = new File(C.sdroot + resFullPath);
		if (resFile.isFile() == false || useLocalCache == false || resFile.length() == 0) {
			resFile.createNewFile();
			JSONObject reqServerRes = new JSONObject();
			reqServerRes.put(C.processor, C.getServerResource);
			reqServerRes.put(C.resPathOnServ, resFullPath);
			byte[] content = C.comm.sendMsg(reqServerRes, null);
			OutputStream out = new FileOutputStream(resFile);
			out.write(content);
			out.flush();
			out.close();
			if (converter == null) {
				return null;
			} else {
				return converter.convert(content);
			}
		}
		if (converter == null) {
			return null;
		} else {
			byte[] content = new byte[(int) resFile.length()];
			InputStream input = new FileInputStream(resFile);
			input.read(content);
			input.close();
			return converter.convert(content);
		}
	}

}
