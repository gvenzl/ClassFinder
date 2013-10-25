package com.optit;

import java.io.File;

/**
 * 
 * @author gvenzl
 *
 */
public final class SearchableFileFilter extends javax.swing.filechooser.FileFilter implements java.io.FileFilter
{
	@Override
	public boolean accept(File pathname)
	{
		final String fileName = pathname.getName();
		return (pathname.isDirectory()
			|| fileName.endsWith(".jar")
			|| fileName.endsWith(".war")
			|| fileName.endsWith(".ear")
			|| fileName.endsWith(".zip")
			|| fileName.endsWith(".rar")
			|| fileName.endsWith(".class")
			|| fileName.endsWith(".java"));
	}

	@Override
	public String getDescription()
	{
		return ".jar, .war, .ear, .zip, .class, .java, directory";
	}

}
