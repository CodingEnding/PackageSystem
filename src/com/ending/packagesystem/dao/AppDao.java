package com.ending.packagesystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ending.packagesystem.po.AppPO;
import com.ending.packagesystem.utils.DBUtils;

public class AppDao {
	/**
	 * 根据id获取App
	 * @param id
	 * @return
	 */
	public AppPO findAppById(int id){
		Connection connection=null;
		AppPO app=new AppPO();
		try {
			connection=DBUtils.getConnection();
			String sql="select * from app where id=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setInt(1,id);
			ResultSet resultSet=statement.executeQuery();
			if(resultSet.next()){
				app.setId(id);
				app.setName(resultSet.getString("app_name"));
				app.setFreeFlowType(resultSet.getInt("free_flow_type"));
				app.setTypeId(resultSet.getInt("type_id"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.closeConnection(connection);
		}
		return app;
	}
	
	/**
	 * 根据应用名获取App
	 * @param name
	 * @return
	 */
	public AppPO findAppByName(String name){
		Connection connection=null;
		AppPO app=new AppPO();
		try {
			connection=DBUtils.getConnection();
			String sql="select * from app where app_name=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,name);
			ResultSet resultSet=statement.executeQuery();
			if(resultSet.next()){
				app.setId(resultSet.getInt("id"));
				app.setName(resultSet.getString("app_name"));
				app.setFreeFlowType(resultSet.getInt("free_flow_type"));
				app.setTypeId(resultSet.getInt("type_id"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.closeConnection(connection);
		}
		return app;
	}
	
	
	/**
	 * 查询所有的App
	 * @return List<AppPO>
	 */
	public List<AppPO> findAll(){
		Connection connection=null;
		List<AppPO> appList=new ArrayList<>();
		try {
			connection=DBUtils.getConnection();
			String sql="select * from app";
			PreparedStatement statement=connection.prepareStatement(sql);
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next()){
				AppPO app=new AppPO();
				app.setId(resultSet.getInt("id"));
				app.setName(resultSet.getString("app_name"));
				app.setFreeFlowType(resultSet.getInt("free_flow_type"));
				app.setTypeId(resultSet.getInt("type_id"));
				appList.add(app);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
		}
		return appList;
	}
	
}
