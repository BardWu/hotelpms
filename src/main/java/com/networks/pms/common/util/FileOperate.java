package com.networks.pms.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;

import org.apache.log4j.Logger;

public class FileOperate {
	public static int countForFileName = 0;
	public static Logger log = Logger.getLogger(FileOperate.class);
	
	public FileOperate() {
	}
	
	/**
	 * 
	 * @Description 将文件重命名
	 * @return void  
	 * @author  ssp
	 * @date 2016-10-26
	 */
	public static  void setFileNewName(String path){
		
	   	 File file = new File(path);
	   	 String valueOf = String.valueOf(System.currentTimeMillis());
         File newfile=new File(path+valueOf);
	     file.renameTo(newfile); 
	}
	
	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            String 如 c:/fqf
	 * @return boolean
	 */
	public static int newFolder(String folderPath) {
		
		int returnNum = 0;
		
		try {
			
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			
			if (!myFilePath.exists()) {
				
				myFilePath.mkdirs();
				
			}
			
			returnNum = 1;
			
		} catch (Exception e) {
			returnNum = 0;
			log.error("新建目录操作出错");
			e.printStackTrace();
		}
		
		return returnNum;
		
	}
	/**
	 * 根据文件名称得到后缀名称
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName){
		
		String type = "";
		
		if(!(fileName == null) && fileName != ""){
			
			type = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			
		}
		
	   return type;
	}

	/**
	 * 返回根据规则处理后的文件名称
	 * @param
	 * @return
	 */
	public static String getFileName(){
		String fileName = "";
		long currentTime = System.currentTimeMillis();
		long weekTime = 7 * 24 * 60 * 60 * 1000;
		long dateTime = currentTime % weekTime;
		long fileNameLong = dateTime * 100000 + countForFileName;
		countForFileName ++;
		if (countForFileName > 100000) {
			countForFileName = 0;
		}
		fileName = Long.toHexString(fileNameLong);
		return fileName; 
	}
	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String 文件内容
	 * @return boolean
	 */
	public void newFile(String filePathAndName, String fileContent) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();

		} catch (Exception e) {
			log.error("新建目录操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param
	 * @return boolean
	 */
	public static int delFile(String filePathAndName) {
		
        int num = 0;
		try {
			
			if(filePathAndName != null && !(filePathAndName.equals(""))){
				
				String filePath = filePathAndName;
				
				filePath = filePath.toString();
				
				File myDelFile = new File(filePath);
				
				if(myDelFile.exists()){
					
					if(myDelFile.isFile()){
						
						myDelFile.delete();
					}
					
				}
				
			}
			
			num = 1;
			
		} catch (Exception e) {
			
			num = 0;
			log.error("删除文件操作出错");
			e.printStackTrace();
			
		}
		
		return num;

	}
	

	/**
	 * 删除文件夹
	 * 文件夹路径及名称 如c:/fqf
	 * @param
	 * @param
	 * @return boolean
	 */
	public static int delFolder(String folderPath) {
		int num = 0;
		
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹
			num = 1;
		} catch (Exception e) {
			num = 0;
			log.error("删除文件夹操作出错");
			e.printStackTrace();

		}
		
		return num;

	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				OutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				
				fs.close();
				
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			log.error("复制整个文件夹内容操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath); 
		delFile(oldPath);

	}
    
	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);

	}
	
	
	/**
	 * 是否存在该文件
	 *  文件路径及名称 如c:/fqf.txt
	 * @param
	 * @return boolean
	 */
	public static boolean exitsFile(String filePath) {

		try {
			
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				
				return false;
			}
			
            return true;
		} catch (Exception e) {
			
			log.error("检查文件是否存在出错");
			e.printStackTrace();
			return false;
		}

	}
	/**
	 * 
	 * @param oldUrl
	 * @param newUrl
	 * @return
	 */
	public static int getDownloadImage(String oldUrl,String newUrl){
		
		int num = 0;
		
		 try {
			
           URL url = new URL(oldUrl);	
	   	   File outFile = new File(newUrl);
	   	   
	   	   OutputStream os = new FileOutputStream(outFile);		
	   	   InputStream is = url.openStream();			
	   	   byte[] buff = new byte[1024];		
	   	   while(true) {			
	   			int readed = is.read(buff);				
	   			if(readed == -1) {			
	   				break;				
	   			}	
	   			byte[] temp = new byte[readed];				
	   			System.arraycopy(buff, 0, temp, 0, readed);		
	   			os.write(temp);			
	   	    }			
				is.close();                          
				os.close();
			    	
			} catch (Exception e) {
				e.printStackTrace();
				num = 1;
			}
		 
		 return num;
	}
   
	public static void main(String[] args) {
		
		/*String url = "http://mmbiz.qpic.cn/mmbiz/NBCNQ6UNqicd2iaTcrJqHM0QibBw7RI3MVRyxpkOQaQaJOA2jA8NLOHKibfVNicaoicIFDaBMHHtnUn4ibHgIhcdqQ9lg/0";
		
		getDownloadImage(url, "d:/data/hsms/test.jpg");*/
		String str = "http://192.168.1.188/stw/images/20160814/33db29ec33d5.jpg";
		System.out.println(str.substring(str.lastIndexOf("images")+7,str.length()));
		
	}
}
