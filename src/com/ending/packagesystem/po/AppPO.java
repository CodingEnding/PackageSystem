package com.ending.packagesystem.po;

/**
 * 应用表（App）
 * @author CodingEnding
 */
public class AppPO {
	private int id;
	private String name;
	private int freeFlowType;//是否在免流应用范围内（0、1）
	private int typeId;//应用类型Id（外键）
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFreeFlowType() {
		return freeFlowType;
	}
	public void setFreeFlowType(int freeFlowType) {
		this.freeFlowType = freeFlowType;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
}
