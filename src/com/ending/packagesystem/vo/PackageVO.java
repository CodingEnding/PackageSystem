package com.ending.packagesystem.vo;

import com.ending.packagesystem.po.PackagePO;

/**
 * 提供给客户端使用的套餐JSON实体类
 * @author CodingEnding
 */
public class PackageVO {
	private int id;
	private String name;
	private String partner;//合作方
	private String operator;//运营商
	private int monthRent;//月租
	private int packageCountryFlow;//套餐内国内流量（M）
	private int packageProvinceFlow;//套餐内省内流量（M）
	private int packageCall;//套餐内通话时长
	private double extraPackageCall;//套餐外通话费用（元/min）
	private double extraCountryFlow;//套餐外全国流量（元/M）
	private double extraProvinceFlow;//套餐外省内流量（元/M）
	private double extraProvinceOutFlow;//套餐外省外流量（元/M）
	private int extraCountryDayRent;//套餐外全国日租（元）
	private int extraCountryDayFlow;//套餐外全国日租包含流量（M）
	private int extraProvinceInDayRent;//套餐外省内日租（元）
	private int extraProvinceInDayFlow;//套餐外省内日租包含流量（M）
	private int extraProvinceOutDayRent;//套餐外省外日租（元）
	private int extraProvinceOutDayFlow;//套餐外省外日租包含流量（M）
	private int extraFlowType;//套餐外流量的计费方式
	private String privilegeDescription;//特权描述
	private double star;//评分
	private String url;//官方的说明链接
	private String remark;//备注
	private int abandon;//是否停办
	private int freeFlowType;//是否有免流范围
	private double totalConsume;//每月的预计消费
	
