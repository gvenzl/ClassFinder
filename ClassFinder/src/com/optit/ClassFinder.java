package com.optit;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.optit.logger.CommandLineLogger;

public class ClassFinder
{
	static Properties parameters;
	static LinkedList<File> files = new LinkedList<File>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ClassFinder finder = new ClassFinder();
		if (!finder.parseArguments(args))
		{
			// Parsing of arguments was not successful, print help and exit
			printHelp();
			System.exit(0);
		}
		finder.findClass();
	}
	
	/**
	 * Parses the passed on arguments
	 * @param args The arguments to pass on
	 * @return Whether the parsing of the arguments was successful
	 */
	public boolean parseArguments(String[] args)
	{
		// No parameters were passed, print help and exit printHelp does the exit
		if (null == args || args.length == 0 || args.length == 1)
		{
			return false;
		}
		else
		{
			// Parameters were passed on, properties file ignored -> read passed on parameters
			parameters = new Properties();
			// Set defaults
			parameters.setProperty(Parameters.matchCase, "false");
			parameters.setProperty(Parameters.verbose, "false");
			
			for (int i=0;i<args.length;i++)
			{
				switch (args[i])
				{
					case Parameters.directory:
					{
						parameters.setProperty(Parameters.directory, args[++i]);
						break;
					}
					case Parameters.classname:
					{
						parameters.setProperty(Parameters.classname, args[++i]);
						break;
					}
					case Parameters.matchCase:
					{
						parameters.setProperty(Parameters.matchCase, "true");
						break;
					}
					case Parameters.verbose:
					{
						parameters.setProperty(Parameters.verbose, "true");
						break;
					}
					case "-help":
					case "--help":
					case "-?":
					case "--?":
					{
						printHelp();
						System.exit(0);
					}
					default:
					{
						CommandLineLogger.log("Unknown parameter: " + args[i]);
						CommandLineLogger.log();
						return false;
					}
				}
			}
			return true;
		}
	}
	
	/**
	 * Print usage help into stdout and Exit
	 */
	public static void printHelp()
	{
		CommandLineLogger.log("Usage: java -jar ClassFinder.jar|com.optit.ClassFinder -d [directory] -c [classname] -m -verbose -help|-h|--help|-?");
        CommandLineLogger.log("");
        CommandLineLogger.log("[-d]			The directory to search in");
        CommandLineLogger.log("[-c]			The classname to search for");
        CommandLineLogger.log("[-m]			Match case");
        CommandLineLogger.log("[-verbose]			Enables verbose output");
        CommandLineLogger.log("[-help|--help|-h|-?]	Display this help");
        CommandLineLogger.log();
        CommandLineLogger.log("The directory specified will be searched recursviely.");
        CommandLineLogger.log("The class name can either just be the class name (e.g. String) or the fully qualified one (e.g. java.lang.String)");
        CommandLineLogger.log();
        CommandLineLogger.log("Good hunting!");
	}
	
	/**
	 * This function sits on the top level and catches all exceptions and prints out proper error messages 
	 * @param e The exception that comes from somewhere within the code
	 */
	public static void handleExceptions(Exception e)
	{
		CommandLineLogger.log("Application error: " + e.getMessage());
		if (e.getCause() != null)
		{
			CommandLineLogger.log("Caused by: " + e.getCause().toString());
		}
		e.printStackTrace(System.err);
	}
	
	// Find the class
	public void findClass()
	{
		boolean verbose = parameters.getProperty(Parameters.verbose).equals("true");

		// Change "." in package names to slashes (e.g. "org.apache.commons" -> "org/apache/commons")
		String classname = parameters.getProperty(Parameters.classname).replaceAll("\\.", "/");
		// Class name contains some package qualifiers
		boolean containsPackageQualifier = (classname.indexOf(".") != -1); 
		
		// Get file tree of directory
		buildFileList(new File(parameters.getProperty(Parameters.directory)));
		
		Iterator<File> fileIterator = files.iterator();
		// Loop over all the filtered files
		while (fileIterator.hasNext())
		{
			
			//TODO: Java source files aren't found!
			
			File file = fileIterator.next();
			
			if (verbose)
			{
				CommandLineLogger.log("Looking at: " + file.getAbsolutePath());
			}
			
			// Direct class files
			if (file.getAbsolutePath().endsWith(".class"))
			{
				// IF:
				// Package qualifier was defined or a part of (e.g. apache.commons.Random -> apache/commons/Random)
				// AND
				// The file name ends with that qualifier --> org.apache.commons.Random.class ends with "apache/commons/Random.class)
				// --> CLASS FOUND!
				// OR
				// Package qualifier wasn't specified but just the class (e.g. Random)
				// AND
				// The FILE NAME (note the call to file.getName() rather than getAbsolutePath()) matches --> org.apache.commons.Random.class's file name is Random.class
				// --> CLASS FOUND!
				if ((containsPackageQualifier && file.getAbsolutePath().endsWith(classname + ".class"))
					||
					(!containsPackageQualifier && file.getName().equals(classname + ".class")))
				{
					CommandLineLogger.log("Class \"" + classname + "\" found at: " + file.getAbsolutePath());
				}
			}
			// The rest of the files: jar, war, ear, zip, rar
			else
			{
				try (JarFile jarFile = new JarFile(file))
				{
					Enumeration<JarEntry> entries = jarFile.entries();
					while(entries.hasMoreElements())
					{
						JarEntry entry = (JarEntry) entries.nextElement();
						
						if (containsPackageQualifier)
						{
							if (entry.getName().endsWith(classname + ".class") || entry.getName().endsWith(classname + ".java"))
							{
								CommandLineLogger.log("Class \"" + classname + "\" found in \"" + file.getAbsolutePath() + "\" as " + entry.getName());
							}
						}
						// No package qualified, just Class Name
						else
						{
							// IF:
							// -- 95% scenario first: Class is in a sub package of the jar file
							// The Class name ends with "/Classname.class" OR "/Classname.java" (e.g. org.apache.commons.Random.class ends with "/Random.class")
							// OR
							// In the case that the class doesn't belong to any package or it was just a ZIPPED file of a .class
							// The file name equals classname.class or classname.java (e.g. Random.java got zipped up into Random.zip. The only thing in there is Random.java and as no package qualifier was given, the class is found)
							if (entry.getName().endsWith("/" + classname + ".class") || entry.getName().endsWith("/" + classname + ".java")
								|| 
								entry.getName().equals(classname + ".class") || entry.getName().equals(classname + ".java"))
							{
								CommandLineLogger.log("Class \"" + classname + "\" found in \"" + file.getAbsolutePath() + "\" as " + entry.getName());
							}
						}
					}
				}
				catch (IOException e)
				{
					if (verbose)
					{
						CommandLineLogger.log("Error reading file " + file.getAbsolutePath() + ": " + e.getMessage());
						CommandLineLogger.logErr(e.getMessage());
					}
				}
			}
		}
	}
	
	public void buildFileList(File directory)
	{
		if (!directory.exists())
		{
			CommandLineLogger.log("Directory \"" + directory.getAbsolutePath() + "\" does not exist!");
		}
		
		// File is directly passed on, no directory serach necessary
		if (!directory.isDirectory() && new SearchableFileFilter().accept(directory))
		{
			files.add(directory);
		}
		else
		{
			for (File file : directory.listFiles(new SearchableFileFilter()))
			{
				if (file.isDirectory())
				{
					buildFileList(file);
				}
				else
				{
					files.add(file);
				}
			}
		}
	}
}
