package com.optit.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.optit.Parameters;

/**
 * @author gvenzl
 *
 */
public class ParameterTest extends TestCase
{
	private class MyParams extends Parameters
	{
		
	}
	
	@Test
	public void test_instantiate()
	{
		System.out.println("Test Parameters instanziation");
		new MyParams();
	}
	
	@Test
	public void test_values()
	{
		assertEquals("-d", Parameters.directory);
		assertEquals("-c", Parameters.classname);
		assertEquals("-m", Parameters.matchCase);
		assertEquals("-verbose", Parameters.verbose);
	}
}
