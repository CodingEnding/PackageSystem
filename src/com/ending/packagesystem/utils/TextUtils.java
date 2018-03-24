package com.ending.packagesystem.utils;

public class TextUtils {
	private TextUtils(){}
	
	public static boolean isEmpty(String str){
		if(str==null||str.equals("")){
			return true;
		}
		return false;
	}
}
