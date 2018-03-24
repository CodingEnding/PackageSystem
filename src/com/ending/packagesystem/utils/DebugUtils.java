package com.ending.packagesystem.utils;

/**
 * 调试工具
 * @author CodingEnding
 */
public class DebugUtils {
	public static final boolean DEBUG_MODE=true;//是否处于调试模式
	
	private DebugUtils(){}
	
	/**
	 * 普通换行打印
	 * @param msg
	 */
	public static void println(String msg){
		if(DEBUG_MODE){
			System.out.println(msg);
		}
	}
	
	/**
	 * 带有标签（一般为类名）的普通换行打印
	 * @param msg
	 */
	public static void println(String tag,String msg){
		if(DEBUG_MODE){
			System.out.println(tag+":"+msg);
		}
	}
	
	/**
	 * 普通打印
	 * @param msg
	 */
	public static void print(String msg){
		if(DEBUG_MODE){
			System.out.print(msg);
		}
	}
	
	/**
	 * 换行打印错误信息
	 * @param msg
	 */
	public static void errorln(String msg){
		if(DEBUG_MODE){
			System.err.println(msg);
		}
	}
	
	/**
	 * 带有标签（一般为类名）换行打印错误信息
	 * @param msg
	 */
	public static void errorln(String tag,String msg){
		if(DEBUG_MODE){
			System.err.println(tag+":"+msg);
		}
	}
	
}
