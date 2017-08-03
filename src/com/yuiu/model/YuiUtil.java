package com.yuiu.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;



public class YuiUtil {
	
	static String yuiPath ="E:\\develop\\yui\\yuicompress.jar";	
	static String encoding = "utf-8";
	
	private static List<String> jsfiles=null;
	String[] file;
	static List<String> js;
	static List<String> css;
	static String path;
	static String outPath;
	private String[] fileNames;
	private static boolean updataflag = false;
	private static boolean updataflag1 = false;
	private SetProperties set = new SetProperties();
	static List<String> delfiles = new ArrayList<String>();
	//获取yui.jar路径
	private  String getYuiPath(){		
		return "E:\\yuitest\\yuicompress.jar";
		//return  System.getProperty("user.dir")+"\\yuicompress.jar";
	}
	
	
	//对css，js文件分类
	private static void judge(String fileName){
		
		String[] file;
		js = new ArrayList<String>();
		css = new ArrayList<String>();
		fileName.trim();
		
		int i = fileName.lastIndexOf("\\");
		path = fileName.substring(0, i+1);
		fileName = fileName.substring(i+1, fileName.length());
		file =fileName.split(",");
		for(String cmd : file){
			if(cmd.endsWith(".js")){
				js.add(cmd);
			}else if(cmd.endsWith(".css")){
				
				css.add(cmd);
			}
		}
	}
	
	//获取outPath
	private static String strPath(String outPath,String fileName){
		//当path路径为空时，默认为输出到压缩文件所在文件夹
		if(outPath.length()==0){
			
			if(fileName.endsWith(".js")||fileName.endsWith(".css")){
				int i = fileName.lastIndexOf("\\");
				outPath = fileName.substring(0, i+1);
				return outPath;
			}else{
				if(fileName.endsWith("\\")){
				return fileName;
				}
				return fileName + "\\";
			}
		}
		//添加跳转\
		if(outPath.charAt(outPath.length()-1)=='\\'){
		return outPath;
		}
		return outPath+"\\";
	}
	
	
	
	//删除文件
	public void delfiles(String name){
		System.out.println(name);
		
		for(String f : delfiles){
			//System.out.println( f);
			String df = f.replace(".js", ".min.js");
			File file = new File(name +"\\js\\"+ f);
			//File dfile = new File(name +"\\js\\"+ df);
			if(file.exists()){
				file.delete();
			}
		}
	}
	
