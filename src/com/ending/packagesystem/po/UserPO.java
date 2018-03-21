package com.ending.packagesystem.po;

/**
 * 用户表（User）
 * @author CodingEnding
 */
public class UserPO {
	private int id;
	private String username;
	private String password;
	private int deviceId;//（外键）设备id
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	
}
