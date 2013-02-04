package easymocktest;

public class InvokerFrameWork {

	private IInvoker invoker;

	public void setInvoker(IInvoker invoker) {
		this.invoker = invoker;
	}

	public Object invokeIt() throws Exception {
		if (invoker == null) {
			throw new InvalidatedInvokerException();
		}
		return invoker.invoke();
	}

}
