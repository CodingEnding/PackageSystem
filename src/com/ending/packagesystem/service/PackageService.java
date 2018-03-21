package com.ending.packagesystem.service;

import java.util.List;

import com.ending.packagesystem.dao.PackageDao;
import com.ending.packagesystem.po.PackagePO;

/**
 * 提供与套餐相关的服务
 * @author CodingEnding
 */
public class PackageService {
	private PackageDao packageDao=new PackageDao();
	
	/**
	 * 获取所有套餐
	 * @return List<PackagePO>
	 */
	public List<PackagePO> getAllPackage(){
		return packageDao.findAll();
	}
	
	/**
	 * 获取指定id的套餐
	 * @param id
	 * @return 指定套餐
	 */
	public PackagePO getPackageById(int id){
		return packageDao.findPackageById(id);
	}
}