	/**
	 * 快速构造方法
	 * @param packagePO
	 * @param extraFlowType 套餐外流量的计费方式
	 * @param totalConsume 每月的预计消费
	 * @return PackageVO
	 */
	public static PackageVO build(PackagePO packagePO,int extraFlowType,double totalConsume){
		PackageVO packageVO=new PackageVO();
		packageVO.setId(packagePO.getId());
		packageVO.setName(packagePO.getName());
		packageVO.setPartner(packagePO.getOperator());
		packageVO.setOperator(packagePO.getOperator());
		packageVO.setMonthRent(packagePO.getMonthRent());
		packageVO.setPackageCountryFlow(packagePO.getPackageCountryFlow());
		packageVO.setPackageProvinceFlow(packagePO.getPackageProvinceFlow());
		packageVO.setPackageCall(packagePO.getPackageCall());
		packageVO.setExtraPackageCall(packagePO.getExtraPackageCall());
		packageVO.setExtraCountryFlow(packagePO.getExtraCountryFlow());
		packageVO.setExtraProvinceFlow(packagePO.getExtraProvinceFlow());
		packageVO.setExtraProvinceOutFlow(packagePO.getExtraProvinceOutFlow());
		packageVO.setExtraCountryDayRent(packagePO.getExtraCountryDayRent());
		packageVO.setExtraCountryDayFlow(packagePO.getExtraCountryDayFlow());
		packageVO.setExtraProvinceInDayRent(packagePO.getExtraProvinceInDayRent());
		packageVO.setExtraProvinceInDayFlow(packagePO.getExtraProvinceInDayFlow());
		packageVO.setExtraProvinceOutDayRent(packagePO.getExtraProvinceOutDayRent());
		packageVO.setExtraProvinceOutDayFlow(packagePO.getExtraProvinceOutDayFlow());
		packageVO.setExtraFlowType(extraFlowType);
		packageVO.setPrivilegeDescription(packagePO.getPrivilegeDescription());
		packageVO.setStar(packagePO.getStar());
		packageVO.setUrl(packagePO.getUrl());
		packageVO.setRemark(packagePO.getRemark());
		packageVO.setAbandon(packagePO.getAbandon());
		packageVO.setFreeFlowType(packagePO.getFreeFlowType());
		packageVO.setTotalConsume(totalConsume);
		return packageVO;
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
	public int getMonthRent() {
		return monthRent;
	}
	public void setMonthRent(int monthRent) {
		this.monthRent = monthRent;
	}
	public int getPackageCountryFlow() {
		return packageCountryFlow;
	}
	public void setPackageCountryFlow(int packageCountryFlow) {
		this.packageCountryFlow = packageCountryFlow;
	}
	public int getPackageProvinceFlow() {
		return packageProvinceFlow;
	}
	public void setPackageProvinceFlow(int packageProvinceFlow) {
		this.packageProvinceFlow = packageProvinceFlow;
	}
	public int getPackageCall() {
		return packageCall;
	}
	public void setPackageCall(int packageCall) {
		this.packageCall = packageCall;
	}
	public double getExtraPackageCall() {
		return extraPackageCall;
	}
	public void setExtraPackageCall(double extraPackageCall) {
		this.extraPackageCall = extraPackageCall;
	}
	public double getExtraCountryFlow() {
		return extraCountryFlow;
	}
	public void setExtraCountryFlow(double extraCountryFlow) {
		this.extraCountryFlow = extraCountryFlow;
	}
	public double getExtraProvinceFlow() {
		return extraProvinceFlow;
	}
	public void setExtraProvinceFlow(double extraProvinceFlow) {
		this.extraProvinceFlow = extraProvinceFlow;
	}
	public double getExtraProvinceOutFlow() {
		return extraProvinceOutFlow;
	}
	public void setExtraProvinceOutFlow(double extraProvinceOutFlow) {
		this.extraProvinceOutFlow = extraProvinceOutFlow;
	}
	public int getExtraCountryDayRent() {
		return extraCountryDayRent;
	}
	public void setExtraCountryDayRent(int extraCountryDayRent) {
		this.extraCountryDayRent = extraCountryDayRent;
	}
	public int getExtraCountryDayFlow() {
		return extraCountryDayFlow;
	}
	public void setExtraCountryDayFlow(int extraCountryDayFlow) {
		this.extraCountryDayFlow = extraCountryDayFlow;
	}
	public int getExtraProvinceInDayRent() {
		return extraProvinceInDayRent;
	}
	public void setExtraProvinceInDayRent(int extraProvinceInDayRent) {
		this.extraProvinceInDayRent = extraProvinceInDayRent;
	}
	public int getExtraProvinceInDayFlow() {
		return extraProvinceInDayFlow;
	}
	public void setExtraProvinceInDayFlow(int extraProvinceInDayFlow) {
		this.extraProvinceInDayFlow = extraProvinceInDayFlow;
	}
	public int getExtraProvinceOutDayRent() {
		return extraProvinceOutDayRent;
	}
	public void setExtraProvinceOutDayRent(int extraProvinceOutDayRent) {
		this.extraProvinceOutDayRent = extraProvinceOutDayRent;
	}
	public int getExtraProvinceOutDayFlow() {
		return extraProvinceOutDayFlow;
	}
	public void setExtraProvinceOutDayFlow(int extraProvinceOutDayFlow) {
		this.extraProvinceOutDayFlow = extraProvinceOutDayFlow;
	}
	public int getExtraFlowType() {
		return extraFlowType;
	}
	public void setExtraFlowType(int extraFlowType) {
		this.extraFlowType = extraFlowType;
	}
	public String getPrivilegeDescription() {
		return privilegeDescription;
	}
	public void setPrivilegeDescription(String privilegeDescription) {
		this.privilegeDescription = privilegeDescription;
	}
	public double getStar() {
		return star;
	}
	public void setStar(double star) {
		this.star = star;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getAbandon() {
		return abandon;
	}
	public void setAbandon(int abandon) {
		this.abandon = abandon;
	}
	public int getFreeFlowType() {
		return freeFlowType;
	}
	public void setFreeFlowType(int freeFlowType) {
		this.freeFlowType = freeFlowType;
	}
	public double getTotalConsume() {
		return totalConsume;
	}
	public void setTotalConsume(double totalConsume) {
		this.totalConsume = totalConsume;
	}
}
