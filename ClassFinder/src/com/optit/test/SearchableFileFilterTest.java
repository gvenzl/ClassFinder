package com.optit.test;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

import com.optit.SearchableFileFilter;

/**
 * @author gvenzl
 *
 */
public class SearchableFileFilterTest extends TestCase
{
	@Test
	public void test_accept() throws Exception
	{
		
		System.out.println("Test SearchableFileFilter.accept()");
		
		SearchableFileFilter filter = new SearchableFileFilter();
		
		assertEquals(filter.accept(new File("test.jar")), true);
		assertEquals(filter.accept(new File("test.war")), true);
		assertEquals(filter.accept(new File("test.ear")), true);
		assertEquals(filter.accept(new File("test.zip")), true);
		
		//Directory test
		File dir = new File("./dir");
		dir.mkdir();
		assertEquals(filter.accept(dir), true);
		dir.delete();
		
		// Negative test
		assertEquals(filter.accept(new File("test.log")), false);
		assertEquals(filter.accept(new File("test.txt")), false);

	}
}
