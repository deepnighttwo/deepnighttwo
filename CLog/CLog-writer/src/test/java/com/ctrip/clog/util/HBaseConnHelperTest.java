package com.ctrip.clog.util;


import org.apache.hadoop.hbase.client.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * User: mzang
 * Date: 11/11/13
 * Time: 3:23 PM
 */
public class HBaseConnHelperTest {

    @Test
    public void testHBaseConn() throws IOException {
        HTablePool hTablePool = CLogHBaseConnHelper.getHTablePool();
        HTableInterface htable = hTablePool.getTable("clog.test");
        Put put = new Put("testdata".getBytes());
        put.add("data".getBytes(), "test1".getBytes(), "testdata1".getBytes());
        htable.put(put);
        htable.close();

        htable = hTablePool.getTable("clog.test");
        Get get = new Get("testdata".getBytes());
        get.addColumn("data".getBytes(), "test1".getBytes());
        Result result = htable.get(get);
        String value = new String(result.getValue("data".getBytes(), "test1".getBytes()));

        Assert.assertEquals("testdata1", value);
    }
}
