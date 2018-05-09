package com.ending.packagesystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			String sql="insert into packagescore(score,user_id,package_id) values(?,?,?)";
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
	
	/**
	 * 更新一条数据
	 * @param packageScorePO
	 * @return
	 */
	public boolean update(PackageScorePO packageScorePO){
		boolean isSucceed=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="update packagescore set score=? where user_id=? and package_id=?";
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
	
	/**
	 * 判断指定数据是否存在
	 * @param packageScorePO
	 */
	public boolean isExsit(PackageScorePO packageScorePO){
		boolean isExsit=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="select 1 from packagescore where user_id=? and package_id=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setInt(1,packageScorePO.getUserId());
			statement.setInt(2,packageScorePO.getPackageId());
			
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
	 * 根据套餐Id获取其所有的评分数据
	 * @param packageId
	 * @return List<PackageScorePO>
	 */
	public List<PackageScorePO> findAll(int packageId){
		List<PackageScorePO> packageScoreList=new ArrayList<>();
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="select * from packagescore where package_id=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setInt(1,packageId);
			
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next()){
				PackageScorePO packageScorePO=new PackageScorePO();
				packageScorePO.setId(resultSet.getInt("id"));
				packageScorePO.setPackageId(resultSet.getInt("package_id"));
				packageScorePO.setScore(resultSet.getInt("score"));
				packageScorePO.setUserId(resultSet.getInt("user_id"));
				packageScoreList.add(packageScorePO);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.closeConnection(connection);
		}
		return packageScoreList;
	}
	
}
