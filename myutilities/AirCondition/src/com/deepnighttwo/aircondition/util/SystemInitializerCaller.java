package com.deepnighttwo.aircondition.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SystemInitializerCaller implements ServletContextListener {

	private static final Logger log = Logger
			.getLogger(SystemInitializerCaller.class.getName());

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		Properties props = new Properties();
		try {
			props.load(SystemInitializerCaller.class.getClassLoader()
					.getResourceAsStream("accondition.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			log.severe("System Initializer can not load accondition.properties: "
					+ e.getMessage());
			return;
		}
		String clzes = props.getProperty("initializers");
		if (clzes == null || clzes.trim().length() == 0) {
			return;
		}

		String[] clzzStr = clzes.split(",");

		for (String clzz : clzzStr) {
			try {
				clzz = clzz.trim();
				if (clzz.length() == 0) {
					continue;
				}
				Class<?> clz = Class.forName(clzz);
				ISystemInitializer initializer = (ISystemInitializer) clz
						.newInstance();
				initializer.initSystem();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
