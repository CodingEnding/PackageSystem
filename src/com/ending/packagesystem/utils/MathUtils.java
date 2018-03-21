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
	
}
