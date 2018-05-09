package com.ending.packagesystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ending.packagesystem.config.Constants;
import com.ending.packagesystem.po.UserPO;
import com.ending.packagesystem.utils.DBUtils;

public class UserDao {
	
	/**
	 * 插入一条数据
	 * @param userPO
	 */
	public boolean insert(UserPO userPO){
		boolean isSucceed=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="insert into user(username,password,email) values(?,?,?)";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,userPO.getUsername());
			statement.setString(2,userPO.getPassword());
			statement.setString(3,userPO.getEmail());
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
	 * 通过邮箱判断是否有这条数据存在（邮箱应该唯一）
	 * @param email
	 */
	public boolean isExsitWithEmail(String email){
		boolean isExsit=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="select 1 from user where email=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,email);
			
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
	 * 通过邮箱和密码组合判断是否有这条数据存在
	 * @param email
	 * @param password
	 */
	public boolean isExsitByEmailAndPass(String email,String password){
		boolean isExsit=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="select 1 from user where email=? and password=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,email);
			statement.setString(2,password);
			
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
	 * 通过邮箱和session组合判断用户令牌是否有效
	 * 可用于用户身份确认
	 * @param email
	 * @param sessionToken 用户令牌
	 */
	public boolean isSessionValidWithEmail(String email,String sessionToken){
		boolean isExsit=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="select 1 from user where email=? and session_token=? and session_expire> now()";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,email);
			statement.setString(2,sessionToken);
			
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
	 * 通过邮箱和密码组合判断用户身份是否有效
	 * 可用于用户身份确认
	 * @param email
	 * @param password 密码
	 */
	public boolean isPwdValidWithEmail(String email,String password){
		boolean isExsit=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="select 1 from user where email=? and password=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,email);
			statement.setString(2,password);
			
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
	
//	/**
//	 * TODO
//	 * 通过用户名和SessionToken的组合判断是否有这条数据存在
//	 * @param email
//	 * @param password
//	 */
//	public boolean isExsitWithSession(String email,String password){
//		boolean isExsit=false;
//		Connection connection=null;
//		try {
//			connection=DBUtils.getConnection();
//			String sql="select * from User where email=? and password=?";
//			PreparedStatement statement=connection.prepareStatement(sql);
//			statement.setString(1,email);
//			statement.setString(2,password);
//			
//			ResultSet resultSet=statement.executeQuery();
//			if(resultSet.next()){
//				isExsit=true;
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBUtils.closeConnection(connection);
//		}
//		return isExsit;
//	}
	
	/**
	 * 更新sessionToken（通过email）
	 * @param email
	 * @param sessionToken
	 * @param expire（有效期）
	 * @return
	 */
	public boolean updateSessionByEmail(String email,String sessionToken,Timestamp expire){
		Connection connection=null;
		boolean isSucceed=false;
		try {
			connection=DBUtils.getConnection();
			String sql="update user set session_token=?,session_expire=? where email=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,sessionToken);
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
	 * 根据邮箱修改指定列的数据
	 * @param email
	 * @param columnName
	 * @param value
	 * @return 是否修改成功
	 */
	public boolean updateInfoByEmail(String email,String columnName,String value){
		Connection connection=null;
		boolean isSucceed=false;
		
		try {
			connection=DBUtils.getConnection();
			String sql=String.format("update user set %s=? where email=?",columnName);
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,value);
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
	 * 根据邮箱修改密码
	 * @return 是否修改成功
	 */
	public boolean updatePwdByEmail(String email,String password){
		Connection connection=null;
		boolean isSucceed=false;
		
		try {
			connection=DBUtils.getConnection();
			String sql="update user set password=? where email=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,password);
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
	 * 通过id获取指定用户（不包含password和session过期时间）
	 * @param id
	 * @return
	 */
	public UserPO findUserById(int id){
		Connection connection=null;
		UserPO userPO=new UserPO();
		userPO.setId(Constants.QUERY_ERROR_ID);//默认设置为异常值，在查询失败时调用者可以察觉);
		try {
			connection=DBUtils.getConnection();
			String sql="select * from user where id=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setInt(1,id);
			
			ResultSet resultSet=statement.executeQuery();
			bindUserData(userPO, resultSet);//绑定数据
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
		}
		return userPO;
	}
	
	/**
	 * 通过email获取指定用户（不包含password和session过期时间）
	 */
	public UserPO findUserByEmail(String email){
		Connection connection=null;
		UserPO userPO=new UserPO();
		userPO.setId(Constants.QUERY_ERROR_ID);//默认设置为异常值，在查询失败时调用者可以察觉);
		try {
			connection=DBUtils.getConnection();
			String sql="select * from user where email=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,email);
			
			ResultSet resultSet=statement.executeQuery();
			bindUserData(userPO, resultSet);//绑定数据
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
		}
		return userPO;
	}
	
	/**
	 * 通过email获取指定用户的Id
	 */
	public int findUserIdByEmail(String email){
		Connection connection=null;
		int id=Constants.QUERY_ERROR_ID;//默认设置为异常值，在查询失败时调用者可以察觉);
		try {
			connection=DBUtils.getConnection();
			String sql="select id from user where email=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,email);
			
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
	
	//绑定数据
	private void bindUserData(UserPO userPO,ResultSet resultSet) throws SQLException{
		if(resultSet.next()){
			userPO.setId(resultSet.getInt("id"));
			userPO.setEmail(resultSet.getString("email"));
			userPO.setEmailVerified(resultSet.getInt("email_verified"));
			userPO.setPhone(resultSet.getString("phone"));
			userPO.setSessionToken(resultSet.getString("session_token"));
			userPO.setUsername(resultSet.getString("username"));
		}
	}
	
	/**
	 * 通过邮箱获取用户Id
	 * @param email
	 */
	public int findIdByEmail(String email){
		Connection connection=null;
		int id=Constants.QUERY_ERROR_ID;//默认设置为异常值，在查询失败时调用者可以察觉);
		try {
			connection=DBUtils.getConnection();
			String sql="select * from user where email=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,email);
			
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
	
}
