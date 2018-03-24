package com.ending.packagesystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ending.packagesystem.config.Constants;
import com.ending.packagesystem.po.AppUpdateInfoPO;
import com.ending.packagesystem.utils.DBUtils;

public class AppUpdateInfoDao {
	
	/**
	 * 返回唯一的应用版本信息数据
	 * @return AppUpdateInfoPO
	 */
	public AppUpdateInfoPO findAppUpdateInfo(){
		AppUpdateInfoPO appUpdateInfoPO=new AppUpdateInfoPO();
		appUpdateInfoPO.setId(Constants.QUERY_ERROR_ID);//默认设置为异常Id，以便调用者察觉
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="select * from AppUpdateInfo";
			PreparedStatement statement=connection.prepareStatement(sql);
			ResultSet resultSet=statement.executeQuery();
			if(resultSet.next()){
				appUpdateInfoPO.setId(resultSet.getInt("id"));
				appUpdateInfoPO.setSilent(resultSet.getInt("silent"));
				appUpdateInfoPO.setForce(resultSet.getInt("force"));
				appUpdateInfoPO.setAutoInstall(resultSet.getInt("auto_install"));
				appUpdateInfoPO.setIgnore(resultSet.getInt("ignore"));
				appUpdateInfoPO.setVersionCode(resultSet.getInt("version_code"));
				appUpdateInfoPO.setVersionName(resultSet.getString("version_name"));
				appUpdateInfoPO.setUpdateContent(resultSet.getString("update_content"));
				appUpdateInfoPO.setUrl(resultSet.getString("url"));
				appUpdateInfoPO.setApkName(resultSet.getString("apk_name"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtils.closeConnection(connection);
		}
		return appUpdateInfoPO;
	}
}
