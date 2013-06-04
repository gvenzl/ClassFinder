package com.optit;

import java.io.File;
import java.util.LinkedList;
import java.util.Properties;

import com.optit.logger.CommandLineLogger;

public class ClassFinder
{
	static Properties parameters;
	static LinkedList<File> files;
	
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
		files = new LinkedList<File>();
		// Get file tree of directory
		buildFileList(new File(parameters.getProperty(Parameters.directory)));
	}
	
	private void buildFileList(File directory)
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
