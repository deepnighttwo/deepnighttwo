package com.snda.mzang.tvtogether.server.dao.test;

import java.util.Date;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

import com.snda.mzang.tvtogether.server.dao.UserInfoDao;
import com.snda.mzang.tvtogether.server.entry.UserInfo;
import com.snda.mzang.tvtogether.server.entry.UserInfo.UserStatus;
import com.snda.mzang.tvtogether.server.util.EntryId;

public class UserInfoDaoTest extends BaseDaoTest {

	public static String id = EntryId.USER.getUUID();

	@Test
	public void testInsert() {

		Properties props = System.getProperties();

		for (Object key : props.keySet().toArray()) {
			System.out.println(key + ":" + props.getProperty((String) key));
		}

		UserInfoDao dao = UserInfoDao.getInstance();
		UserInfo userInfo = new UserInfo();
		userInfo.setId(id);
		userInfo.setUserName("TestUser");
		userInfo.setUserPassword("this is the fucking password.");
		userInfo.setComments("");
		userInfo.setFavor("");
		userInfo.setIcon("1.png");
		userInfo.setLastLogin(new Date());
		userInfo.setLocationX(0.0);
		userInfo.setLocationY(0.0);
		userInfo.setRegisterTime(new Date());
		userInfo.setStatus(UserStatus.ENABLE.getStatus());
		Assert.assertTrue(dao.insertUser(userInfo));
	}

	@Test
	public void testUpdate() {
		UserInfoDao dao = UserInfoDao.getInstance();
		UserInfo userInfo = new UserInfo();
		userInfo.setId(id);
		userInfo.setUserName("TestUse2 - 2");
		userInfo.setUserPassword("this is the fucking password.");
		userInfo.setComments("");
		userInfo.setFavor("");
		userInfo.setIcon("1.png");
		userInfo.setLastLogin(new Date());
		userInfo.setLocationX(0.0);
		userInfo.setLocationY(0.0);
		userInfo.setRegisterTime(new Date());
		userInfo.setStatus(UserStatus.ENABLE.getStatus());
		Assert.assertTrue(dao.updatetUser(userInfo));
	}

	@Test
	public void testLogin() {
		UserInfoDao dao = UserInfoDao.getInstance();
		UserInfo userInfo = new UserInfo();
		userInfo.setId(id);
		userInfo.setUserName("TestUse2 - 2");
		userInfo.setUserPassword("this is the fucking password.");
		Assert.assertNotNull(dao.login(userInfo));
	}

	@Test
	public void testSelectById() {
		UserInfoDao dao = UserInfoDao.getInstance();
		Assert.assertNotNull(dao.selectUserById(id));
	}

	@Test
	public void testSelectByName() {
		UserInfoDao dao = UserInfoDao.getInstance();
		Assert.assertNotNull(dao.selectUserByName("TestUse2 - 2"));
	}

	@Test
	public void testDelete() {
		UserInfoDao dao = UserInfoDao.getInstance();
		boolean ret = dao.deleteUser(id);
		Assert.assertTrue(ret);
	}

}
