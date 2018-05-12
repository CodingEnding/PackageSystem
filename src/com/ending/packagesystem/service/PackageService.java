package com.ending.packagesystem.service;

import java.util.ArrayList;
import java.util.Arrays;
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

	public static final String CATEGORY_NAME_SINGLE="SINGLE_CATEGORY";//单分类情况下的category_name（比如查询日租卡、免流特权卡...）
	public static final String CATEGORY_DAY_RENT="DAY_RENT";//[分类]日租卡
	public static final String CATEGORY_FREE_FLOW="FREE_FLOW";//[分类]免流特权卡
	public static final String CATEGORY_INFINITE_FLOW="INFINITE_FLOW";//[分类]无限流量卡
	public static final String CATEGORY_ALL="ALL";//[分类]全部种类

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
	 * @param page 页码
	 */
	public List<SimplePackageVO> getAllHotPackage(int limit,int page){
		List<PackagePO> packageList=packageDao.findAllTopPackage(
				Constants.PACKAGE_COLUMN_STAR,limit,page);
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
	 * @param limit
	 * @param page
	 */
	public List<SimplePackageVO> getAllSimplePackageByKey(String key,int limit,int page){
		List<SimplePackageVO> simplePackageList=new ArrayList<>();
		List<PackagePO> packageList=packageDao.findAllByKey(key,limit,page);
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
		
		boolean isSucceed=false;
		if(packageScoreDao.isExsit(packageScorePO)){//数据已存在则更新评分
			isSucceed=packageScoreDao.update(packageScorePO);
		}else{
			isSucceed=packageScoreDao.insert(packageScorePO);
		}
		return isSucceed;
	}
	
	/**
	 * 获取指定用户对指定套餐的评分
	 * @param userId
	 * @param packageId
	 * @return 返回套餐评分对象
	 */
	public PackageScorePO getMyScore(int userId,int packageId){
		return packageScoreDao.findByUserId(userId,packageId);
	}
	
	/**
	 * 获取指定套餐的评分数量
	 * @param packageId
	 * @return 套餐评分数量或非法状态码
	 */
	public int getScoreCount(int packageId){
		return packageScoreDao.findScoreCountByPackageId(packageId);
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

	/**
	 * 获取指定分类的套餐数据
	 * @param categoryName
	 * @param categoryValue
	 * @param limit
	 * @param page
	 * @return List<SimplePackageVO>
	 */
	public List<SimplePackageVO> getAllSimplePackageByCategory(String categoryName,
			String categoryValue,int limit,int page){
		List<SimplePackageVO> simplePackageList=new ArrayList<>();
		List<PackagePO> packageList=null;

		//根据分类浏览的方式执行不同操作
		if(categoryName.equals(CATEGORY_NAME_SINGLE)){//单分类模式（如日租卡）
			packageList=getAllPackageBySingleCategory(categoryValue,limit,page);
		}else{//常规分类模式（如按照运营商、合作方分类）
			packageList=packageDao.findAllByCategory(categoryName,categoryValue,limit,page);
		}

		//将PackagePO批量转换为SimplePackageVO
		for(PackagePO packagePO:packageList){
			SimplePackageVO simplePackageVO=SimplePackageVO.build(packagePO);//快速构建SimplePackageVO
			simplePackageList.add(simplePackageVO);
		}
		return simplePackageList;
	}

	//根据一个单独的类别获取相应的套餐列表
	private List<PackagePO> getAllPackageBySingleCategory(String categoryValue,int limit,int page){
		List<PackagePO> dataList=new ArrayList<>();
		switch (categoryValue) {
		case CATEGORY_DAY_RENT://日租卡
			List<Integer> dayRentTypeList=Arrays.asList(2,3,4,6,7);
			dataList=packageDao.findAllByExtraType(dayRentTypeList,limit,page);
			break;
		case CATEGORY_FREE_FLOW://免流特权
			dataList=packageDao.findAllByFreeFlow(PackagePO.FLOW_TYPE_FREE,limit,page);
			break;
		case CATEGORY_INFINITE_FLOW://无限流量
			List<Integer> infiniteTypeList=Arrays.asList(5,6,7);
			dataList=packageDao.findAllByExtraType(infiniteTypeList,limit,page);
			break;
		case CATEGORY_ALL://全部
		default:
			dataList=packageDao.findAll(limit,page);
			break;
		}
		return dataList;
	}

}
