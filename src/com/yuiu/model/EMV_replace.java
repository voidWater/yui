package com.yuiu.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EMV_replace {
	private static List<String> files= new ArrayList<String>(
			Arrays.asList("index","sale","pub","pubpos","rpr","set","check","bind","calendar","fund","hydj","scan"));
	private static String mVersion = "0";
	private static SetProperties set = new SetProperties();
	
	/**从html文件获取old version
	 * 
	 * @param originFolder：要获得version的html文件路径
	 * @return ：version
	 * @throws IOException
	 */
	public static  String getVersion(String originFolder) throws IOException{
		File files = new File(originFolder);
		String version = "-1";
		if(files.exists()){
			File[] fileList = files.listFiles();
            for (File file : fileList) {
                if (!file.isDirectory()) {
                    if(file.getName().lastIndexOf(".html")!=-1){
                    	String oneLine;
                    	FileReader fr=new FileReader(originFolder+"\\"+file.getName());
                    	BufferedReader br=new BufferedReader(fr);
                    	 while((oneLine=br.readLine())!=null){  //每次读取 1 行
                    		 //System.out.println(oneLine);
                    		 version = judgeStr(oneLine);
                             if(version!="0"){
                            	 fr.close();
                            	 br.close();
                            	 return mVersion = version;
                             }
                        }		
                    }
                 }
            }
		}
		return version;
	}
	
	
	/** 从String中截取version
	 * 
	 * @param oneLine:目标字符串
	 * @return:version
	 */
	private static String judgeStr(String oneLine) {
		// TODO Auto-generated method stub
		for(String file : files){
			if(oneLine.indexOf(file)>-1){
				//String name = oneLine.substring(oneLine.indexOf(file),oneLine.indexOf(".",oneLine.indexOf(file)));
				int begin = oneLine.indexOf(".js");
				int end = oneLine.indexOf("\"", begin);
				//筛出version
				if(begin!=-1&&end!=-1&&end-begin>3){
					System.out.println(oneLine);
					oneLine = oneLine.substring(begin+4, end);
					return oneLine;
				}
			}
		}
		return "0";
	}
	
	/**从配置文件获取version
	 * 
	 * @return version
	 */
	public static String getVersion(){
		return mVersion = set.readVersion();
	}
	/**
	 * 更新version,外部接口
	 * @param orgin
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String replaceVersion(String orgin,String filePath) throws IOException {
		// TODO Auto-generated method stub
		String version ="-1";
		files = new ArrayList<String>(Arrays.asList(set.readJs().split(",")));
		orgin=orgin.substring(0, orgin.length()-2);
		orgin=orgin.substring(0, orgin.lastIndexOf("\\"));
		filePath=filePath.substring(0, filePath.length()-2);
		filePath=filePath.substring(0, filePath.lastIndexOf("\\"));
		if(orgin.indexOf(".css")==-1){
			version = getVersion();
			updataVersion(filePath);
		}
		return version;
	}
	/**更新version
	 * 
	 * @param AimFolder
	 * @return
	 * @throws IOException
	 */
	public static int updataVersion(String AimFolder)throws IOException{	
		File files = new File(AimFolder);
		StringBuffer sb;
		if(files.exists()){
			File[] fileList = files.listFiles();
            for (File file : fileList) {
                if (!file.isDirectory()) {
                    if(file.getName().lastIndexOf(".html")!=-1){
                    	sb=new StringBuffer();
                    	String oneLine;
                    	//FileReader fr=new FileReader(AimFolder+"\\"+file1.getName());
                    	//BufferedReader br=new BufferedReader(fr);
                    	File filePath = new File(AimFolder+"\\"+file.getName());
                    	InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath),"utf-8");
                    	BufferedReader br = new BufferedReader(isr);                    	
                    	while((oneLine=br.readLine())!=null){  //每次读取 1 行
                    			if(oneLine.indexOf("<script")>-1){
                    				oneLine=isScript(oneLine);
                    			}
                    			
                    			sb.append(oneLine).append( "\r\n");
                    			
                    	}
                    	isr.close();
                    	br.close();
                    	BufferedWriter bw=new BufferedWriter(new FileWriter( AimFolder+"\\"+file.getName()));
                    	bw.write(sb.toString());
                    	bw.close();
                     }     	 		
                 }
             }
           }
		return 0;
	}
	/**
	 * 判断
	 * @param oneLine
	 * @return
	 */
	private static String isScript(String oneLine) {
		// TODO Auto-generated method stub
		for(String file : files){
			if(oneLine.indexOf(file)>-1){
				String name = oneLine.substring(oneLine.indexOf(file),oneLine.indexOf(".",oneLine.indexOf(file)));
				int start = oneLine.indexOf(file);
				int end = oneLine.indexOf(mVersion);
				String old =null;
				if(oneLine.indexOf(mVersion)==-1){

					 old = oneLine.substring(oneLine.indexOf(name), oneLine.indexOf("\"",oneLine.indexOf(".js")));
					 
				}else{
					 old = oneLine.substring(oneLine.indexOf(name), oneLine.indexOf(mVersion)+ mVersion.length());
				}
//				System.out.println(start+":"+end);
//				System.out.println(oneLine);
//				System.out.println(mVersion);
//				System.out.println();
//				String old = oneLine.substring(oneLine.indexOf(file), oneLine.indexOf(mVersion)+ mVersion.length());

				String nEw = name + ".min.js?"+(Integer.parseInt(mVersion)+1);
				//System.out.println(old+":"+nEw);
				oneLine = oneLine.replace(old, nEw);
				//System.out.println(oneLine);
				return oneLine;
			}
		}
		return oneLine;
	}

	

	
	/*public static void main(String arg[]) throws IOException {
		// TODO Auto-generated method stub
		
		
			updataVersion("E:\\yui\\wposjy");
		//	updataVersion("F:\\webpos");
		
	}*/
}
