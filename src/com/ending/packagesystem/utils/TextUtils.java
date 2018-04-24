package com.ending.packagesystem.utils;

/**
 * 与文本相关的工具类
 * @author CodingEnding
 */
public class TextUtils {
	private TextUtils(){}
	
	public static boolean isEmpty(String str){
		if(str==null||str.equals("")){
			return true;
		}
		return false;
	}
}
