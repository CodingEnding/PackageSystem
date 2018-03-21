package com.ending.packagesystem.po;

/**
 * 流量消费表（FlowConsume）
 * @author CodingEnding
 */
public class FlowConsumePO {
	private int id;
	private int appId;//（外键）应用id
	private int flowAmount;//流量消费量
	private int week;//第几周（1-4）
	private int month;//第几个月（1-12）
	private int deviceId;//（外键）设备id
	
}
