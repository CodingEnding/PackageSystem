package com.ending.packagesystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ending.packagesystem.po.FlowPrivilegePO;
import com.ending.packagesystem.utils.DBUtils;

/**
 * @author CodingEnding
 */
public class FlowPrivilegeDao {
	
	/**
	 * 判断指定的套餐流量特权是否存在
	 * @param flowPrivilege
	 * @return
	 */
	public boolean exist(FlowPrivilegePO flowPrivilege){
		Connection connection=null;
		boolean isExist=false;
		
		try {
			connection=DBUtils.getConnection();
			String sql="select * from flowprivilege where app_id=? and package_id=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setInt(1,flowPrivilege.getAppId());
			statement.setInt(2,flowPrivilege.getPackageId());
			ResultSet resultSet=statement.executeQuery();
			if(resultSet.next()){
				isExist=true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.closeConnection(connection);
		}
		
		return isExist;
	}
}
