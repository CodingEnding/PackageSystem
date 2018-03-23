package com.ending.packagesystem.po;

/**
 * 套餐评分表（PackageScore）
 * @author CodingEnding
 */
public class PackageScorePO {
	private int id;
	private int score;
	private int userId;//（外键）
	private int packageId;//（外键）套餐id
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	
}
