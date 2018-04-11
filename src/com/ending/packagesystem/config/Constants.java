package com.ending.packagesystem.config;

/**
 * 保存一些常量
 * @author CodingEnding
 */
public interface Constants {
	int QUERY_ERROR_ID=-1;//在查询出现错误时默认将查询对象的Id设置为这个值
	int REGISTER_ERROR_ID=-2;//在注册出现错误时默认将返回的Id设置为这个值
	int REGISTER_EMAIL_EXSIT=-3;//在注册邮箱重复时使用
	int LOGIN_ERROR_ID=-4;//登录失败
	String PACKAGE_COLUMN_STAR="star";//Package列表的评分列名称
}
