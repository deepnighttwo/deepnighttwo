package junit.testcase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCase2 {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test1() {
		System.out.println("Test case2");
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

}
