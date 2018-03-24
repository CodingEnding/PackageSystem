package com.ending.packagesystem.vo;

import com.ending.packagesystem.po.PackagePO;

/**
 * 封装较少信息的套餐实体类
 * @author CodingEnding
 */
public class SimplePackageVO {
	private int id;
	private String name;
	private String partner;//合作方
	private String operator;//运营商
	private double star;//评分
	private int freeFlowType;//是否有免流范围
	
	/**
	 * 快速构造SimplePackageVO对象
	 * @param packagePO
	 * @return SimplePackageVO
	 */
	public static SimplePackageVO build(PackagePO packagePO){
		return new SimplePackageVO(packagePO.getId(),packagePO.getName(),
				packagePO.getPartner(),packagePO.getOperator(),
				packagePO.getStar(),packagePO.getFreeFlowType());
	}
	
	public SimplePackageVO() {
	}
	
	public SimplePackageVO(int id, String name, String partner, 
			String operator, double star, int freeFlowType) {
		this.id = id;
		this.name = name;
		this.partner = partner;
		this.operator = operator;
		this.star = star;
		this.freeFlowType = freeFlowType;
	}
	
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
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public double getStar() {
		return star;
	}
	public void setStar(double star) {
		this.star = star;
	}
	public int getFreeFlowType() {
		return freeFlowType;
	}
	public void setFreeFlowType(int freeFlowType) {
		this.freeFlowType = freeFlowType;
	}
	
}
