package com.snda.mzang.tvtogether.server.dao;

import com.snda.mzang.tvtogether.server.entry.UserInfo;

public class UserInfoDao extends BaseDao {

	private static UserInfoDao INSTANCE = new UserInfoDao();

	private UserInfoDao() {

	}

	public static UserInfoDao getInstance() {
		return INSTANCE;
	}

	public boolean insertUser(UserInfo userInfo) {
		return getSqlSession().insert("userInfo.insertUser", userInfo) == 1;
	}

	public boolean updatetUser(UserInfo userInfo) {
		return getSqlSession().update("userInfo.updateUser", userInfo) == 1;
	}

	public UserInfo login(UserInfo userInfo) {
		return (UserInfo) getSqlSession().selectOne("userInfo.login", userInfo);
	}

	public UserInfo selectUserById(String id) {
		return (UserInfo) getSqlSession().selectOne("userInfo.selectUserById", id);
	}

	public UserInfo selectUserByName(String userName) {
		return (UserInfo) getSqlSession().selectOne("userInfo.selectUserByName", userName);
	}

	public boolean deleteUser(String id) {
		return getSqlSession().delete("userInfo.deleteUser", id) == 1;
	}

}
