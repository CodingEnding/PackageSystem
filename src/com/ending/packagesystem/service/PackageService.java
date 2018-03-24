package com.ending.packagesystem.service;

import java.util.ArrayList;
import java.util.List;

import com.ending.packagesystem.config.Constants;
import com.ending.packagesystem.dao.PackageDao;
import com.ending.packagesystem.dao.PackageScoreDao;
import com.ending.packagesystem.po.PackagePO;
import com.ending.packagesystem.po.PackageScorePO;
import com.ending.packagesystem.vo.PackageVO;
import com.ending.packagesystem.vo.SimplePackageVO;

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
	 * 获取热门套餐
	 * @param limit 最大数据条数
	 * @return
	 */
	public List<SimplePackageVO> getAllHotPackage(int limit){
		List<PackagePO> packageList=packageDao.findAllTopPackage(
				Constants.PACKAGE_COLUMN_STAR,limit);
		List<SimplePackageVO> simplePackageList=new ArrayList<>();
		for(PackagePO packagePO:packageList){//将PackagePO转化为SimplePackageVO
			SimplePackageVO simplePackageVO=SimplePackageVO.build(packagePO);//快速构建SimplePackageVO
			simplePackageList.add(simplePackageVO);
		}
		return simplePackageList;
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
	 * 根据关键词查询套餐数据
	 * @param key
	 */
	public List<SimplePackageVO> getAllSimplePackageByKey(String key){
		List<SimplePackageVO> simplePackageList=new ArrayList<>();
		List<PackagePO> packageList=packageDao.findAllByKey(key);
		for(PackagePO packagePO:packageList){
			SimplePackageVO simplePackageVO=SimplePackageVO.build(packagePO);//快速构建SimplePackageVO
			simplePackageList.add(simplePackageVO);
		}
		return simplePackageList;
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
	
	/**
	 * 根据套餐Id获取其所有的评分数据
	 * @param packageId
	 * @return List<PackageScorePO>
	 */
	public List<PackageScorePO> getAllPackageScore(int packageId){
		return packageScoreDao.findAll(packageId);
	}
	
	/**
	 * 更新指定Id的套餐数据
	 * @param id
	 * @param packagePO
	 * @return
	 */
	public boolean updatePackageById(int id,PackagePO packagePO){
		return packageDao.updatePackageById(id, packagePO);
	}
	
}
