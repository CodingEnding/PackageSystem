package com.ending.packagesystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ending.packagesystem.po.DevicePO;
import com.ending.packagesystem.utils.DBUtils;

public class DeviceDao {
	
	/**
	 * 插入一条数据
	 * @param devicePO
	 */
	public boolean insert(DevicePO devicePO){
		boolean isSucceed=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="insert into device(device_type,system_version,device_finger,user_id) values(?,?,?,?)";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,devicePO.getDeviceType());
			statement.setString(2,devicePO.getSystemVersion());
			statement.setString(3,devicePO.getDeviceFinger());
			statement.setInt(4,devicePO.getUserId());
			
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
	
	/**
	 * 通过Finger判断是否有这条数据存在
	 * @param email
	 */
	public boolean isExsitWithFinger(String deviceFinger){
		boolean isExsit=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="select * from device where device_finger=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,deviceFinger);
			
			ResultSet resultSet=statement.executeQuery();
			if(resultSet.next()){
				isExsit=true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
		}
		return isExsit;
	}
	
	/**
	 * 通过设备finger更新数据
	 * @param deviceFinger
	 */
	public boolean updateByFinger(DevicePO devicePO,String deviceFinger){
		boolean isSucceed=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="update device set device_type=?,system_version=?,user_id=? where device_finger=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,devicePO.getDeviceType());
			statement.setString(2,devicePO.getSystemVersion());
			statement.setInt(3,devicePO.getUserId());
			statement.setString(4,deviceFinger);
			
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
