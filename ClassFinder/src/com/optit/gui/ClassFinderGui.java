package com.optit.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

import com.optit.ClassFinder;
import com.optit.Parameters;
import com.optit.SearchableFileFilter;
import com.optit.logger.TableLogger;

public class ClassFinderGui {

	private JFrame frame;
	private JTextField tfJarFileFolder;
	private JTextField tfClassName;
	private JCheckBox chckbxMatchCase;
	private JFileChooser fc;
	private ClassFinderTableModel tm;
	private JTable resultsTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClassFinderGui window = new ClassFinderGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClassFinderGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 547, 367);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblJarFile = new JLabel("Jar file / Folder:");
		
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
		btnSearch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Path is empty
				if (tfJarFileFolder.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "No folder was specified", "Error", JOptionPane.ERROR_MESSAGE);
				}
				// Path does not exist
				else if (!new File(tfJarFileFolder.getText()).exists())
				{
					JOptionPane.showMessageDialog(null, "Specified path does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				// Class name is empty
				else if (tfClassName.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "No class name was specified", "Error", JOptionPane.ERROR_MESSAGE);
				}
				// Validation successful, run parsing
				else
				{
					tm.removeAllRows();
					ClassFinder finder = new ClassFinder(new TableLogger(tm));
					finder.parseArguments(new String[] {Parameters.directory, tfJarFileFolder.getText(), Parameters.classname, tfClassName.getText(), (chckbxMatchCase.isSelected() ? Parameters.matchCase : null)});
					finder.findClass();
				}
			}
		});
		
		JLabel lblMatchCase = new JLabel("Match case:");
		
		chckbxMatchCase = new JCheckBox("");
		chckbxMatchCase.setSelected(true);
		
		tm = new ClassFinderTableModel (new String[] {"Class", "Jar file", "Location"}, 0);
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
		resultsTable.getColumnModel().getColumn(1).setPreferredWidth(250);
		resultsTable.getColumnModel().getColumn(2).setPreferredWidth(10);
		
		JScrollPane scrollPane = new JScrollPane(resultsTable);
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
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
								.addComponent(chckbxMatchCase)))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 516, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(chckbxMatchCase)
						.addComponent(lblMatchCase))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
