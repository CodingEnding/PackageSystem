package com.ending.packagesystem.vo;

/**
 * 封装App的版本更新信息
 * @author CodingEnding
 */
public class AppUpdateInfoVO {
	private int id;
    private boolean hasUpdate;//是否有新版本
    private boolean isSilent;//是否静默下载：有新版本时不提示直接下载
    private boolean isForce;//是否强制安装：不安装无法使用app
    private boolean isAutoInstall;//是否下载完成后自动安装
    private boolean isIgnorable;//是否可忽略该版本
    private int versionCode;
    private String versionName;
    private String updateContent;//更新内容描述
    private String url;//下载地址（有新版本时必须有这个值）
    private String md5;//（有新版本时必须有这个值）
    private long size;//安装包大小
	
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isHasUpdate() {
		return hasUpdate;
	}
	public void setHasUpdate(boolean hasUpdate) {
		this.hasUpdate = hasUpdate;
	}
	public boolean isSilent() {
		return isSilent;
	}
	public void setSilent(boolean isSilent) {
		this.isSilent = isSilent;
	}
	public boolean isForce() {
		return isForce;
	}
	public void setForce(boolean isForce) {
		this.isForce = isForce;
	}
	public boolean isAutoInstall() {
		return isAutoInstall;
	}
	public void setAutoInstall(boolean isAutoInstall) {
		this.isAutoInstall = isAutoInstall;
	}
	public boolean isIgnorable() {
		return isIgnorable;
	}
	public void setIgnorable(boolean isIgnorable) {
		this.isIgnorable = isIgnorable;
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
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
    
}
