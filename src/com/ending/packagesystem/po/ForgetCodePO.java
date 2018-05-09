package com.ending.packagesystem.po;

import java.sql.Timestamp;

/**
 * 找回密码的验证码表（ForgetCode）
 * @author CodingEnding
 */
public class ForgetCodePO {
	private int id;
	private String email;
	private String code;//验证码
	private Timestamp expire;//过期时间点
	
	public ForgetCodePO() {
	}
	public ForgetCodePO(String email,String code,Timestamp expire) {
		super();
		this.email = email;
		this.code = code;
		this.expire=expire;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Timestamp getExpire() {
		return expire;
	}
	public void setExpire(Timestamp expire) {
		this.expire = expire;
	}
	
}
