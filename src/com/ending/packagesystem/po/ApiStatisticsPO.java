package com.ending.packagesystem.po;

/**
 * API调用次数统计表（ApiStatistics）
 * @author CodingEnding
 */
public class ApiStatisticsPO {
	private int id;
	private String apiName;
	private String remark;//API的备注
	private int count;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
