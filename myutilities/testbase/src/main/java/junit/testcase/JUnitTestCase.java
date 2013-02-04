package junit.testcase;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class JUnitTestCase {

	static {
		System.out.println("Class Initial...");
	}

	@BeforeClass
	public static void beforeClass() {
		System.out.println("Before Class!");

	}

	@AfterClass
	public static void afterClass() {
		System.out.println("After Class!");
	}

	@Before
	public void setupTest() {
		System.out.println("Before!");
	}

	@Test
	public void testASimple() {

	}

	@Test
	public void testASimpleFail() {
		Assert.assertEquals(1, 2);
	}

	@Test
	public void testException() {
		throw new NullPointerException();
	}

	@Test(expected = NullPointerException.class, timeout = 9000)
	public void testExceptionIgnore() {
		throw new NullPointerException();
	}

	@Ignore("This should be ignored")
	public void testExceptionIgnoreCase() {
		throw new NullPointerException();
	}

	@After
	public void cleanTestCase() {
		System.out.println("After!");
	}
}
