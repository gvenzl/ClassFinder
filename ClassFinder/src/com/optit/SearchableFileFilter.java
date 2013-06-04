package com.optit;

import java.io.File;
import java.util.LinkedList;

public final class SearchableFileFilter extends javax.swing.filechooser.FileFilter implements java.io.FileFilter
{
	LinkedList<String> list;
	
	public SearchableFileFilter()
	{
		list = new LinkedList<String>();
		list.add(".jar");
		list.add(".war");
		list.add(".ear");
		list.add(".zip");
	}
	
	@Override
	public boolean accept(File pathname)
	{
		if (pathname.isDirectory())
			return true;
		
		if (list.contains(pathname.getName().
						substring(pathname.
									getName().length()-4,
									pathname.getName().length())))
			return true;
		else
			return false;
	}

	@Override
	public String getDescription()
	{
		return ".jar, .war, .ear, .zip, directory";
	}

}
