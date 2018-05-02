package com.ending.packagesystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ending.packagesystem.config.Constants;
import com.ending.packagesystem.po.PackagePO;
import com.ending.packagesystem.utils.DBUtils;
import com.ending.packagesystem.utils.DebugUtils;

public class PackageDao {
	/**
	 * 根据id查询相应的套餐
	 * @param id
	 * @return PackagePO
	 */
	public PackagePO findPackageById(int id){
		Connection connection=null;
		PackagePO packagePO=new PackagePO();
		packagePO.setId(Constants.QUERY_ERROR_ID);//将默认设置为异常值，在查询失败时调用者可以察觉
		try {
			connection=DBUtils.getConnection();
			String sql="select * from package where id=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet=statement.executeQuery();
			if(resultSet.next()){
				deployPackagePO(packagePO,resultSet);//为PackagePO设置属性
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.closeConnection(connection);
		}
		return packagePO;
	}
	
	/**
	 * 根据运营商返回相应的套餐列表
	 * @param operatorArray 运营商
	 * @return 套餐列表
	 */
	public List<PackagePO> findAllByOperator(List<String> operatorList){
		List<PackagePO> packageList=new ArrayList<>();
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			int operatorSize=operatorList.size();
			PreparedStatement statement=null;
			if(operatorSize==1){//参数个数不同sql也不同
				String sql="select * from package where operator=?";
			    statement=connection.prepareStatement(sql);
				statement.setString(1,operatorList.get(0));
			}else if(operatorSize==2){
				String sql="select * from package where operator=? or operator=?";
			    statement=connection.prepareStatement(sql);
				statement.setString(1,operatorList.get(0));
				statement.setString(2,operatorList.get(1));
			}else{
				String sql="select * from package where operator=? or operator=? or operator=?";
				statement=connection.prepareStatement(sql);
				statement.setString(1,operatorList.get(0));
				statement.setString(2,operatorList.get(1));
				statement.setString(3,operatorList.get(2));
			}
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next()){
				PackagePO packagePO=new PackagePO();
				deployPackagePO(packagePO,resultSet);//为PackagePO设置属性
				packageList.add(packagePO);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.closeConnection(connection);
		}
		
		return packageList;
	}
	
