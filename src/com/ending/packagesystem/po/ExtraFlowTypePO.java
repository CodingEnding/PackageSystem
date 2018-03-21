package com.ending.packagesystem.po;

/**
 * 套餐外流量计费方式表 （ExtraFlowType）
 * @author CodingEnding
 */
public class ExtraFlowTypePO {
	private int id;
	private int type;//计费方式标识
	private String remark;//备注
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
