package jmxlearn.agent;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import jmxlearn.intf.HelloWorld;

import com.sun.jdmk.comm.HtmlAdaptorServer;

public class HelloWorldAgent {

	private MBeanServer mbs = null;

	public HelloWorldAgent() {
		mbs = MBeanServerFactory.createMBeanServer("HelloAgent");

		HelloWorld hwi = new HelloWorld();
		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		ObjectName adaptorName = null;
		ObjectName helloworldName = null;

		try {
			helloworldName = new ObjectName("HelloAgent:name=helloworld1");
			mbs.registerMBean(hwi, helloworldName);

			adaptorName = new ObjectName(
					"HelloAgent:name=htmladapter,port=9999");
			adapter.setPort(9999);
			mbs.registerMBean(adapter, adaptorName);

			adapter.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Running");
		HelloWorldAgent agent = new HelloWorldAgent();
	}

}
