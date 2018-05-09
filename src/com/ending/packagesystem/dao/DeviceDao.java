package com.ending.packagesystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ending.packagesystem.config.Constants;
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
	 * 插入一条数据（不包含user_id）
	 * @param devicePO
	 */
	public boolean insertWithOutUserId(DevicePO devicePO){
		boolean isSucceed=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="insert into device(device_type,system_version,device_finger) values(?,?,?)";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,devicePO.getDeviceType());
			statement.setString(2,devicePO.getSystemVersion());
			statement.setString(3,devicePO.getDeviceFinger());
			
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
			String sql="select 1 from device where device_finger=?";
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
	
	/**
	 * 通过设备finger更新数据（不包含user_id）
	 * @param deviceFinger
	 */
	public boolean updateByFingerWithOutUserId(DevicePO devicePO,String deviceFinger){
		boolean isSucceed=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="update device set device_type=?,system_version=? where device_finger=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,devicePO.getDeviceType());
			statement.setString(2,devicePO.getSystemVersion());
			statement.setString(3,deviceFinger);
			
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
	 * 通过deviceFinger获取指定设备的Id
	 */
	public int findDeviceIdByEmail(String deviceFinger){
		Connection connection=null;
		int id=Constants.QUERY_ERROR_ID;//默认设置为异常值，在查询失败时调用者可以察觉);
		try {
			connection=DBUtils.getConnection();
			String sql="select id from device where device_finger=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,deviceFinger);
			
			ResultSet resultSet=statement.executeQuery();
			if(resultSet.next()){
				id=resultSet.getInt("id");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
		}
		return id;
	}
	
	
	/**
	 * 根据UserId获取设备列表
	 * @param userId
	 * @return
	 */
	public List<DevicePO> findAllByUserId(int userId){
		List<DevicePO> dataList=new ArrayList<>();
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="select * from device where user_id=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setInt(1,userId);
			
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next()){
				DevicePO devicePO=new DevicePO();
				devicePO.setDeviceFinger(resultSet.getString("device_finger"));
				devicePO.setDeviceType(resultSet.getString("device_type"));
				devicePO.setId(resultSet.getInt("id"));
				devicePO.setSystemVersion(resultSet.getString("system_version"));
				devicePO.setUserId(resultSet.getInt("user_id"));
				dataList.add(devicePO);
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
