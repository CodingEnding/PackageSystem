package com.ending.packagesystem.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ending.packagesystem.config.StatusCode;
import com.ending.packagesystem.po.DevicePO;
import com.ending.packagesystem.service.DeviceService;
import com.ending.packagesystem.vo.BaseResponse;
import com.ending.packagesystem.vo.DataResponse;

/**
 * 统一处理与用户设备相关的请求
 * @author CodingEnding
 */
@WebServlet(urlPatterns={"/api/v1/device/backup","/api/v1/device/list"})
public class DeviceServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String URL_BACKUP="/api/v1/device/backup";//备份设备信息（POST）
	private static final String URL_LIST="/api/v1/device/list";//获取设备列表（GET）

	private DeviceService deviceService=new DeviceService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initCharsetContentType(response);
		String servletPath=request.getServletPath();
		switch (servletPath) {
		case URL_LIST:
			doList(request, response);
			break;
		default:
			response.setStatus(405);
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initCharsetContentType(response);
		String servletPath=request.getServletPath();
		switch (servletPath) {
		case URL_BACKUP:
			doBackup(request, response);
			break;
		default:
			doGet(request, response);
			break;
		}
	}

	//更新设备信息
	private void doBackup(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String deviceType=request.getParameter("device_type");
		String systemVersion=request.getParameter("system_version");
		String deviceFinger=request.getParameter("device_finger");
		String email=request.getParameter("email");
		String sessionToken=request.getParameter("session_token");
		
		//插入或备份设备信息
		boolean isSucceed=deviceService.addOrUpdate(email,sessionToken,
				deviceType,systemVersion,deviceFinger);
		BaseResponse jsonResponse=null;
		if(isSucceed){
			jsonResponse=new BaseResponse(true,StatusCode.CODE_SUCCEED);
		}else{
			jsonResponse=new BaseResponse(false,StatusCode.CODE_DEVICE_BACKUP_ERROR);
		}
		writeJsonToClient(jsonResponse, response);
	}

	//获取设备列表
	private void doList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String email=request.getParameter("email");
		String sessionToken=request.getParameter("session_token");
		
		DataResponse<List<DevicePO>> jsonResponse=null;
		List<DevicePO> dataList=deviceService.getDeviceList(email,sessionToken);
		if(dataList!=null){
			jsonResponse=new DataResponse<List<DevicePO>>(true,StatusCode.CODE_SUCCEED,dataList);
		}else{
			jsonResponse=new DataResponse<List<DevicePO>>(false,StatusCode.CODE_NO_PRIVILEGE,null);
		}
		writeJsonToClient(jsonResponse, response);
	}

}
