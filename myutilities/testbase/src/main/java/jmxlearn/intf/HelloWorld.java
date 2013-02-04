package jmxlearn.intf;



public class HelloWorld implements HelloWorldMBean {

	private String message;

	public HelloWorld() {
		message = "This is initial message";
	}

	@Override
	public void setMessage(String str) {
		this.message = str;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void printMessage() {
		System.out.println(message);
	}

}
