package com.ending.packagesystem.po;

/**
 * 套餐流量特权表（FlowPrivilege）
 * @author CodingEnding
 */
public class FlowPrivilegePO {
	private int id;
	private int appId;//（外键）免流的应用Id
	private int packageId;//（外键）免流的套餐列表Id
	private int onlyProvince;//（外键）是否仅在省内有效
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	public int getOnlyProvince() {
		return onlyProvince;
	}
	public void setOnlyProvince(int onlyProvince) {
		this.onlyProvince = onlyProvince;
	}
	
}
