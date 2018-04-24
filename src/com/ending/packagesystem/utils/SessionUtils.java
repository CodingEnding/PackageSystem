package com.ending.packagesystem.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

import com.ending.packagesystem.config.Config;

/**
 * 与SessionToken相关的工具类
 * @author CodingEnding
 */
public class SessionUtils {
	private SessionUtils(){}
	
	/**
	 * 创造Session字符串
	 * @param email
	 */
	public static String createSession(String email){
		int randStart=new Random().nextInt(100000);
		int randEnd=new Random().nextInt(100000);
		String sessionToken=randStart+email+randEnd
				+System.currentTimeMillis();
		sessionToken=MD5Utils.encodeString(sessionToken);
		return sessionToken;
	}
	
	/**
	 * 创造Session有效期
	 */
	public static Timestamp createSessionExpire(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.YEAR,Config.SESSION_EXPIRE_YEAR);//推迟到有效期后
		Timestamp timestamp=new Timestamp(calendar.getTimeInMillis());
		return timestamp;
	}
	
}
