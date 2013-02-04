package com.snda.mzang.tvtogether.base;

public interface B {

	/**
	 * 通讯协议相关key
	 * 
	 */
	String result = "result";
	String processor = "processor";
	String userId = "userId";
	String channelId = "channelId";
	String username = "username";
	String password = "password";
	String keepLogin = "keepLogin";
	String regNewUser = "regNewUser";
	String failMsg = "failMsg";
	String channleName = "channelName";
	// 获取频道列表时，channel节点在json中的名字
	String channels = "channels";
	String programmes = "programmes";

	// 获得server resource的时，资源的路径名
	String resPathOnServ = "resPathOnServ";

	/**
	 * 通讯常量
	 */
	String success = "success";
	String fail = "fail";

	/**
	 * 应用常量
	 */

	String defaultIcon = "defaut.png";

	/**
	 * 协议相关
	 */
	// 消息长度，四个字节一个int
	int MSG_INT_LEN = 4;
	// 客户端给服务器端发送的handler名字，27个字节长度的字符
	int MSG_HANDLER_NAME_LEN = 27;

	// 对服务器来说，msg header就是这两个的和，对于客户端来说，就是MSG_INT_LEN
	int MSG_CLIENT_HEADER_LEN = MSG_INT_LEN + MSG_HANDLER_NAME_LEN;

	/**
	 * server processor的名字
	 */
	String login = "login";
	String getChannelList = "getChannelList";
	String getProgrammeList = "getProgrammeList";
	String getServerResource = "getServerResource";

	/**
	 * 数据库相关
	 */
	String DB_NAME = "TVTogetherDB";

	String TB_USER = "UserInfo";

	String col_username = "username";
	String col_password = "password";

	/**
	 * 文件相关
	 */
	String APP_DIR = "tvtogether/";

	String CHANNEL_RES_DIR = APP_DIR + "channelres/";
	String PROGRAMME_RES_DIR = APP_DIR + "programmes/";

}
