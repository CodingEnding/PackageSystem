package com.ending.packagesystem.servlet.listener;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ending.packagesystem.config.Config;
import com.ending.packagesystem.utils.DebugUtils;

/**
 * 在服务器启动后执行一些初始化操作
 * @author CodingEnding
 */
public class InitServletListener implements ServletContextListener {
	public static final String TAG="InitServletListener";
	private Timer serverTimer;//全局使用的定时器
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		DebugUtils.println(TAG,"destroy...");
		if(serverTimer!=null){
			serverTimer.cancel();//取消所有定时任务
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		DebugUtils.println(TAG,"init...");
		serverTimer=new Timer();
		calculateAverageScore();//使用定时器实现定时更新每个套餐的评分
	}

	/**
	 * 使用定时器实现定时更新每个套餐的评分
	 * 定时器会在在独立的线程中执行任务
	 * 计算套餐的评分平均分
	 */
	private void calculateAverageScore(){
		TimerTask packageScoreTask=new PackageScoreTask();
		serverTimer.schedule(packageScoreTask,
				Config.PACKAGE_SCORE_DELAY,Config.PACKAGE_SCORE_PERIOD);
	}
	
}