	/**
	 * 根据关键词返回所有套餐
	 * @param key
	 * @param limit
	 * @param page
	 */
	public List<PackagePO> findAllByKey(String key,int limit,int page){
		List<PackagePO> packageList=new ArrayList<>();
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="select * from package where name like ?"
					+ "or operator like ? or partner like ? limit ? offset ?";
			PreparedStatement statement=connection.prepareStatement(sql);
			String keyParameter="%"+key+"%";//传入的参数需要先添加两侧的通配符
			statement.setString(1,keyParameter);
			statement.setString(2,keyParameter);
			statement.setString(3,keyParameter);
			statement.setInt(4,limit);
			int offsetNum=(page-1)*limit;//计算需要跳过的条目数
			statement.setInt(5,offsetNum);
			
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next()){
				PackagePO packagePO=new PackagePO();
				deployPackagePO(packagePO,resultSet);//为PackagePO设置属性
				packageList.add(packagePO);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.closeConnection(connection);
		}
		return packageList;
	}
	
	/**
	 * 根据分类返回所有套餐
	 * @param categoryName 分类名称
	 * @param categoryValue 分类的具体值
	 * @param limit 每页大小
	 * @param page 页码（从1开始）
	 * @return
	 */
	public List<PackagePO> findAllByCategory(String categoryName,
			String categoryValue,int limit,int page){
		List<PackagePO> packageList=new ArrayList<>();
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql=String.format("select * from package where %s=? limit ? offset ?",categoryName);
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,categoryValue);
			statement.setInt(2,limit);
			int offsetNum=(page-1)*limit;//计算需要跳过的条目数
			statement.setInt(3,offsetNum);
			
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next()){
				PackagePO packagePO=new PackagePO();
				deployPackagePO(packagePO,resultSet);//为PackagePO设置属性
				packageList.add(packagePO);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.closeConnection(connection);
		}
		return packageList;
	}
	
	/**
	 * 根据extra_flow_type获取套餐列表
	 * @param flowTypeList 包含多个extra_flow_type
	 * @param limit
	 * @param page
	 * @return
	 */
	public List<PackagePO> findAllByExtraType(List<Integer> flowTypeList,int limit,int page){
		List<PackagePO> packageList=new ArrayList<>();
		if(flowTypeList==null||flowTypeList.isEmpty()){//边界情况
			return packageList;
		}
		Connection connection=null;
		
		//生成关于extraflowtype相关的预查询语句 TODO 暂时使用extra_flow_type_id
		String flowTypeQuery="extra_flow_type_id=?";
		for(int i=1;i<flowTypeList.size();i++){
			flowTypeQuery+=" or extra_flow_type_id=?";
		}
//		DebugUtils.println("PackageDao",flowTypeQuery);
		try {
			connection=DBUtils.getConnection();
			String sql=String.format("select * from package where %s limit ? offset ?",flowTypeQuery);
			PreparedStatement statement=connection.prepareStatement(sql);
			
			for(int i=0;i<flowTypeList.size();i++){//逐个注入值
				statement.setInt(i+1,flowTypeList.get(i));
			}
			int size=flowTypeList.size();
			statement.setInt(size+1,limit);
			int offsetNum=(page-1)*limit;//计算需要跳过的条目数
			statement.setInt(size+2,offsetNum);
			
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next()){
				PackagePO packagePO=new PackagePO();
				deployPackagePO(packagePO,resultSet);//为PackagePO设置属性
				packageList.add(packagePO);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.closeConnection(connection);
		}
		return packageList;
	}
	
	/**
	 * 根据free_flow_type返回套餐列表
	 */
	public List<PackagePO> findAllByFreeFlow(int freFlow,int limit,int page){
		List<PackagePO> packageList=new ArrayList<>();
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="select * from package where free_flow_type=? limit ? offset ?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setInt(1,freFlow);
			statement.setInt(2,limit);
			int offsetNum=(page-1)*limit;//计算需要跳过的条目数
			statement.setInt(3,offsetNum);
			
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next()){
				PackagePO packagePO=new PackagePO();
				deployPackagePO(packagePO,resultSet);//为PackagePO设置属性
				packageList.add(packagePO);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.closeConnection(connection);
		}
		return packageList;
	}
	
	/**
	 * 返回所有的套餐
	 * @return List<PackagePO>
	 */
	public List<PackagePO> findAll(){
		Connection connection=null;
		List<PackagePO> packageList=new ArrayList<>();
		try {
			connection=DBUtils.getConnection();
			String sql="select * from package";
			PreparedStatement statement=connection.prepareStatement(sql);
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next()){
				PackagePO packagePO=new PackagePO();
				deployPackagePO(packagePO, resultSet);
				packageList.add(packagePO);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.closeConnection(connection);
		}
		return packageList;
	}
	
	/**
	 * 返回所有的套餐（有limit和page）
	 */
	public List<PackagePO> findAll(int limit,int page){
		Connection connection=null;
		List<PackagePO> packageList=new ArrayList<>();
		try {
			connection=DBUtils.getConnection();
			String sql="select * from package limit ? offset ?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setInt(1,limit);
			int offsetNum=(page-1)*limit;//计算需要跳过的条目数
			statement.setInt(2,offsetNum);
			
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next()){
				PackagePO packagePO=new PackagePO();
				deployPackagePO(packagePO, resultSet);
				packageList.add(packagePO);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.closeConnection(connection);
		}
		return packageList;
	}
	
	/**
	 * 根据排序条件，返回TOP-N个数据
	 * @param orderColumn 作为排序依据的列名
	 * @param limit 返回的数据条数
	 * @param page 页码
	 */
	public List<PackagePO> findAllTopPackage(String orderColumn,int limit,int page){
		List<PackagePO> packageList=new ArrayList<>();
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="select * from package where operator != '中国移动' "
					+ "order by ? DESC limit ? offset ?";//TODO 暂时不让中国移动参与排序
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,orderColumn);
			statement.setInt(2,limit);
			int offsetNum=(page-1)*limit;//计算需要跳过的条目数
			statement.setInt(3,offsetNum);
			
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next()){
				PackagePO packagePO=new PackagePO();
				deployPackagePO(packagePO, resultSet);
				packageList.add(packagePO);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
		}
		return packageList;
	}
	
	/**
	 * 更新指定Id的套餐数据
	 * @param id
	 * @param packagePO 封装需要更新的内容
	 * @return
	 */
	public boolean updatePackageById(int id,PackagePO packagePO){
		boolean isSucceed=false;
		Connection connection=null;
		try {
			connection=DBUtils.getConnection();
			String sql="update package set name=?,partner=?,operator=?,month_rent=?,"
					+ "package_flow_country=?,package_flow_province=?,package_call=?,"
					+ "extra_package_call=?,extra_flow_country=?,extra_flow_province=?,"
					+ "extra_flow_province_out=?,extra_rent_day_country=?,extra_flow_day_country=?,"
					+ "extra_rent_day_province_in=?,extra_flow_day_province_in=?,"
					+ "extra_rent_day_province_out=?,extra_flow_day_province_out=?,"
					+ "extra_flow_type_id=?,privilege_description=?,star=?,url=?,remark=?,"
					+ "abandon=?,free_flow_type=? where id=?";
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1,packagePO.getName());
			statement.setString(2,packagePO.getPartner());
			statement.setString(3,packagePO.getOperator());
			statement.setInt(4,packagePO.getMonthRent());
			statement.setInt(5,packagePO.getPackageCountryFlow());
			statement.setInt(6,packagePO.getPackageProvinceFlow());
			statement.setInt(7,packagePO.getPackageCall());
			statement.setDouble(8,packagePO.getExtraPackageCall());
			statement.setDouble(9,packagePO.getExtraCountryFlow());
			statement.setDouble(10,packagePO.getExtraProvinceFlow());
			statement.setDouble(11,packagePO.getExtraProvinceOutFlow());
			statement.setInt(12,packagePO.getExtraCountryDayRent());
			statement.setInt(13,packagePO.getExtraCountryDayFlow());
			statement.setInt(14,packagePO.getExtraProvinceInDayRent());
			statement.setInt(15,packagePO.getExtraProvinceInDayFlow());
			statement.setInt(16,packagePO.getExtraProvinceOutDayRent());
			statement.setInt(17,packagePO.getExtraProvinceOutDayFlow());
			statement.setInt(18,packagePO.getExtraFlowTypeId());
			statement.setString(19,packagePO.getPrivilegeDescription());
			statement.setDouble(20,packagePO.getStar());
			statement.setString(21,packagePO.getUrl());
			statement.setString(22,packagePO.getRemark());
			statement.setInt(23,packagePO.getAbandon());
			statement.setInt(24,packagePO.getFreeFlowType());
			statement.setInt(25,packagePO.getId());
			
			int end=statement.executeUpdate();
			if(end==1){//返回结果只应该改变一行
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
	
	//为PackagePO对象设置属性（从ResultSet中获取数据）
	private void deployPackagePO(PackagePO packagePO,ResultSet resultSet) throws SQLException{
		packagePO.setId(resultSet.getInt("id"));
		packagePO.setName(resultSet.getString("name"));
		packagePO.setPartner(resultSet.getString("partner"));
		packagePO.setOperator(resultSet.getString("operator"));
		packagePO.setMonthRent(resultSet.getInt("month_rent"));
		packagePO.setPackageCountryFlow(resultSet.getInt("package_flow_country"));
		packagePO.setPackageProvinceFlow(resultSet.getInt("package_flow_province"));
		packagePO.setPackageCall(resultSet.getInt("package_call"));
		packagePO.setExtraPackageCall(resultSet.getDouble("extra_package_call"));
		packagePO.setExtraCountryFlow(resultSet.getDouble("extra_flow_country"));
		packagePO.setExtraProvinceFlow(resultSet.getDouble("extra_flow_province"));
		packagePO.setExtraProvinceOutFlow(resultSet.getDouble("extra_flow_province_out"));
		packagePO.setExtraCountryDayRent(resultSet.getInt("extra_rent_day_country"));
		packagePO.setExtraCountryDayFlow(resultSet.getInt("extra_flow_day_country"));
		packagePO.setExtraProvinceInDayRent(resultSet.getInt("extra_rent_day_province_in"));
		packagePO.setExtraProvinceInDayFlow(resultSet.getInt("extra_flow_day_province_in"));
		packagePO.setExtraProvinceOutDayRent(resultSet.getInt("extra_rent_day_province_out"));
		packagePO.setExtraProvinceOutDayFlow(resultSet.getInt("extra_flow_day_province_out"));
		packagePO.setExtraFlowTypeId(resultSet.getInt("extra_flow_type_id"));
		packagePO.setPrivilegeDescription(resultSet.getString("privilege_description"));
		packagePO.setStar(resultSet.getDouble("star"));
		packagePO.setUrl(resultSet.getString("url"));
		packagePO.setRemark(resultSet.getString("remark"));
		packagePO.setAbandon(resultSet.getInt("abandon"));
		packagePO.setFreeFlowType(resultSet.getInt("free_flow_type"));
	}
	
}
