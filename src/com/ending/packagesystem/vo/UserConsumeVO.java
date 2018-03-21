package com.ending.packagesystem.vo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * {
"call_time":100,
"flow_all":1200,
"week":3,
"month":3,
"province_out_day":0,
"device_type":"华为Nova",
"system_version":"Android7.0",
"device_finger":"4822AOSNV45668",
"operator":["中国联通","中国电信"],
"flow_consume":[
	{"app_name":"QQ","app_flow":"160"},
	{"app_name":"微信","app_flow":"290"},
	{"app_name":"绝地求生","app_flow":"500"}
	]
}
 */

/**
 * 用户上传的JSON数据实体类
 * @author CodingEnding
 */
public class UserConsumeVO {
	@SerializedName("call_time")
	private int callTime;//通话时长
	@SerializedName("flow_all")
	private int allFlow;//总的流量消费量
	private int week;//第几周
	private int month;//第几个月
	@SerializedName("province_out_day")
	private int provinceOutDay;//每月在省外的时间
	@SerializedName("device_type")
	private String deviceType;//机型
	@SerializedName("system_version")
	private String systemVersion;//系统版本
	@SerializedName("device_finger")
	private String deviceFinger;//设备唯一标识
	@SerializedName("operator")
	private List<String> operatorList;//需要推荐的运营商套餐
	@SerializedName("flow_consume")
	private List<FlowConsumeVO> flowConsumeList;//应用流量消费列表
	
	public int getCallTime() {
		return callTime;
	}
	public void setCallTime(int callTime) {
		this.callTime = callTime;
	}
	public int getAllFlow() {
		return allFlow;
	}
	public void setAllFlow(int allFlow) {
		this.allFlow = allFlow;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getProvinceOutDay() {
		return provinceOutDay;
	}
	public void setProvinceOutDay(int provinceOutDay) {
		this.provinceOutDay = provinceOutDay;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getSystemVersion() {
		return systemVersion;
	}
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}
	public String getDeviceFinger() {
		return deviceFinger;
	}
	public void setDeviceFinger(String deviceFinger) {
		this.deviceFinger = deviceFinger;
	}
	public List<String> getOperatorList() {
		return operatorList;
	}
	public void setOperatorList(List<String> operatorList) {
		this.operatorList = operatorList;
	}
	public List<FlowConsumeVO> getFlowConsumeList() {
		return flowConsumeList;
	}
	public void setFlowConsumeList(List<FlowConsumeVO> flowConsumeList) {
		this.flowConsumeList = flowConsumeList;
	}
	
}

