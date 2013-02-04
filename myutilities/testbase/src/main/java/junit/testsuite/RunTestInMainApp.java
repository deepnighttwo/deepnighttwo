package junit.testsuite;

import junit.testcase.JUnitTestCase;
import junit.testcase.TestCase2;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class RunTestInMainApp {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(JUnitTestCase.class,
				TestCase2.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
	}

}
