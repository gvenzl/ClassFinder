package com.optit.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.optit.logger.CommandLineLogger;

/**
 * @author gvenzl
 *
 */
public class CommandLineLoggerTest extends TestCase {
	@Test
	public void test_logEmpty() throws Exception {
		System.out.println("Test logger new line");
		new CommandLineLogger().log();
	}
	
	@Test
	public void test_logNotEmpty() throws Exception {
		System.out.println("Test logger output");
		new CommandLineLogger().log("Testline test test test");
	}
	
	@Test
	public void test_logThreeParams() {
		System.out.println("Test logger with three parameters passed on");
		new CommandLineLogger().log("MyClass", "InThis.jar");
	}
	
	@Test
	public void test_logErr() throws Exception {
		System.out.println("Test logger to error output");
		new CommandLineLogger().logErr("Testline error error error");
	}
	
	@Test
	public void test_logTimed() throws Exception {
		System.out.println("Test logger with timing output");
		new CommandLineLogger().logTimed("Testline test test test");
		new CommandLineLogger().logTimed("Second test line test test test");
	}
	
	@Test
	public void test_setVerbose() throws Exception {
		new CommandLineLogger().setVerbose(true);
	}
	
	@Test
	public void test_getVerbose() {
		CommandLineLogger logger = new CommandLineLogger();
		logger.setVerbose(true);
		assertEquals(true, logger.getVerbose());
		logger.setVerbose(false);
		assertEquals(false, logger.getVerbose());
	}
	
	@Test
	public void test_logVerbose() throws Exception {
		new CommandLineLogger().setVerbose(true);
		new CommandLineLogger().logVerbose("This is a DEBUG OUTPUT!");
	}
}
