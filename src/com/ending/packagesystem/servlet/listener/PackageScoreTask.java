package com.ending.packagesystem.servlet.listener;

import java.util.List;
import java.util.TimerTask;

import com.ending.packagesystem.po.PackagePO;
import com.ending.packagesystem.po.PackageScorePO;
import com.ending.packagesystem.service.PackageService;
import com.ending.packagesystem.utils.DebugUtils;

/**
 * 定时任务
 * 定时更新每个套餐的评分
 * @author CodingEnding
 */
public class PackageScoreTask extends TimerTask{
	public static final String TAG="PackageScoreTask";
	private PackageService packageService=new PackageService();
	
	@Override
	public void run() {
		DebugUtils.println(TAG,"[定时任务]套餐的评分任务开始执行...");
		List<PackagePO> packageList=packageService.getAllPackage();//查询出所有套餐
		for(PackagePO packagePO:packageList){//循环操作每项套餐
			List<PackageScorePO> packageScoreList=packageService
					.getAllPackageScore(packagePO.getId());
			
			if(packageScoreList.isEmpty()){//如果套餐还不存在评分数据就跳过
				continue;
			}
			
			double scoreSum=0.0;//该项套餐的评分总分
			double scoreAverage=0.0;//该项套餐的评分平均分
			for(PackageScorePO packageScorePO:packageScoreList){//循环获取该项套餐的评分
				scoreSum+=packageScorePO.getScore();
			}
			scoreAverage=scoreSum/packageScoreList.size();
			packagePO.setStar(scoreAverage);
			//更新该项套餐的评分数据
			packageService.updatePackageById(packagePO.getId(),packagePO);
		}
		DebugUtils.println(TAG,"[定时任务]套餐的评分数据更新完毕");
	}

}
