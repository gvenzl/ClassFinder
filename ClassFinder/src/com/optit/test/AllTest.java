package com.optit.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author gvenzl
 *
 */
public class AllTest extends TestSuite
{
	public static Test suite()
	{
		
		TestSuite suite = new TestSuite("Test for all JUnit tests");
		suite.addTestSuite(ClassFinderTest.class);
		suite.addTestSuite(LoggerTest.class);
		suite.addTestSuite(ParameterTest.class);
		return suite;
	}
}