package junit.testsuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.testcase.JUnitTestCase;

public class WrapTestCaseToTestSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(JUnitTestCase.class);
	}

}
