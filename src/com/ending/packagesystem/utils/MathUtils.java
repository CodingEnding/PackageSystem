package com.ending.packagesystem.utils;

/**
 * 与数字、计算相关的工具类
 * @author CodingEnding
 */
public class MathUtils {
	private MathUtils(){}

	/**
	 * 将小于0的数返回为0
	 * @param num
	 * @return
	 */
	public static int positiveNum(int num){
		if(num<0){
			return 0;
		}
		return num;
	}
	
	/**
	 * 如果num小于指定的数，就返回指定的数
	 * @param num
	 * @param atleast
	 * @return
	 */
	public static int leastNum(int num,int leastNum){
		if(num<leastNum){
			return leastNum;
		}
		return num;
	}
	
	/**
	 * 如果num不是合法的数字就返回默认值defaultNum
	 * @param num 字符串形式的Int
	 * @param defaultNum 默认值
	 * @return
	 */
	public static int legalIntNum(String numStr,int defaultNum){
		int end=0;
		try {
			end=Integer.valueOf(numStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			end=defaultNum;//返回默认值
		} 
		return end;
	}
}
