package com.optit.logger;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author gvenzl
 *
 */
public class GuiLogger implements Logger
{
	private DefaultTableModel tm;
	private JLabel statusBar;
	boolean verboseFlag = false;
	
	public GuiLogger(DefaultTableModel tm, JLabel statusBar)
	{
		this.tm = tm;
		this.statusBar = statusBar;
	}

	@Override
	public void setVerbose(boolean verbose)
	{
		this.verboseFlag = verbose;
	}
	
	@Override
	public boolean getVerbose()
	{
		return verboseFlag;
	}
	
	@Override
	public void log()
	{
		log(null, null);
	}

	@Override
	public void log(String line)
	{
		log(line, line);
	}

	@Override
	public void log(String className, String location) 
	{
		tm.addRow(new String[] {className, location});
	}

	@Override
	public void logVerbose(String line)
	{
		statusBar.setText(line);
	}
	
	@Override
	public void logErr(String line)
	{
		tm.addRow(new Object[] {line, line});
	}
}
