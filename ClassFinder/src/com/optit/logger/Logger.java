package com.optit.logger;

/**
 * 
 * @author gvenzl
 *
 */
public interface Logger
{
	public void log();
	
	public void log(String line);
	
	public void log(String className, String location);
	
	public void logErr(String line);
	
	public void logVerbose(String line);
	
	public void setVerbose(boolean verbose);
	
	public boolean getVerbose();
}
