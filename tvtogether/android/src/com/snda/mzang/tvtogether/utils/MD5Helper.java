package com.snda.mzang.tvtogether.utils;

import org.apache.commons.lang.StringUtils;

public class MD5Helper {
	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String getMD5(String content) {
		if (StringUtils.isEmpty(content)) {
			return "";
		}
		StringBuilder ret = new StringBuilder(16 * 2);

		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(content.getBytes("UTF-8"));
			// 结果为128位的整数，就是16×8个字节。
			byte tmp[] = md.digest();
			// 每个字节用两个字符表示
			// 代表已经填充到的位
			for (int i = 0; i < 16; i++) {
				byte data = tmp[i];
				// 逻辑右移动四位，表示前四个字节
				ret.append(hexDigits[data >>> 4 & 0xf]);
				// 抹去前四个字节，代表后四个字节
				ret.append(hexDigits[data & 0xf]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.toString();
	}
}
