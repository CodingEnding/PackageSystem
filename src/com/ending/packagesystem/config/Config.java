package com.ending.packagesystem.config;

/**
 * 服务器的一些配置参数
 * @author CodingEnding
 */
public interface Config {
	int PACKAGE_SCORE_DELAY=0;//首次计算套餐评分的延迟（0s）
	int PACKAGE_SCORE_PERIOD=10*60*1000;//定时计算套餐评分的时间间隔（10min）
	int HOT_PACKAGE_COUNT=30;//默认返回的热门套餐数量（30条）
	String APK_FOLDER_PATH="/apk";//保存安装包的文件夹路径
}
