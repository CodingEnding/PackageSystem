package com.ending.packagesystem.po;

import java.sql.Timestamp;

/**
 * 服务器各个表的状态表（TableStatus）
 * @author CodingEnding
 */
public class TableStatusPO {
	private int id;
	private String tableName;
	private String remark;//备注
	private Timestamp updateTime;//更新时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}
