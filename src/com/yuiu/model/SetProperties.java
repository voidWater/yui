package com.yuiu.model;

import java.io.File;
import java.io.IOException;

import com.yuiu.utils.PropertiesUtil;


public class SetProperties {
	private static PropertiesUtil p= new PropertiesUtil();
	
	//默认js列表
	private String jsList = "wsIndex,wspubpos,wsrpt,wssale,wslxc,wsset,wscheck,wsbind,index,sale,pub,pubpos,rpt,set,check,bind,calendar,fund,hydj,scan,gps,zjm";
	/**
	 * 获得项目路径
	 * @return
	 */
	private String getYuiSetPath(){ 
		return  System.getProperty("user.dir")+"\\yui_js.properties";
		//return "E:\\develop\\yui\\yui_js.properties";
	}
	/**
	 * 初始化配置文件,yui_js.properties
	 */
	public  void init(){
		File f = new File(getYuiSetPath());
		if(!f.exists()){
			try {
				f.createNewFile();
				p.setProperty(getYuiSetPath(), "js", jsList);
				p.setProperty(getYuiSetPath(), "version", "0");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("create yuiset err");
				e.printStackTrace();
			}
		}
	}
	/**获得js属性
	 * @return
	 */
	public String readJs(){
		return p.getProperty(getYuiSetPath(), "js");
	}
	/**
	 * 更改js属性
	 * @param js 
	 */
	public void writeJs(String js){
		p.setProperty(getYuiSetPath(), "js", js);
	}
	/**
	 * 获得version
	 * @return
	 */
	public String readVersion(){
		return p.getProperty(getYuiSetPath(), "version");
	}
	/**
	 * 写入version
	 * @param v
	 */
	public void writeVersion(String v){
		p.setProperty(getYuiSetPath(), "version", v);
	}
	/**
	 * 获得保存的转换路径
	 * @return
	 */
	public String readInputPath(){
		return p.getProperty(getYuiSetPath(), "infile");
	}
	/**
	 * 获得保存的输出路径
	 * @return
	 */
	public String readOutputPath(){
		return p.getProperty(getYuiSetPath(), "outfile");
	}
	/**
	 * 保存转换和输出路径
	 * @param in
	 * @param out
	 */
	public void writePath(String in,String out){
		p.setProperty(getYuiSetPath(), "infile", in);
		p.setProperty(getYuiSetPath(), "outfile", out);
	}
}
