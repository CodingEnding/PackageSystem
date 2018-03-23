package com.ending.packagesystem.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 在服务器启动后执行一些初始化操作
 * @author CodingEnding
 */
public class InitServletListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("init");
	}

	/**
	 * 在独立的线程中定时更新每个套餐的评分
	 * 计算套餐的评分平均分
	 */
	private void calculateAverageScore(){
		
	}
	
}
