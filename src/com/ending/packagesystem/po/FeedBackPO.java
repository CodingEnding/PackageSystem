package com.ending.packagesystem.po;

/**
 * 用户反馈表（FeedBack）
 * @author CodingEnding
 */
public class FeedBackPO {
	private int id;
	private int deviceId;//（外键）设备id
	private String content;//反馈内容
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
