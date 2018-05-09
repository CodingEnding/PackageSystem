package com.ending.packagesystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ending.packagesystem.po.NotificationPO;
import com.ending.packagesystem.utils.DBUtils;
import com.ending.packagesystem.utils.MathUtils;

public class NotificationDao {
	
	/**
	 * 获取通知列表（按照日期降序排列）
	 * @param limit 每页条数
	 * @param page 页码
	 * @return 通知列表
	 */
	public List<NotificationPO> findAll(int limit,int page){
		List<NotificationPO> dataList=new ArrayList<>();
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="select * from notification order by send_time desc limit ? offset ?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setInt(1,limit);
			int offsetNum=MathUtils.positiveNum((page-1)*limit);//需要跳过的条数
			statement.setInt(2,offsetNum);
			
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next()){
				NotificationPO notificationPO=new NotificationPO();
				notificationPO.setId(resultSet.getInt("id"));
				notificationPO.setContent(resultSet.getString("content"));
				notificationPO.setTitle(resultSet.getString("title"));
				notificationPO.setSendTime(resultSet.getTimestamp("send_time"));
				dataList.add(notificationPO);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
		}
		return dataList;
	}
	
}
