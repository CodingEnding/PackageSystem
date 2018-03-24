package com.ending.packagesystem.service;

import java.io.File;

import com.ending.packagesystem.config.Constants;
import com.ending.packagesystem.dao.AppUpdateInfoDao;
import com.ending.packagesystem.po.AppUpdateInfoPO;
import com.ending.packagesystem.utils.DebugUtils;
import com.ending.packagesystem.utils.FileUtils;
import com.ending.packagesystem.utils.MD5Utils;
import com.ending.packagesystem.vo.AppUpdateInfoVO;

/**
 * 提供与应用更新相关的服务
 * @author CodingEnding
 */
public class AppUpdateService {
	public static final String TAG="AppUpdateService";
	private AppUpdateInfoDao appUpdateInfoDao=new AppUpdateInfoDao();
	
	/**
	 * 坚持是否存在更新
	 * @param apkFolderPath apk文件所在的文件夹路径
	 * @param versionCode 客户端的版本号
	 * @return
	 */
	public AppUpdateInfoVO checkUpdate(String apkFolderPath,int versionCode){
		AppUpdateInfoPO appUpdateInfoPO=appUpdateInfoDao.findAppUpdateInfo();
		AppUpdateInfoVO resultInfo=new AppUpdateInfoVO();
		if(appUpdateInfoPO.getId()==Constants.QUERY_ERROR_ID){//如果id为异常Id，说明查询失败
			resultInfo.setId(Constants.QUERY_ERROR_ID);
			return resultInfo;
		}
		//查询正常情况下的逻辑
		if(versionCode<appUpdateInfoPO.getVersionCode()){//客户端的版本小于当前最新版本
			resultInfo.setHasUpdate(false);//只需要告诉客户端不存在更新
		}else{
			resultInfo.setHasUpdate(true);
		}
		resultInfo.setId(appUpdateInfoPO.getId());
		resultInfo.setAutoInstall(oneToTrue(appUpdateInfoPO.getAutoInstall()));
		resultInfo.setForce(oneToTrue(appUpdateInfoPO.getForce()));
		resultInfo.setIgnorable(oneToTrue(appUpdateInfoPO.getIgnore()));
		resultInfo.setSilent(oneToTrue(appUpdateInfoPO.getSilent()));
		resultInfo.setUrl(appUpdateInfoPO.getUrl());
		resultInfo.setVersionCode(appUpdateInfoPO.getVersionCode());
		resultInfo.setVersionName(appUpdateInfoPO.getVersionName());
		resultInfo.setUpdateContent(appUpdateInfoPO.getUpdateContent());
		String apkName=appUpdateInfoPO.getApkName();//安装包文件的名称（带有后缀名）
		File file=new File(apkFolderPath,apkName);
		if(file.exists()){
			DebugUtils.println(TAG,file.getName()+" exsits");
		}
		String apkMD5=MD5Utils.md5sum(file.getAbsolutePath());//安装包的MD5
		resultInfo.setMd5(apkMD5);
		DebugUtils.println(TAG,"md5:"+apkMD5);
		long apkFileSize=FileUtils.getFileSize(file);
		DebugUtils.println(TAG,"apkFileSize:"+apkFileSize);//安装包文件大小
		resultInfo.setSize(apkFileSize);
		return resultInfo;
	}
	
	//将1转化为true
	private boolean oneToTrue(int num){
		if(num==1){
			return true;
		}
		return false;
	}
	
}
