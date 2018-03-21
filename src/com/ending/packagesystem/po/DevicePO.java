package com.ending.packagesystem.po;

/**
 * 设备表（Device）
 * @author CodingEnding 
 */
public class DevicePO {
	private int id;
	private String deviceType;//机型
	private String systemVersion;//系统版本
	private String deviceFinger;//设备唯一识别码
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
}
