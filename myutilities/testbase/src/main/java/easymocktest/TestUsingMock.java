package easymocktest;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class TestUsingMock {

	InvokerFrameWork worker;
	IInvoker invoker;

	@Before
	public void setup() {
		worker = new InvokerFrameWork();
		invoker = EasyMock.createMock(IInvoker.class);
	}

	@Test(expected = InvalidatedInvokerException.class)
	public void testException() throws Exception {
		worker.invokeIt();
	}

	@Test
	public void testInvoker() throws Exception {
		EasyMock.expect(invoker.invoke()).andReturn("Finish").times(2);
		EasyMock.replay(invoker);

		worker.setInvoker(invoker);

		Assert.assertEquals("Finish", worker.invokeIt());
		Assert.assertEquals("Finish", worker.invokeIt());

		// 没有调用两次，就会抛出异常。
		EasyMock.verify(invoker);
	}

	@Test(expected = InvalidatedInvokerException.class)
	public void testInvokerException() throws Exception {
		EasyMock.expect(invoker.invoke()).andThrow(new InvalidatedInvokerException());
		EasyMock.replay(invoker);

		worker.setInvoker(invoker);
		worker.invokeIt();
	}
}
