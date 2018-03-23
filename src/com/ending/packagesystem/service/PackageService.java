package com.ending.packagesystem.service;

import java.util.List;

import com.ending.packagesystem.config.Constants;
import com.ending.packagesystem.dao.PackageDao;
import com.ending.packagesystem.dao.PackageScoreDao;
import com.ending.packagesystem.po.PackagePO;
import com.ending.packagesystem.po.PackageScorePO;
import com.ending.packagesystem.vo.PackageVO;

/**
 * 提供与套餐相关的服务
 * @author CodingEnding
 */
public class PackageService {
	private PackageDao packageDao=new PackageDao();
	private PackageScoreDao packageScoreDao=new PackageScoreDao();
	
	/**
	 * 获取所有套餐
	 * @return List<PackagePO>
	 */
	public List<PackagePO> getAllPackage(){
		return packageDao.findAll();
	}
	
	/**
	 * 获取指定运营商的所有套餐
	 * @return List<PackagePO>
	 */
	public List<PackagePO> getAllPackageByOperator(List<String> operatorList){
		return packageDao.findAllByOperator(operatorList);
	}
	
	/**
	 * 获取指定id的套餐PackagePO
	 * @param id
	 * @return PackagePO
	 */
	public PackagePO getPackageById(int id){
		return packageDao.findPackageById(id);
	}
	
	/**
	 * 获取指定id的套餐
	 * 返回的PackageVO主要用于客户端展示
	 * @param id
	 * @return PackageVO
	 */
	public PackageVO getPackageVOById(int id){
		PackagePO temp=packageDao.findPackageById(id);
		PackageVO packageVO=null;
		if(temp.getId()==Constants.QUERY_ERROR_ID){//此时查询失败
			packageVO=new PackageVO();
			packageVO.setId(Constants.QUERY_ERROR_ID);//返回包含异常值的对象
		}else{
			int extraFlowType=temp.getExtraFlowTypeId();//TODO 暂时进行这样的快捷处理（因为extraFlowType表中的type和id保持一致）
			packageVO=PackageVO.build(temp,extraFlowType,0);
		}
		return packageVO;
	}
	
	/**
	 * 为指定套餐评分
	 * @param score 评分
	 * @param userId
	 * @param packageId 套餐Id
	 * @return
	 */
	public boolean scorePackage(int score,int userId,int packageId){
		PackageScorePO packageScorePO=new PackageScorePO();
		packageScorePO.setScore(score);
		packageScorePO.setUserId(userId);
		packageScorePO.setPackageId(packageId);
		return packageScoreDao.insert(packageScorePO);
	}
	
}
