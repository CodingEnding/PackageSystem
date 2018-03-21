package com.ending.packagesystem.core;

/**
 * 封装套餐Id和套餐月消费
 * 用于对推荐结果的排序中
 * @author CodingEnding
 */
public class PackageConsume implements Comparable<PackageConsume>{
	private int packageId;
	private String packageName;
	private double consume;//最终的套餐月消费

	public PackageConsume() {
	}
	
	public PackageConsume(int packageId, String packageName, double consume) {
		super();
		this.packageId = packageId;
		this.packageName = packageName;
		this.consume = consume;
	}

	@Override
	public int compareTo(PackageConsume other) {
		int compareEnd=Double.compare(consume,other.consume);
		if(compareEnd==0){
			int nameOrder=packageName.compareTo(other.packageName);
			if(nameOrder==0){
				return Integer.compare(packageId,other.packageId);
			}
			return nameOrder;
		}
		return compareEnd;
	}
	
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public double getConsume() {
		return consume;
	}
	public void setConsume(double consume) {
		this.consume = consume;
	}
	
}