	public  String model(String inPath,String outPath){
		yuiPath = getYuiPath();
		jsfiles = new ArrayList<String>(Arrays.asList(set.readJs().split(",")));
		StringBuilder sb = new StringBuilder();
		String outPathl = strPath(outPath,inPath);
		if(inPath.endsWith("wposjy")){
			updataflag1 = true;
			File file = new File(inPath+"\\js");
			System.out.println(file.getPath()+","+outPathl);
			compressSpJS(yuiPath,sb,file,encoding,outPathl+"js\\");
		}else if(!inPath.endsWith(".js")&&!inPath.endsWith(".css")&&(inPath.indexOf("*.js")==-1&&inPath.indexOf("*.css")==-1)){
			
			File file = new File(inPath);
			compressAllJS(yuiPath, sb, file, encoding,outPathl);
			compressAllCSS(yuiPath, sb, file, encoding,outPathl);
		}else if(inPath.indexOf("*.js")>0){
			
			judge(inPath);
			File file = new File(path);
			compressAllJS(yuiPath, sb, file, encoding,outPathl);
		}else if(inPath.indexOf("*.css")>0){
			
			judge(inPath);
			File file = new File(path);
			compressAllCSS(yuiPath, sb, file, encoding,outPathl);
		}else{
			
		//指定文件
			judge(inPath);
			
			for(int i = 0;i<js.size();i++){
				File file = new File(path+js.get(i));
				String outfile = outPathl+js.get(i);
				compressJS(yuiPath, sb, file, encoding,outfile);
				
			}
			for(int i = 0;i<css.size();i++){
				File file = new File(path+css.get(i));
				String outfile = outPathl+js.get(i);
				compressCSS(yuiPath, sb, file, encoding,outfile);
			}
			
		}
		//后台信息
		String[] res = sb.toString().split("\n");
		Runtime runTime = Runtime.getRuntime();
		Process p = null;
		Date startTime = new Date();
		Long count = 0L;
		for (String cmd : res) {
			if (!cmd.trim().equals("")) {
				System.out.println(cmd);
				try {
					p=runTime.exec(cmd);
					p.waitFor();
					count++;
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		Date endTime = new Date();
		Long cost = endTime.getTime() - startTime.getTime();
		//System.out.println(inPath+":" + "压缩完成，耗时：" + cost + "ms，共压缩文件个数：" + count);
		
		
		String version ="";
		if(count>0&&updataflag == true&&updataflag1==true){
			try {				
				version = version+",version:"+(Integer.parseInt(EMV_replace.replaceVersion(inPath+"\\js",outPathl+"\\js"))+1);
				System.out.println("as:"+version);
				System.out.println(set.readVersion());
				set.writeVersion((Integer.parseInt(set.readVersion())+1)+"");
				System.out.println(set.readVersion());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			updataflag1 = false;
		}
		updataflag = false;
		delfiles(outPath);
		System.out.println("压缩完成，共压缩文件个数：" + count + version);
		return "压缩完成，共压缩文件个数：" + count + version;

	}
	
	/**
	 * 压缩wposjy目录下js文件夹中的所有js并更改版本号
	 * @param yuiPath
	 * @param sb
	 * @param f
	 * @param encoding
	 * @param outPath
	 */
	public static void compressSpJS(String yuiPath, StringBuilder sb, File f, String encoding,String outPath) {

		if (f.isDirectory()) {
			File[] files = f.listFiles();
			// 如果某个文件夹是空文件夹，则跳过
			if (files == null) {
				return;
			}
			for (File file : files) {		
				if(!file.isDirectory()){
				compressSpJS(yuiPath, sb, file, encoding,outPath);
				}
			}
		} else {
			String fileName = f.getName();
			
				String fn = fileName.substring(0, fileName.indexOf("."));
				for(String n : jsfiles){
					if(fn.equals(n)){					
						updataflag=true;
						if (fileName.endsWith(".js") && !fileName.endsWith(".min.js")) {
							delfiles.add(fileName);
							sb.append("java -jar ");
							sb.append(yuiPath);
							sb.append(" --type js --charset ");
							sb.append(encoding + " " + f.getPath());
							sb.append(" -o " + (outPath+f.getName()).replace(".js", ".min.js"));
							sb.append("\n");
						}
					}
				}

		}
	}
	
	/**压缩单个js文件
	 * 
	 */
	public static void compressJS(String yuiPath, StringBuilder sb, File f, String encoding,String outPath) {
		if (f.isDirectory()){
			File[] files = f.listFiles();
			// 如果某个文件夹是空文件夹，则跳过
			if (files == null) {
				return;
			}			
		} else {
			
			String fileName = f.getName();
			
			if(updataflag==false){
				String fn = fileName.substring(0, fileName.indexOf("."));
				for(String n : jsfiles){
					//System.out.println( fileName.substring(0, fileName.indexOf("."))+n);
					if(fn.equals(n)){
						//System.out.println( fileName.substring(0, fileName.indexOf("."))+fn);
						updataflag=true;
					}
				}
			}
			if (fileName.endsWith(".js") && !fileName.endsWith(".min.js")) {
				sb.append("java -jar ");
				sb.append(yuiPath);
				sb.append(" --type js --charset ");
				sb.append(encoding + " " + f.getPath());
				sb.append(" -o " + outPath.replace(".js", ".min.js"));
				sb.append("\n");
			}
		}
	}
	/**压缩单个css文件
	*/
	public static void compressCSS(String yuiPath, StringBuilder sb, File f, String encoding,String outPath) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			// 如果某个文件夹是空文件夹，则跳过
			if (files == null) {
				return;
			}
		} else {
			String fileName = f.getName();
			if (fileName.endsWith(".css") && !fileName.endsWith(".min.css")) {
				sb.append("java -jar ");
				sb.append(yuiPath);
				sb.append(" --type css --charset ");
				sb.append(encoding + " " + f.getPath());
				sb.append(" -o " + outPath.replace(".css", ".min.css"));
				sb.append("\n");
			}
		}
	}
	
	/**
	 * 压缩所有js文件
	 * @param yuiPath：yuicompressor.jar所在文件路径
	 * @param sb：command
	 * @param f：js文件所在文件夹
	 * @param encoding：编码方式
	 * @param outPath：输出路径
	 */
	public static void compressAllJS(String yuiPath, StringBuilder sb, File f, String encoding,String outPath) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			// 如果某个文件夹是空文件夹，则跳过
			if (files == null) {
				return;
			}
			for (File file : files) {
				if(!file.isDirectory()){
				compressAllJS(yuiPath, sb, file, encoding,outPath);
				}
			}
		} else {
			String fileName = f.getName();
			if(updataflag==false){
				String fn = fileName.substring(0, fileName.indexOf("."));
				for(String n : jsfiles){
					if(fn.equals(n)){
						updataflag=true;
					}
				}
			}	
			if (fileName.endsWith(".js") && !fileName.endsWith(".min.js")) {
				delfiles.add(fileName);
				sb.append("java -jar ");
				sb.append(yuiPath);
				sb.append(" --type js --charset ");
				sb.append(encoding + " " + f.getPath());
				sb.append(" -o " + (outPath+f.getName()).replace(".js", ".min.js"));
				sb.append("\n");
			}
		}
	}

	/**
	 * 压缩所有css文件
	 * @param yuiPath: yuicompressor.jar文件目录
	 * @param sb :command
	 * @param f :需要转换css文件所在的文件夹
	 * @param encoding:编码方式
	 * @param outPath：输出路径
	 */
	public static void compressAllCSS(String yuiPath, StringBuilder sb, File f, String encoding,String outPath) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			// 如果某个文件夹是空文件夹，则跳过
			if (files == null) {
				return;
			}
			for (File file : files) {
				if(!file.isDirectory()){
					compressAllCSS(yuiPath, sb, file, encoding,outPath);
					}
			}
		} else {
			String fileName = f.getName();
			if (fileName.endsWith(".css") && !fileName.endsWith(".min.css")) {
				sb.append("java -jar ");
				sb.append(yuiPath);
				sb.append(" --type css --charset ");
				sb.append(encoding + " " + f.getPath());
				sb.append(" -o " + (outPath+f.getName()).replace(".css", ".min.css"));
				sb.append("\n");
			}
		}
	}

}
