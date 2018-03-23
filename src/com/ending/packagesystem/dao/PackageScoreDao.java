package com.ending.packagesystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ending.packagesystem.po.PackageScorePO;
import com.ending.packagesystem.utils.DBUtils;

public class PackageScoreDao {
	
	/**
	 * 插入一条新数据
	 * @param packageScorePO
	 * @return
	 */
	public boolean insert(PackageScorePO packageScorePO){
		boolean isSucceed=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="insert into PackageScore(score,user_id,package_id) values(?,?,?)";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setInt(1,packageScorePO.getScore());
			statement.setInt(2,packageScorePO.getUserId());
			statement.setInt(3,packageScorePO.getPackageId());
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
