package com.ending.packagesystem.service;

import java.util.List;

import com.ending.packagesystem.dao.DeviceDao;
import com.ending.packagesystem.dao.UserDao;
import com.ending.packagesystem.po.DevicePO;
import com.ending.packagesystem.utils.TextUtils;
import com.sun.istack.internal.Nullable;

/**
 * 提供与Device相关的服务
 * @author CodingEnding
 */
public class DeviceService {
	private DeviceDao deviceDao=new DeviceDao();
	private UserDao userDao=new UserDao();
	
	/**
	 * 插入或更新设备信息
	 * @param sessionToken 用户令牌
	 */
	public boolean addOrUpdate(String email,String sessionToken,String deviceType,
			String systemVersion,String deviceFinger){
		boolean isSucceed=false;
		
		DevicePO devicePO=new DevicePO();
		devicePO.setDeviceType(deviceType);
		devicePO.setSystemVersion(systemVersion);
		devicePO.setDeviceFinger(deviceFinger);
		
		if(TextUtils.isEmpty(email)){//此时并没有用户信息，直接插入或更新设备信息即可
			if(!deviceDao.isExsitWithFinger(deviceFinger)){//还不存在就插入
				isSucceed=deviceDao.insertWithOutUserId(devicePO);
			}else{
				isSucceed=deviceDao.updateByFingerWithOutUserId(devicePO,deviceFinger);
			}
		}
		else{//此时需要判断用户令牌是否正确（因为这时涉及到用户的个人信息）
			if(userDao.isSessionValidWithEmail(email,sessionToken)){
				devicePO.setUserId(userDao.findIdByEmail(email));
				if(!deviceDao.isExsitWithFinger(deviceFinger)){
					isSucceed=deviceDao.insert(devicePO);
				}else{
					isSucceed=deviceDao.updateByFinger(devicePO,deviceFinger);
				}
			}
		}
		return isSucceed;
	}
	
	/**
	 * 获取用户设备信息列表
	 * @param email
	 * @param sessionToken 用户令牌
	 */
	public List<DevicePO> getDeviceList(String email,String sessionToken){
		if(userDao.isSessionValidWithEmail(email,sessionToken)){//先对用户令牌进行检测
			int userId=userDao.findIdByEmail(email);
			return deviceDao.findAllByUserId(userId);
		}
		return null;
	}
	
}
