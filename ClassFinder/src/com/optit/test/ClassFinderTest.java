package com.optit.test;

import java.io.File;

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
	public void test_handleException()
	{
		System.out.println("Test exception handling of non-caught exceptions");
		new ClassFinder().handleExceptions(new RuntimeException());
	}

	@Test
	public void test_printHelp()
	{
		System.out.println("Test online help");
		new ClassFinder().printHelp();
	}
	
	@Test
	public void test_parseArguments()
	{
		new ClassFinder().parseArguments(new String[] {Parameters.directory, "test", Parameters.classname, "test", Parameters.matchCase, Parameters.verbose});
	}
	
	@Test
	public void test_buildFileList()
	{
		if (System.getProperty("os.name").startsWith("Windows"))
			new ClassFinder().buildFileList(new File("C:\\temp"));
		else
			new ClassFinder().buildFileList(new File("/tmp"));
	}
}
