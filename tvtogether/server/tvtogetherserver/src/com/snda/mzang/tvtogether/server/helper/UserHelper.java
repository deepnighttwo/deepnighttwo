package com.snda.mzang.tvtogether.server.helper;

import com.snda.mzang.tvtogether.server.dao.UserInfoDao;
import com.snda.mzang.tvtogether.server.entry.UserInfo;

public class UserHelper {

	private static UserInfoDao userInfoDao = UserInfoDao.getInstance();

	public static boolean checkLogin(String userName, String password) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(userName);
		userInfo.setUserPassword(password);
		return userInfoDao.login(userInfo) != null;
	}
}
