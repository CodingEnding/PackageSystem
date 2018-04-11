package com.ending.packagesystem.po;

import java.sql.Timestamp;

/**
 * 用户表（User）
 * @author CodingEnding
 */
public class UserPO {
	private int id;
	private String username;
	private String password;
	private String email;
	private String phone;
	private int emailVerified;//邮箱是否已经经过验证
	private String sessionToken;//权限令牌
	private Timestamp sessionExpire;//令牌过期时间点
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getEmailVerified() {
		return emailVerified;
	}
	public void setEmailVerified(int emailVerified) {
		this.emailVerified = emailVerified;
	}
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	public Timestamp getSessionExpire() {
		return sessionExpire;
	}
	public void setSessionExpire(Timestamp sessionExpire) {
		this.sessionExpire = sessionExpire;
	}
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
	
}
