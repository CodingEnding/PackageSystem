package com.ending.packagesystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ending.packagesystem.po.PackagePO;
import com.ending.packagesystem.utils.DBUtils;

public class PackageDao {
	/**
	 * 根据id查询相应的套餐
	 * @param id
	 * @return PackagePO
	 */
	public PackagePO findPackageById(int id){
		Connection connection=null;
		PackagePO packagePO=new PackagePO();
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
