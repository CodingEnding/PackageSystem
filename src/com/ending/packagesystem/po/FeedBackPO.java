package com.ending.packagesystem.po;

/**
 * 用户反馈表（FeedBack）
 * @author CodingEnding
 */
public class FeedBackPO {
	private int id;
	private int deviceId;//（外键）设备id
	private int userId;//（外键）用户id
	private String content;//反馈内容
	
	public FeedBackPO() {
	}
	public FeedBackPO(int deviceId, int userId, String content) {
		this.deviceId = deviceId;
		this.userId = userId;
		this.content = content;
	}
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
