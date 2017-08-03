package com.yuiu.view;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tip extends JPanel{
	JLabel box1,box2,box3;
	
	Tip(){
		setLayout(new GridLayout(3,1));
		box1 = new JLabel("文件夹路径：压缩文件夹下全部文件");
		box2 = new JLabel("*.css：压缩全部css文件");
		box3 = new JLabel("*.js ：压缩全部js文件");
		add(box3);
		add(box2);
		add(box1);
	}
}
