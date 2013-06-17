package com.optit.test;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

import com.optit.ClassFinder;
import com.optit.Parameters;
import com.optit.logger.CommandLineLogger;

/**
 * @author gvenzl
 *
 */
public class ClassFinderTest extends TestCase
{
	@Test
	public void test_ClassFinder()
	{
		new ClassFinder();
	}
	
	@Test
	public void test_ClassFinderWithCommandLineLogger()
	{
		new ClassFinder(new CommandLineLogger());
	}
	
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
	
	@Test
	public void testNegative_buildFileList()
	{
		new ClassFinder().buildFileList(new File("IDoNotExist"));
	}
	
	@Test
	public void test_run() throws InterruptedException
	{
		ClassFinder finder = new ClassFinder();
		finder.parseArguments(new String[] {Parameters.directory, "test", Parameters.classname, "test", Parameters.matchCase, Parameters.verbose});
		finder.run();
		
	}
	
	@Test
	public void test_findClass()
	{
		ClassFinder finder = new ClassFinder();
		finder.parseArguments(new String[] {Parameters.directory, ".", Parameters.classname, "Test,"});
		finder.findClass();
	}
	
	@Test
	public void test_mainGUIStart()
	{
		ClassFinder.main(new String[] {});
	}
	
	public void test_mainCommandLine()
	{
		ClassFinder.main(new String[] {Parameters.directory, "test", Parameters.classname, "test"});
	}
}
