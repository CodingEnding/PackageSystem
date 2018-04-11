package com.ending.packagesystem.po;

/**
 * 流量消费表（FlowConsume）
 * @author CodingEnding
 */
public class FlowConsumePO {
	private int id;
	private int appId;//（外键）应用id
	private int flowAmount;//流量消费量
	private int day;//第几天（1-31）
	private int month;//第几月（1-12）
	private int year;//第几年（2018-）
	private int deviceId;//（外键）设备id
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
	public int getFlowAmount() {
		return flowAmount;
	}
	public void setFlowAmount(int flowAmount) {
		this.flowAmount = flowAmount;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	
}
