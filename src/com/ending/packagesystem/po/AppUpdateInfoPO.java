package com.ending.packagesystem.po;

/**
 * 应用版本信息表（AppUpdateInfo）
 * @author CodingEnding
 */
public class AppUpdateInfoPO {
	private int id;
    private int silent;//是否静默下载：有新版本时不提示直接下载
    private int force;//是否强制安装：不安装无法使用app
    private int autoInstall;//是否下载完成后自动安装
    private int ignore;//是否可忽略该版本
    private int versionCode;
    private String versionName;
    private String updateContent;//更新内容描述
    private String url;//下载地址
    private String apkName;//安装包名称
	
    public String getApkName() {
		return apkName;
	}
	public void setApkName(String apkName) {
		this.apkName = apkName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSilent() {
		return silent;
	}
	public void setSilent(int silent) {
		this.silent = silent;
	}
	public int getForce() {
		return force;
	}
	public void setForce(int force) {
		this.force = force;
	}
	public int getAutoInstall() {
		return autoInstall;
	}
	public void setAutoInstall(int autoInstall) {
		this.autoInstall = autoInstall;
	}
	public int getIgnore() {
		return ignore;
	}
	public void setIgnore(int ignore) {
		this.ignore = ignore;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getUpdateContent() {
		return updateContent;
	}
	public void setUpdateContent(String updateContent) {
		this.updateContent = updateContent;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    
}
