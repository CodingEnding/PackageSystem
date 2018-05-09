package com.ending.packagesystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ending.packagesystem.po.FeedBackPO;
import com.ending.packagesystem.utils.DBUtils;

public class FeedBackDao {
	
	/**
	 * 插入一条数据
	 * @param feedBackPO
	 */
	public boolean insert(FeedBackPO feedBackPO){
		boolean isSucceed=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="insert into feedback(user_id,device_id,content) values(?,?,?)";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setInt(1,feedBackPO.getUserId());
			statement.setInt(2,feedBackPO.getDeviceId());
			statement.setString(3,feedBackPO.getContent());
			
			int end=statement.executeUpdate();
			if(end==1){
				isSucceed=true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
		}
		return isSucceed;
	}
	
}
