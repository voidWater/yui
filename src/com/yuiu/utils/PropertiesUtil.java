package com.yuiu.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
	Properties pps = new Properties();
	/**
	 * 获得属性
	 * @param fileName
	 * @param key
	 * @return
	 */
	public String getProperty(String fileName,String key){
		
		InputStream in;
		String result;
		try {
			in = new FileInputStream(fileName);
			pps.load(in);
			result=pps.getProperty(key);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	/**
	 * 设置属性
	 * @param fileName
	 * @param key
	 * @param value
	 * @return
	 */
	public String setProperty(String fileName,String key ,String value){
		
		try {
			
			OutputStream out = new FileOutputStream(fileName);	
			pps.setProperty(key, value);
			pps.store(out, "Update '" + value + "' value");
			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getProperty(fileName,key);
	}
	/**
	 * 获得所有属性
	 * @param fileName
	 * @return
	 */
	public Map<String,String> getProperties(String fileName){
		
		Map<String,String> m = new HashMap<String,String>();
		try {
			InputStream input = new FileInputStream(fileName);
			pps.load(input);
			Enumeration en =  pps.propertyNames();
			while(en.hasMoreElements()){
				String key = (String) en.nextElement();
				String value = pps.getProperty(key);
				m.put(key, value);
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return m;
	}

}
