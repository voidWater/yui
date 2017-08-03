package com.yuiu.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;



import com.yuiu.utils.WindowUtil;

public class Dialog extends JDialog implements ActionListener{
	JLabel version;
	JLabel title;
	JButton done;
	Dialog(Frame fileChoose,String s){
		super(fileChoose,"js替换列表",true);
		Container con = this.getContentPane();
		con.setLayout(new GridLayout(2,1));
		title = new JLabel(s);
		done = new JButton("关闭");
		done.addActionListener(this);
		con.add(title,"North");
		con.add(done,"South");
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 200;
		int height = 200;
		this.setBounds(WindowUtil.getWith()/2,WindowUtil.getHeight()/2,width,height);
		setResizable(false);
		//con.setVisible(true);
		//this.pack();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		setVisible(false);
        dispose();
	}

	

}
