package junit.testsuite;

import junit.testcase.JUnitTestCase;
import junit.testcase.TestCase2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ JUnitTestCase.class, TestCase2.class })
public class AllTestsUsingAnnotation {

}
