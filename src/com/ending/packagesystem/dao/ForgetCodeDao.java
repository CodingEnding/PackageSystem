package com.ending.packagesystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ending.packagesystem.po.ForgetCodePO;
import com.ending.packagesystem.utils.DBUtils;

public class ForgetCodeDao {
	
	/**
	 * 插入一条数据
	 * @param forgetCodePO
	 */
	public boolean insert(ForgetCodePO forgetCodePO){
		boolean isSucceed=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="insert into forgetcode(email,code,expire) values(?,?,?)";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,forgetCodePO.getEmail());
			statement.setString(2,forgetCodePO.getCode());
			statement.setTimestamp(3,forgetCodePO.getExpire());
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
	 * 更新验证码和对应的有效期（通过email）
	 * @param email
	 * @param code
	 * @param expire 指定有效期
	 */
	public boolean updateCodeByEmail(String email,String code,Timestamp expire){
		Connection connection=null;
		boolean isSucceed=false;
		try {
			connection=DBUtils.getConnection();
			String sql="update forgetcode set code=?,expire=? where email=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,code);
			statement.setTimestamp(2,expire);
			statement.setString(3,email);
			
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
	 * 更新指定记录的有效期（通过email）
	 * @param email
	 * @param expire 指定有效期
	 */
	public boolean updateExpireByEmail(String email,Timestamp expire){
		Connection connection=null;
		boolean isSucceed=false;
		try {
			connection=DBUtils.getConnection();
			String sql="update forgetcode set expire=? where email=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setTimestamp(1,expire);
			statement.setString(2,email);
			
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
	 * 根据邮箱判断是否已存在数据
	 * @param email
	 * @return 是否存在
	 */
	public boolean isExsitWithEmail(String email){
		boolean isSucceed=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="select 1 from forgetcode where email=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,email);
			
			ResultSet resultSet=statement.executeQuery();
			if(resultSet.next()){
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
	 * 判断验证码是否合法且未过期
	 * @param email
	 * @param code 待判断的验证码
	 * @return 是否合理
	 */
	public boolean isCodeValidWithEmail(String email,String code){
		boolean isValid=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="select 1 from forgetcode where email=? and code=? and expire>now()";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,email);
			statement.setString(2,code);
			
			ResultSet resultSet=statement.executeQuery();
			if(resultSet.next()){
				isValid=true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
		}
		return isValid;
	}
	
}
