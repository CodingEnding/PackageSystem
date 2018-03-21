package com.ending.packagesystem.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


import com.ending.packagesystem.po.PackagePO;
import com.ending.packagesystem.service.PackageService;
import com.ending.packagesystem.utils.MathUtils;
import com.ending.packagesystem.vo.FlowConsumeVO;
import com.ending.packagesystem.vo.PackageVO;
import com.ending.packagesystem.vo.UserConsumeVO;

/**
 * 核心推荐算法
 * @author CodingEnding
 */
public class RecommentCore {
	public static final int EXTRA_FLOW_TYPE_ONE=1;//形式：全国流量10元/GB
	public static final int EXTRA_FLOW_TYPE_TWO=2;//形式：全国日租1元800M
	public static final int EXTRA_FLOW_TYPE_THREE=3;//形式：省内日租1元800M 省外日租2元800M
	public static final int EXTRA_FLOW_TYPE_FOUR=4;//形式：省内日租1元800M 省外流量0.1元/GB
	public static final int EXTRA_FLOW_TYPE_FIVE=5;//形式：全国无限流量
	public static final int EXTRA_FLOW_TYPE_SIX=6;//形式：全国日租3元无限流量
	public static final int EXTRA_FLOW_TYPE_SEVEN=7;//形式：省内日租3元无限流量 省外日租2元800M
	public static final int MONTH_DAY=30;//一个月的天数
	
	private static RecommentCore recommentCore=new RecommentCore();
	private static List<PackagePO> allPackageList;//装载所有的套餐

	private RecommentCore(){}
	
	/********************内部方法********************/
	//返回推荐结果（套餐列表）
	private List<PackageVO> recomment_(UserConsumeVO userConsume){
		List<PackageVO> packageList=new ArrayList<>();
		PackageService packageService=new PackageService();
		allPackageList=packageService.getAllPackage();
		Set<PackageConsume> consumeSet=new TreeSet<>();//存储套餐和它需要消耗的费用（自然排序）
		
//		System.out.println("allPackageList:"+allPackageList.size());//TODO
		int callTime=userConsume.getCallTime();//通话时长
		int totalFlow=userConsume.getAllFlow();//总的流量消耗量
		int provinceOutDay=userConsume.getProvinceOutDay();//每月在省外的天数
		List<FlowConsumeVO> flowConsumeList=userConsume.getFlowConsumeList();//各个应用的流量消耗
		
		for(int i=0;i<allPackageList.size();i++){
			PackagePO packagePO=allPackageList.get(i);
			double consume=calcuteCallConsume(packagePO,callTime)+
					calcuteFlowConsume(packagePO, provinceOutDay, 
					totalFlow, flowConsumeList)+packagePO.getMonthRent();
			PackageConsume packageConsume=new PackageConsume(packagePO.getId(),
					packagePO.getName(),consume);
			consumeSet.add(packageConsume);
		}
		Iterator<PackageConsume> iterator=consumeSet.iterator();
		int count=0;
		while(iterator.hasNext()&&count<15){
			PackageConsume temp=iterator.next();
			PackagePO packagePO=packageService.getPackageById(temp.getPackageId());
			int extraFlowType=packagePO.getExtraFlowTypeId();//TODO 暂时进行这样的快捷处理（因为extraFlowType表中的type和id保持一致）
			packageList.add(PackageVO.build(packagePO,extraFlowType,temp.getConsume()));
			count++;
		}
//		System.out.println("packageList:"+packageList.size());//TODO
		return packageList;
	}
	
	/**
	 * 计算指定套餐需要支付的流量费用
	 * @param packagePO 套餐
	 * @param provinceOutDay 在省外的天数
	 * @param totalFlow 总流量
	 * @param flowConsumeList 应用流量消耗列表
	 * @return 需要支付的流量费用
	 */
	private double calcuteFlowConsume(PackagePO packagePO,int provinceOutDay,
			int totalFlow,List<FlowConsumeVO> flowConsumeList){
		double flowConsume=0;
		int leaveProvinceOutFlow=totalFlow*provinceOutDay/MONTH_DAY;//剩余需要介入计费的省外流量
		int leaveProvinceInFlow=totalFlow-leaveProvinceOutFlow;//剩余需要介入计费的省内流量
		if(packagePO.getFreeFlowType()==1&&flowConsumeList.size()>0){//剔除免费流量
			//TODO 增加免流应用的影响
		}
		leaveProvinceInFlow-=packagePO.getPackageProvinceFlow();//剔除套餐内省内流量
		leaveProvinceInFlow=MathUtils.positiveNum(leaveProvinceInFlow);//避免小于0
		int leaveTotalFlow=MathUtils.positiveNum(leaveProvinceOutFlow+
				leaveProvinceInFlow-packagePO.getPackageCountryFlow());//剩余需要计费的总流量
		
		//开始根据套餐外流量的计费类型计算剩余流量的收费情况
		if(leaveTotalFlow>0){
			flowConsume=calculateExtraFlowConsume(packagePO,
					leaveTotalFlow,provinceOutDay);
		}
		
		return flowConsume;
	}
	
