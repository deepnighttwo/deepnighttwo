package com.snda.mzang.tvtogether.server.handler.processor;

import java.util.Date;

import org.json.JSONObject;

import com.snda.mzang.tvtogether.base.B;
import com.snda.mzang.tvtogether.base.JSONUtil;
import com.snda.mzang.tvtogether.server.dao.UserInfoDao;
import com.snda.mzang.tvtogether.server.entry.UserInfo;
import com.snda.mzang.tvtogether.server.entry.UserInfo.UserStatus;
import com.snda.mzang.tvtogether.server.protocol.IMessageProcessor;
import com.snda.mzang.tvtogether.server.util.EntryId;
import com.snda.mzang.tvtogether.server.util.JSONConverter;

public class Login implements IMessageProcessor {

	UserInfoDao userInfoDao = UserInfoDao.getInstance();

	public String getProcessorName() {
		return B.login;
	}

	public byte[] handle(JSONObject msg) {
		JSONObject ret = new JSONObject();

		String userName = JSONUtil.getString(msg, B.username);
		String password = JSONUtil.getString(msg, B.password);
		boolean regNewUser = JSONUtil.getBoolean(msg, B.regNewUser);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(userName);
		userInfo.setUserPassword(password);
		UserInfo loginUser = userInfoDao.login(userInfo);

		try {
			if (loginUser != null) {
				JSONConverter.convertBeanToJSON(loginUser, ret, null);
				ret.put(B.result, B.success);
			} else if (regNewUser == true) {
				if (userInfoDao.selectUserByName(userName) != null) {
					ret.put(B.result, B.fail);
					ret.put(B.failMsg, "用户名" + userName + "已存在");
				} else {
					String userId = EntryId.USER.getUUID();
					userInfo.setRegisterTime(new Date());
					userInfo.setStatus(UserStatus.ENABLE.getStatus());
					userInfo.setIcon(B.defaultIcon);
					userInfo.setComments("还没填写Comment。");
					userInfo.setFavor("");
					userInfo.setLastLogin(new Date());
					userInfo.setLocationX(0.0);
					userInfo.setLocationY(0.0);
					userInfo.setId(userId);
					boolean retInsert = userInfoDao.insertUser(userInfo);
					if (retInsert == true) {
						JSONConverter.convertBeanToJSON(userInfo, ret, null);
						ret.put(B.result, B.success);
					} else {
						ret.put(B.result, B.fail);
						ret.put(B.failMsg, "注册新用户失败");
					}
				}
			} else {
				ret.put(B.failMsg, "用户名密码错误或用户不存在");
				ret.put(B.result, B.fail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.toString().getBytes();
	}
}