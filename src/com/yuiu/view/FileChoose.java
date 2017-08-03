package com.yuiu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class FileChoose extends JPanel{
	public static final int Width=250;
	public static final int Height=200;
	    
	
	JLabel label1,label2;
	JTextField inPath,outPath;
	JButton inBut,outBut,submit,jsBut;
	JPanel up,down;
	
	FileChoose(){
		
		setLayout(new GridLayout(4,1));
		
		up = new JPanel();
		down = new JPanel();
		up.setLayout(new FlowLayout(FlowLayout.LEFT));
		down.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		label1 = new JLabel("文件：");
		label2 = new JLabel("路径：");
		inBut = new JButton("+");
		outBut = new JButton("-");
		submit = new JButton("确定");
		jsBut = new JButton("js替换列表");
		inPath = new JTextField("",15);
		outPath = new JTextField("",15);
		
		up.add(label1);
		up.add(inPath);
		up.add(inBut);
		down.add(label2);
		down.add(outPath);
		down.add(outBut);
		
		add(up);
		add(down);
		add(submit);
		add(jsBut);
	}

}
