package com.yuiu.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;

import com.yuiu.model.SetProperties;

public class JsDialog extends JDialog implements ActionListener{
	JTextArea title;
	JButton done;
	SetProperties set;
	JsDialog(Frame fileChoose,String s){
		super(fileChoose,"js列表",true);
		Container con = this.getContentPane();
		//con.setLayout(new GridLayout(3,1));
		title = new JTextArea("",5,20);
		done = new JButton("保存并关闭");
		set = new SetProperties();
		title.setText(set.readJs());
		title.setLineWrap(true);
		done.addActionListener(this);
		
		done.setBounds(	1, 1, 30, 20);
		con.add(title,"North");
		con.add(done,"South");
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 300;
		int height = 150;
		this.setBounds((d.width-width)/2,(d.height-height)/2,width,height);
//		setResizable(false);
//		con.setVisible(true);
//		this.pack();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		set.writeJs(title.getText());
		setVisible(false);
        //dispose();
	}
}
