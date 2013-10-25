package com.optit.gui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

import com.optit.ClassFinder;
import com.optit.Parameters;
import com.optit.SearchableFileFilter;
import com.optit.logger.GuiLogger;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

/**
 * 
 * @author gvenzl
 *
 */
public class ClassFinderGui {

	private JFrame frame;
	private JTextField tfJarFileFolder;
	private JTextField tfClassName;
	private JCheckBox chckbxMatchCase;
	private JFileChooser fc;
	private ClassFinderTableModel tm;
	private JTable resultsTable;
	private JLabel statusBar;
	private JCheckBox chckbxRecursiveSearch;

	/**
	 * Create the application.
	 */
	public ClassFinderGui()
	{
		try {
			// Set system look and feel (Windows / Unix, etc.)
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// If System look and feel failed, set the CrossPlatform UI look and feel
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
		throws HeadlessException
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("ClassFinder");
		
		JLabel lblJarFile = new JLabel("File / Folder:");
		
		JLabel lblClassName = new JLabel("Class name:");
		
		tfJarFileFolder = new JTextField();
		tfJarFileFolder.setColumns(10);
		
		tfClassName = new JTextField();
		tfClassName.setColumns(10);
		
		fc = new JFileChooser();
		fc.setDialogTitle("Choose file or folder");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setFileFilter(new SearchableFileFilter());
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
		        if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
		        {
		        	tfJarFileFolder.setText(fc.getSelectedFile().getAbsolutePath());
		        }
			}
		});
		
		JButton btnSearch = new JButton("Search");
		// Make search button default for ENTER key
		frame.getRootPane().setDefaultButton(btnSearch);
		btnSearch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				performSearch();
			}
		});
		
		JLabel lblMatchCase = new JLabel("Match case:");
		
		chckbxMatchCase = new JCheckBox("");
		chckbxMatchCase.setSelected(true);
		
		tm = new ClassFinderTableModel (new String[] {"Class", "Location"}, 0);
		resultsTable = new JTable(tm);
		// Enable column selection for copy/paste
		resultsTable.setColumnSelectionAllowed(true);
		// Enable cell selection for copy/paste of the value
		resultsTable.setCellSelectionEnabled(true);
		// Make the table big enough to fill the viewport
		resultsTable.setFillsViewportHeight(true);
		// Make table sort-able
		resultsTable.setAutoCreateRowSorter(true);
		
		// Set column widths
		resultsTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		resultsTable.getColumnModel().getColumn(1).setPreferredWidth(125);
		
		JScrollPane scrollPane = new JScrollPane(resultsTable);
		
		statusBar = new JLabel("Ready");
		
		JLabel lblRecursive = new JLabel("Recursive:");
		
		chckbxRecursiveSearch = new JCheckBox("");
		chckbxRecursiveSearch.setSelected(true);
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblJarFile)
								.addComponent(lblClassName)
								.addComponent(lblMatchCase))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(tfClassName)
										.addComponent(tfJarFileFolder, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnSearch, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnBrowse, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(chckbxMatchCase)
									.addGap(18)
									.addComponent(lblRecursive, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
									.addGap(20)
									.addComponent(chckbxRecursiveSearch, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))))
						.addComponent(statusBar))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblJarFile)
						.addComponent(tfJarFileFolder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowse))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblClassName)
						.addComponent(tfClassName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSearch))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblMatchCase)
						.addComponent(chckbxMatchCase)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(7)
								.addComponent(lblRecursive))
							.addComponent(chckbxRecursiveSearch, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
					.addGap(13)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(statusBar)
					.addGap(6))
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tfJarFileFolder, btnBrowse, tfClassName, chckbxMatchCase, chckbxRecursiveSearch, btnSearch}));
	}
	
	private void performSearch()
	{
		// Path is empty
		if (tfJarFileFolder.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "No file or folder was specified!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		// Path does not exist
		else if (!new File(tfJarFileFolder.getText()).exists())
		{
			JOptionPane.showMessageDialog(null, "Specified path does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		// Class name is empty
		else if (tfClassName.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "No class name was specified!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		// Validation successful, run parsing
		else
		{
			statusBar.setText("Searching...");
			tm.setRowCount(0);

			ArrayList<String> params = new ArrayList<String>();
			params.add(Parameters.directory);
			params.add(tfJarFileFolder.getText());
			params.add(Parameters.classname);
			params.add(tfClassName.getText());
			params.add(Parameters.verbose);
			
			if (chckbxMatchCase.isSelected())
			{
				params.add(Parameters.matchCase);
			}
			
			if (chckbxRecursiveSearch.isSelected())
			{
				params.add(Parameters.recursiveSearch);
			}
			
			ClassFinder finder = new ClassFinder(new GuiLogger(tm, statusBar));
			if (finder.parseArguments(params.toArray(new String[] {})))
				new Thread(finder).start();
		}
	}
}
