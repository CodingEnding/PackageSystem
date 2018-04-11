package com.ending.packagesystem.service;

import java.sql.Timestamp;

import com.ending.packagesystem.config.Constants;
import com.ending.packagesystem.dao.DeviceDao;
import com.ending.packagesystem.dao.UserDao;
import com.ending.packagesystem.po.DevicePO;
import com.ending.packagesystem.po.UserPO;
import com.ending.packagesystem.utils.SessionUtils;

/**
 * 提供与用户相关的服务
 * @author CodingEnding
 */
public class UserService {
	private UserDao userDao=new UserDao();
	private DeviceDao deviceDao=new DeviceDao();
	
	/**
	 * 注册新用户
	 * @param userPO
	 */
	public int register(UserPO userPO){
		int id=Constants.REGISTER_ERROR_ID;
		if(userDao.isExsitWithEmail(userPO.getEmail())){//先判断邮箱是否已经注册
			id=Constants.REGISTER_EMAIL_EXSIT;
			return id;
		}
		boolean isSucceed=userDao.insert(userPO);
		if(isSucceed){
			id=userDao.findIdByEmail(userPO.getEmail());
			return id;
		}
		return id;
	}
	
	/**
	 * 登录（包括绑定设备Device）
	 * @param email
	 * @param password
	 * @return UserPO
	 */
	public UserPO login(String email,String password,String deviceType,
			String systemVersion,String deviceFinger){
		UserPO userPO=new UserPO();
		userPO.setId(Constants.LOGIN_ERROR_ID);//默认设置登录失败的Id
		
		if(userDao.isExsitByEmailAndPass(email,password)){//登录成功
			String sessionToken=SessionUtils.createSession(email);
			Timestamp sessionExpire=SessionUtils.createSessionExpire();
			
			//更新User的sessionToken
			userDao.updateSessionByEmail(email,sessionToken,sessionExpire);
			userPO=userDao.findUserByEmail(email);
			
			//插入或更新Device
			DevicePO devicePO=new DevicePO();
			devicePO.setDeviceType(deviceType);
			devicePO.setSystemVersion(systemVersion);
			devicePO.setDeviceFinger(deviceFinger);
			devicePO.setUserId(userPO.getId());
			if(!deviceDao.isExsitWithFinger(deviceFinger)){//还不存在就插入
				deviceDao.insert(devicePO);
			}else{//已经存在就更新或绑定Device
				deviceDao.updateByFinger(devicePO,deviceFinger);
			}
		}
		return userPO;
	}
	
}
