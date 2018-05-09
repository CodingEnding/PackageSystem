package com.ending.packagesystem.po;

import java.sql.Timestamp;

/**
 * 系统通知表（Notification）
 * @author CodingEnding
 */
public class NotificationPO {
	private int id;
	private String title;
	private String content;
	private Timestamp sendTime;//发送时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getSendTime() {
		return sendTime;
	}
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	
}
