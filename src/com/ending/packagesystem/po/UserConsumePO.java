package com.ending.packagesystem.po;

/**
 * 用户消费表（UserConsume）
 * @author CodingEnding
 */
public class UserConsumePO {
	private int id;
	private int callTime;//通话时长
	private int allFlow;//总的流量消耗
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
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	
}
