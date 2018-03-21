package com.ending.packagesystem.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * JDBC工具类
 * 获取一个数据库连接
 * @author CodingEnding
 */
public class DBUtils {
	// JDBC 驱动名及数据库 URL
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	public static final String DB_NAME="package_system";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/"+DB_NAME+"?characterEncoding=UTF-8";

	// 数据库的用户名与密码
	static final String USER = "root";
	static final String PASSWORD = ""; 

	
	/**
	 * 获取一个数据库连接
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		//注册驱动
		Class.forName(JDBC_DRIVER);
		Connection connection=DriverManager.getConnection(DB_URL,USER,PASSWORD);
		return connection;
	}

	/**
	 * 关闭指定数据库连接
	 * @param connection
	 */
	public static void closeConnection(Connection connection){
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
