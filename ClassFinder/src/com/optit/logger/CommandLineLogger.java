package com.optit.logger;

/**
 * The Logger is used as an interface for logging information
 * @author gvenzl
 *
 */
public class CommandLineLogger
{
	private static long milliSecs = 0;
	private static boolean verboseFlag = false;
	
	/**
	 * En- or disables verbose output
	 * @param debug True=enable debug; False=disable debug
	 */
	public static void setVerbose(boolean verbose)
	{
		verboseFlag = verbose;
	}
	
	/**
	 * Logs one line into the standard output
	 * @param line The line to log
	 */
	public static void log(String line)
	{
		synchronized (System.out)
		{
			System.out.println(line);
		}
	}
	
	/**
	 * Logs one line into the standard output only if verbose is enabled
	 * @param line The line to log
	 */
	public static void logVerbose(String line)
	{
		if (verboseFlag)
		{
			synchronized (System.out)
			{
				System.out.println("VERBOSE: " + line);
			}
		}
	}
	
	
	/**
	 * Logs a new line into the standard output
	 */
	public static void log()
	{
		synchronized (System.out)
		{
			System.out.println();
		}
	}

	/**
	 * Logs a line into the standard output with timing set
	 * @param line The line to log
	 */
	public static void logTimed(String line)
	{
		if (milliSecs == 0)
		{
			milliSecs = System.currentTimeMillis();
			synchronized (System.out)
			{
				System.out.println(line);
			}
		}
		else
		{
			long duration = System.currentTimeMillis()-milliSecs;
			milliSecs = 0;
			synchronized (System.out)
			{
				System.out.println("Duration: " + duration + "ms - " + line);
			}
		}
	}
	
	/**
	 * Logs an error into the error output
	 * @param line The line to log
	 */
	public static void logErr(String line)
	{
		synchronized (System.err)
		{
			System.err.println(line);
		}
	}
}
