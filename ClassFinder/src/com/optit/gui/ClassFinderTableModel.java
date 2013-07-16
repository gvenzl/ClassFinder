package com.optit.gui;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class ClassFinderTableModel extends DefaultTableModel
{
	private static final long serialVersionUID = -6524244270026531200L;

	public ClassFinderTableModel() {
		super();
	}

	public ClassFinderTableModel(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public ClassFinderTableModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public ClassFinderTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	@SuppressWarnings("rawtypes")
	public ClassFinderTableModel(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	@SuppressWarnings("rawtypes")
	public ClassFinderTableModel(Vector data, Vector columnNames) {
		super(data, columnNames);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public Class getColumnClass(int col)
	{
		 return String.class;  
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
        return false;
    } 
}
