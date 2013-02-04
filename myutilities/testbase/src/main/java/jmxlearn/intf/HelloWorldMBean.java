package jmxlearn.intf;

public interface HelloWorldMBean {

	public void setMessage(String str);

	public String getMessage();

	public void printMessage();

}
