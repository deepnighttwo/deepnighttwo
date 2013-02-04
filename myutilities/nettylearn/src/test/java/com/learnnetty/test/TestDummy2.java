package com.learnnetty.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDummy2 {

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
	
	@Test
	public void test2() {
		log("Test case3-2!");
	}

	@Before
	public void setUp() throws Exception {
		log("Before Test");

	}

	@After
	public void after() throws Exception {
		
		System.out.println("\u951b");
		log("After Test");

	}

}
