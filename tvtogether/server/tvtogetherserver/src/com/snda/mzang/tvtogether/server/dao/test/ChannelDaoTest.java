package com.snda.mzang.tvtogether.server.dao.test;

import java.io.File;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.snda.mzang.tvtogether.base.B;
import com.snda.mzang.tvtogether.server.dao.ChannelDao;
import com.snda.mzang.tvtogether.server.entry.Channel;
import com.snda.mzang.tvtogether.server.entry.Channel.ChannelStatus;
import com.snda.mzang.tvtogether.server.util.EntryId;
import com.snda.mzang.tvtogether.server.util.SC;

public class ChannelDaoTest extends BaseDaoTest {

	@Test
	public void testInsert() {

		ChannelDao dao = ChannelDao.getInstance();

		String imageRoot = SC.resBase + B.CHANNEL_RES_DIR;

		File dir = new File(imageRoot);

		File[] files = dir.listFiles();

		for (File dataFile : files) {
			String id = EntryId.CHANNEL.getUUID();
			Channel channel = new Channel();
			channel.setComments("test");
			channel.setId(id);
			channel.setStatus(ChannelStatus.ENABLE.getStatus());
			channel.setUpdateTime(new Date());

			String fileName = dataFile.getName();
			channel.setImage(fileName);

			int last = fileName.lastIndexOf('.') > 0 ? fileName.lastIndexOf('.') : fileName.length();
			String resName = fileName.substring(0, last);
			channel.setName(resName);

			Assert.assertTrue(dao.insertChannel(channel));
		}
	}

	@Test
	public void testSelect() {
		ChannelDao dao = ChannelDao.getInstance();
		Assert.assertNotNull(dao.selectAllChannels());
	}

}
