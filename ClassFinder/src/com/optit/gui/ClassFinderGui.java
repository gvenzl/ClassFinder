package com.optit.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

import com.optit.ClassFinder;
import com.optit.Parameters;
import com.optit.SearchableFileFilter;

public class ClassFinderGui {

	private JFrame frame;
	private JTextField tfJarFileFolder;
	private JTextField tfClassName;
	private JTable resultsTable;
	private JCheckBox chckbxMatchCase;
	private JFileChooser fc;

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
				ClassFinder finder = new ClassFinder();
				finder.parseArguments(new String[] {Parameters.directory, tfJarFileFolder.getText(), Parameters.classname, tfClassName.getText(), (chckbxMatchCase.isSelected() ? Parameters.matchCase : null)});
				finder.findClass();
			}
		});
		
		JLabel lblMatchCase = new JLabel("Match case:");
		
		chckbxMatchCase = new JCheckBox("");
		chckbxMatchCase.setSelected(true);
		
		resultsTable = new JTable();
		resultsTable.setCellSelectionEnabled(true);
		resultsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
						.addComponent(resultsTable, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE))
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
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(resultsTable, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
