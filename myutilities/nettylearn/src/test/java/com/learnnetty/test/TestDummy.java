package com.learnnetty.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDummy {

	private static void log(Object obj) {
		System.out.println(obj);
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		log("Before Class");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		log("After Class");

	}

	@Test
	public void test1() {
		log("Test case3!");
	}

	@Before
	public void setUp() throws Exception {
		log("Before Test");

	}

	@After
	public void after() throws Exception {
		log("After Test");

	}

}
