package com.yuiu.view;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import com.yuiu.model.SetProperties;
import com.yuiu.model.YuiUtil;



public class Window extends JFrame implements ActionListener{
	

	Tip panel1;
	FileChoose panel2;
	Dialog dialog;
	JsDialog jsDialog;
	YuiUtil yuiUtil = new YuiUtil();
	SetProperties set = new SetProperties();
	//flag
	private boolean isName;
	
	Window(String s){
		super(s);
        set.init();     
		//content
		Container con = this.getContentPane();
		con.setLayout(new GridLayout(2,1));
		this.setLocation(100,100);
        this.setSize(300,300);
        panel1= new Tip();
        panel2 = new FileChoose();
        
        con.add(panel1);
        con.add(panel2);
        
        String[] nn = {set.readInputPath(),set.readOutputPath()};
        if(nn != null){
        	panel2.inPath.setText(nn[0]);
        	panel2.outPath.setText(nn[1]);
        }
        panel2.inBut.addActionListener(this);
        panel2.outBut.addActionListener(this);
        panel2.submit.addActionListener(this);
        panel2.jsBut.addActionListener(this);
        
        setBounds( 100,100,300,300);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 //Container conPane=panel2.getRootPane();
	        if(e.getActionCommand().equals("确定")){        	
	        	String info;
	        	set.writePath(panel2.inPath.getText(),panel2.outPath.getText());
	        	info = yuiUtil.model(panel2.inPath.getText(), panel2.outPath.getText());
	        	dialog = new Dialog(this,info);
	            dialog.setVisible(true);
	        }else if(e.getActionCommand().equals("+")){
	        	String norm=System.getProperty("user.dir");
	        	if(!panel2.inPath.getText().equals("")){
	        		norm = panel2.inPath.getText();
	        	}
	        	JFileChooser fd = new JFileChooser(norm);  
	        	fd.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	        	fd.showOpenDialog(null);  
	        	File f = fd.getSelectedFile();  
	        	if(f != null){
	        		panel2.inPath.setText(f.getPath());
	        	}
	        }else if(e.getActionCommand().equals("-")){
	        	String norm=System.getProperty("user.dir");
	        	if(!panel2.outPath.getText().equals("")){
	        		norm = panel2.outPath.getText();
	        	}
	        	JFileChooser fd = new JFileChooser(norm);  
	        	fd.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);  
	        	fd.showOpenDialog(null);  
	        	File f = fd.getSelectedFile();  
	        	if(f != null){
	        		panel2.outPath.setText(f.getPath());
	        	}
	        }else if(e.getActionCommand().equals("js替换列表")){
	        	jsDialog = new JsDialog(this,"");
	        	jsDialog.setVisible(true);
	        }
	}
}
