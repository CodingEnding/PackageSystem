package com.ending.packagesystem.service;

import com.ending.packagesystem.dao.FlowPrivilegeDao;
import com.ending.packagesystem.po.FlowPrivilegePO;

public class FlowPrivilegeService {
	private FlowPrivilegeDao flowPrivilegeDao=new FlowPrivilegeDao();
	
	/**
	 * 判断指定套餐是否存在指定应用的免流量特权
	 * @param appId
	 * @param packageId
	 * @return
	 */
	public boolean exist(int appId,int packageId){
		FlowPrivilegePO flowPrivilege=new FlowPrivilegePO();
		flowPrivilege.setAppId(appId);
		flowPrivilege.setPackageId(packageId);
		return flowPrivilegeDao.exist(flowPrivilege);
	}
}
