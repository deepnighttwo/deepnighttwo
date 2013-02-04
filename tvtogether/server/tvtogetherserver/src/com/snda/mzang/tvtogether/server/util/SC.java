package com.snda.mzang.tvtogether.server.util;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public interface SC {

	String resBase = "/root/mymise/target/";
	// String resBase = "G:\\My Dropbox\\电视台台标\\target\\";

	SqlSessionFactoryBuilder sqlBuilder = new SqlSessionFactoryBuilder();
	SqlSessionFactory sqlFactory = sqlBuilder.build(SC.class.getClassLoader().getResourceAsStream("mybatis-config.xml"));

}
