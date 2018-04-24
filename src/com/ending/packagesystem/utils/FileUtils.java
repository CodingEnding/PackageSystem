package com.ending.packagesystem.utils;

import java.io.File;

/**
 * 与文件相关的工具类
 * @author CodingEnding
 */
public class FileUtils {
	private FileUtils(){}
	
	/**
    * @param path 路径
    * @return  返回文件大小
    */
   public static long getFileSize(String path) {
       if (TextUtils.isEmpty(path)) {
           return -1;
       }

       File file = new File(path);
       return getFileSize(file);
   }
   
   /**
    * @param file 文件对象
    * @return  返回文件大小
    */
   public static long getFileSize(File file) {
       if (file==null) {
           return -1;
       }
       return (file.exists() && file.isFile() ? file.length() : -1);
   }
}
