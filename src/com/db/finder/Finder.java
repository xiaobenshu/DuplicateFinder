package com.db.finder;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JList;
import java.awt.Panel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.swing.JRadioButton;

public class Finder  implements ActionListener{

	private JFrame frmAndroid;
	private JTextField textField;
	private JTextField textField_1;
	JTextArea textArea ;
	JRadioButton rdbtnNewRadioButton;
	JTextPane httpStringArea ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Finder window = new Finder();
					window.frmAndroid.setVisible(true);
					JSwingCommon.moveCenter(window.frmAndroid);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Finder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAndroid = new JFrame();
		frmAndroid.setTitle("Android重复资源删除工具");
		frmAndroid.setBounds(100, 100, 450, 300);
		frmAndroid.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAndroid.getContentPane().setLayout(new BorderLayout(10, 10));
		
		Panel panel = new Panel();
		frmAndroid.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(10, 10));
		
		textField_1 = new JTextField();
		panel.add(textField_1, BorderLayout.CENTER);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("检查");
		btnNewButton.addActionListener(this);

		panel.add(btnNewButton, BorderLayout.EAST);
		
		rdbtnNewRadioButton = new JRadioButton("删除多余文件");
		panel.add(rdbtnNewRadioButton, BorderLayout.WEST);
		rdbtnNewRadioButton.setSelected(true);
		
		
		httpStringArea = new JTextPane();
		JScrollPane scroll = new JScrollPane(httpStringArea); 		
		scroll.setVerticalScrollBarPolicy( 
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 	
		scroll.setVerticalScrollBarPolicy( 
		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 

		
		frmAndroid.getContentPane().add(scroll, BorderLayout.CENTER);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equalsIgnoreCase("检查")){
			String path = JSwingCommon.openChooseDialog();
			textField_1.setText(path);
			if (path.length() <= 0) {
				return ;
			}
			
			HashMap<String,File> lists = new ResDuplicateFinder(path).checkDir();			
			if (lists == null) {
				JSwingCommon.showDialog("请选择正确的Android工程,或者已清理干净");
				return ;
			}		
			StringBuffer buffer = new StringBuffer();
			Set<String> keys = lists.keySet();
			for (String string : keys) {
				buffer.append(string+ "   Path:"+lists.get(string).getAbsolutePath() + "\n" );
				if (rdbtnNewRadioButton.isSelected()) {
					FileUtil.deleteFile(lists.get(string).getAbsolutePath());	
				}
			}
			httpStringArea.setText(buffer.toString());			
			if (lists != null) {		
				if (rdbtnNewRadioButton.isSelected()) {
					JSwingCommon.showDialog("共清理垃圾文件："+keys.size()+"个");
				}
				else JSwingCommon.showDialog("共检查出垃圾文件："+keys.size()+"个");
			}
		}
		
	}

	
}