	/**
	 * 计算套餐外流量的额外消费
	 * @param packagePO 指定套餐
	 * @param leaveTotalFlow 剩余流量总量
	 * @param provinceOutDay 每月在省外的天数
	 * @return
	 */
	private double calculateExtraFlowConsume(PackagePO packagePO,
			int leaveTotalFlow,int provinceOutDay){
		double flowConsume=0;
		int extraFlowType=packagePO.getExtraFlowTypeId();//TODO 暂时进行这样的快捷处理（因为extraFlowType表中的type和id保持一致）
		int provinceInDay=MONTH_DAY-provinceOutDay;//待在省内的天数
		int leaveProvinceOutFlow=leaveTotalFlow*provinceOutDay/MONTH_DAY;//剔除套餐内流量后需要介入计费的省外流量
		int leaveProvinceInFlow=leaveTotalFlow-leaveProvinceOutFlow;//剔除套餐内流量后需要介入计费的省内流量

		int dayFlow=0;//每天平均消耗的流量
		int dayRentNum=0;//每天要消耗的日租包个数
		int provinceInDayFlow=0;//省内每天平均消耗的流量
		int provinceInDayRentNum=0;//省内每天要消耗的日租包个数
		int provinceOutDayFlow=0;//省外每天平均消耗的流量
		int provinceOutDayRentNum=0;//省外每天要消耗的日租包个数

		switch (extraFlowType) {//根据额外流量的计费方法计算套餐外流量费用
		case EXTRA_FLOW_TYPE_ONE:
			flowConsume=leaveTotalFlow*packagePO.getExtraCountryFlow();
			break;
		case EXTRA_FLOW_TYPE_TWO:
			dayFlow=leaveTotalFlow/MONTH_DAY;//每天平均消耗的流量
			dayRentNum=calculateDayRentNum(dayFlow,packagePO.getExtraCountryDayFlow());//每天要消耗的日租包个数
			flowConsume=dayRentNum*MONTH_DAY;
			break;
		case EXTRA_FLOW_TYPE_THREE:
			provinceInDayFlow=leaveProvinceInFlow/provinceInDay;//省内每天平均消耗的流量
			provinceInDayRentNum=calculateDayRentNum(provinceInDayFlow,
					packagePO.getExtraProvinceInDayFlow());//省内每天要消耗的日租包个数
			if(provinceOutDay>0){//在省外的天数大于0才计算省外的日租
				provinceOutDayFlow=leaveProvinceOutFlow/provinceOutDay;//省外每天平均消耗的流量
				provinceOutDayRentNum=calculateDayRentNum(provinceOutDayFlow,
						packagePO.getExtraProvinceOutDayFlow());//省外每天要消耗的日租包个数
			}
			flowConsume=provinceInDayRentNum*packagePO.getExtraProvinceInDayRent()*provinceInDay+
				provinceOutDayRentNum*packagePO.getExtraProvinceOutDayRent()*provinceOutDay;
			break;
		case EXTRA_FLOW_TYPE_FOUR:
			provinceInDayFlow=leaveProvinceInFlow/provinceInDay;//省内每天平均消耗的流量
			provinceInDayRentNum=calculateDayRentNum(provinceInDayFlow,
					packagePO.getExtraProvinceInDayFlow());//省内每天要消耗的日租包个数
			flowConsume=provinceInDayRentNum*packagePO.getExtraProvinceInDayRent()*provinceInDay+
				leaveProvinceOutFlow*packagePO.getExtraProvinceOutFlow();
			break;
		case EXTRA_FLOW_TYPE_FIVE:
			flowConsume=0;
			break;
		case EXTRA_FLOW_TYPE_SIX:
			flowConsume=MONTH_DAY*packagePO.getExtraCountryDayRent();
			break;
		case EXTRA_FLOW_TYPE_SEVEN:
			if(provinceOutDay>0){//在省外的天数大于0才计算省外的日租
				provinceOutDayFlow=leaveProvinceOutFlow/provinceOutDay;//省外每天平均消耗的流量
				provinceOutDayRentNum=calculateDayRentNum(provinceOutDayFlow,
						packagePO.getExtraProvinceOutDayFlow());//省外每天要消耗的日租包个数
			}
			flowConsume=provinceInDay*packagePO.getExtraProvinceInDayRent()+
				provinceOutDay*provinceOutDayRentNum*packagePO.getExtraProvinceOutDayRent();
			break;
		default:
			break;
		}
		return flowConsume;
	}
	
	
	
	/**
	 * 计算每日需要的日租包
	 * @param dayFlow 当日平均需要消耗的流量
	 * @param packageExtraDayFlow 一个日租包包含的流量
	 * @return 日租包个数
	 */
	private int calculateDayRentNum(int dayFlow,int packageExtraDayFlow){
		int dayRentNum=0;
		if(packageExtraDayFlow!=0){
			dayRentNum=(int) Math.ceil(dayFlow*1.0/packageExtraDayFlow);//向上取整
			dayRentNum=MathUtils.leastNum(dayRentNum,1);//最小为1
			
		}
		return dayRentNum;
	}
	
	/**
	 * 计算指定套餐需要支付的通话费用
	 * @param packagePO 套餐
	 * @param callTime 通话时长
	 * @return 通话费用
	 */
	private double calcuteCallConsume(PackagePO packagePO,int callTime){
		double callConsume=0;
		int packageCallTime=packagePO.getPackageCall();//套餐内通话时长
		double extraPackageCall=packagePO.getExtraPackageCall();//套餐外通话费用
		if(callTime>packageCallTime){
			callConsume=extraPackageCall*(callTime-packageCallTime);
		}
//		if(packagePO.getId()==131){//TODO
//			System.out.println("callTime:"+callTime);
//			System.out.println("packageCallTime:"+packageCallTime);
//			System.out.println("extraPackageCall:"+extraPackageCall);
//			System.out.println("callConsume:"+callConsume);
//		}
		return callConsume;
	}
	

	/*******************以下是提供给外部的方法********************/
	
	/**
	 * 返回推荐结果（套餐列表）
	 * @param userConsume 用户消费
	 * @return List<PackagePO>
	 */
	public static List<PackageVO> recomment(UserConsumeVO userConsume){
		return recommentCore.recomment_(userConsume);
	}
}
