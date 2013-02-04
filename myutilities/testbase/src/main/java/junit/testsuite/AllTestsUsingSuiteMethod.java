package junit.testsuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.testcase.TestCase3;

public class AllTestsUsingSuiteMethod {

	public static Test suite() {
		TestSuite suite = new TestSuite("Root Test");
		// $JUnit-BEGIN$
		suite.addTest(WrapTestCaseToTestSuite.suite());

		suite.addTest(new JUnit4TestAdapter(TestCase3.class));

		suite.addTest(new JUnit4TestAdapter(AllTestsUsingAnnotation.class));

		// $JUnit-END$
		return suite;
	}

}
