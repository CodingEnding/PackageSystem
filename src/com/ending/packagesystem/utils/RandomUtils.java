package com.ending.packagesystem.utils;

import java.util.Random;

/**
 * 随机数工具类
 * @author CodingEnding
 */

public class RandomUtils {
	private RandomUtils(){}
	
	/**
	 * 生成指定长度的随机码
	 * @param length
	 * @return
	 */
	public static String createRandomCode(int length){
		Random random=new Random();
		String code="";
		for(int i=0;i<length;i++){
			code+=random.nextInt(9);
		}
		return code;
	}
}
