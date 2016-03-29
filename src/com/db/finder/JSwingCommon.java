package com.db.finder;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class JSwingCommon {

	public static void moveCenter(JFrame windows){	
		if (windows != null) {		
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int width = 800;
			int height = 600;
			windows.setBounds((dimension.width - width) / 2,
					(dimension.height - height) / 2, width, height);
		}
	}

	public static void showDialog(String info){
		JOptionPane.showMessageDialog(null, info,"出错了", JOptionPane.ERROR_MESSAGE);
	}

	
	public static String openChooseDialog(){
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.showDialog(new JLabel(), "选择文件夹");
		File file = jfc.getSelectedFile();
		if (file!= null && file.isDirectory()) {
			return file.getAbsolutePath();
		}
		return "";
	}
}
