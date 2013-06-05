package com.optit;

import java.io.File;

public final class SearchableFileFilter extends javax.swing.filechooser.FileFilter implements java.io.FileFilter
{
	@Override
	public boolean accept(File pathname)
	{
		if (pathname.isDirectory())
			return true;
		
		String fileName = pathname.getName();
		if (fileName.endsWith(".jar")
			|| fileName.endsWith(".war")
			|| fileName.endsWith(".ear")
			|| fileName.endsWith(".zip")
			|| fileName.endsWith(".rar")
			|| fileName.endsWith(".class")
			|| fileName.endsWith(".java"))
			return true;
		else
			return false;
	}

	@Override
	public String getDescription()
	{
		return ".jar, .war, .ear, .zip, .class, .java, directory";
	}

}
