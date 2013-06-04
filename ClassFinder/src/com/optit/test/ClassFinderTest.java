package com.optit.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.optit.ClassFinder;
import com.optit.Parameters;

/**
 * @author gvenzl
 *
 */
public class ClassFinderTest extends TestCase
{
	@Test
	public void test_handleException() throws Exception
	{
		System.out.println("Test exception handling of non-caught exceptions");
		ClassFinder.handleExceptions(new RuntimeException());
	}

	@Test
	public void test_printHelp() throws Exception
	{
		System.out.println("Test online help");
		ClassFinder.printHelp();
	}
	
	@Test
	public void test_parseArguments() throws Exception
	{
		new ClassFinder().parseArguments(new String[] {Parameters.directory, "test", Parameters.classname, "test", Parameters.matchCase, Parameters.verbose});
	}
}
