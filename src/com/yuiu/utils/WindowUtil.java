package com.yuiu.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

public class WindowUtil {
	public static Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	public static int getWith(){
		return d.width;
	}
	public static int getHeight(){
		return d.height;
	}
 
}
