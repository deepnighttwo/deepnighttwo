package com.snda.mzang.tvtogether.server.dao;

import java.util.List;

import com.snda.mzang.tvtogether.server.entry.Channel;

public class ChannelDao extends BaseDao {

	private static ChannelDao INSTANCE = new ChannelDao();

	private ChannelDao() {

	}

	public static ChannelDao getInstance() {
		return INSTANCE;
	}

	public boolean insertChannel(Channel channel) {
		return getSqlSession().insert("channel.insertChannel", channel) == 1;
	}

	public boolean updatetChannel(Channel channel) {
		return getSqlSession().update("channel.updateChannel", channel) == 1;
	}

	@SuppressWarnings("unchecked")
	public List<Channel> selectAllChannels() {
		return (List<Channel>) getSqlSession().selectList("channel.selectAllChannels");
	}

	public boolean deleteChannel(String id) {
		return getSqlSession().delete("channel.deleteChannel", id) == 1;
	}

}
