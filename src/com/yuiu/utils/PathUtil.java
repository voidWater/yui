package com.yuiu.utils;

public class PathUtil {
	public static String getPath(){
		return Class.class.getClass().getResource("/").getPath();
	}
}
