package perfmonitoringusinginstrument;

import java.lang.instrument.Instrumentation;

public class PreClazz {
	public static void premain(String agentArgs, Instrumentation inst) {
		inst.addTransformer(new TestLoadTransformer());
	}

}
