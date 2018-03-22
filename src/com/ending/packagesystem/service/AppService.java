package com.ending.packagesystem.service;

import com.ending.packagesystem.dao.AppDao;
import com.ending.packagesystem.po.AppPO;

/**
 * 提供与App相关的服务
 * @author CodingEnding
 *
 */
public class AppService {
	private AppDao appDao=new AppDao();
	
	/**
	 * 根据id返回应用
	 * @param id
	 * @return
	 */
	public AppPO getAppById(int id){
		return appDao.findAppById(id);
	}
	
	/**
	 * 根据name返回应用
	 * @param name
	 * @return
	 */
	public AppPO getAppByName(String name){
		return appDao.findAppByName(name);
	}
	
}
