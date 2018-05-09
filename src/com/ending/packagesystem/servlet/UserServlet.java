package com.ending.packagesystem.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ending.packagesystem.config.Config;
import com.ending.packagesystem.config.Constants;
import com.ending.packagesystem.config.StatusCode;
import com.ending.packagesystem.po.NotificationPO;
import com.ending.packagesystem.po.UserPO;
import com.ending.packagesystem.service.UserService;
import com.ending.packagesystem.utils.MathUtils;
import com.ending.packagesystem.vo.BaseResponse;
import com.ending.packagesystem.vo.DataResponse;
import com.google.gson.Gson;

/**
 * 统一处理与用户相关的请求
 * @author CodingEnding
 */
@WebServlet(urlPatterns={"/api/v1/user/login",
		"/api/v1/user/register","/api/v1/user/forget",
		"/api/v1/user/forgetCode","/api/v1/user/modifyInfo",
		"/api/v1/user/modifyPwd","/api/v1/user/feedback",
		"/api/v1/user/notification"})
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String URL_LOGIN="/api/v1/user/login";//用户登录（GET）
	private static final String URL_REGISTER="/api/v1/user/register";//用户注册（POST）
	private static final String URL_FORGET="/api/v1/user/forget";//找回密码（POST）
	private static final String URL_FORGET_CODE="/api/v1/user/forgetCode";//获取找回密码的验证码（GET）
	private static final String URL_MODIFY_INFO="/api/v1/user/modifyInfo";//修改用户信息（POST）
	private static final String URL_MODIFY_PWD="/api/v1/user/modifyPwd";//修改密码（POST）
	private static final String URL_FEEDBACK="/api/v1/user/feedback";//发送反馈（POST）
	private static final String URL_NOTIFICATION="/api/v1/user/notification";//获取通知（GET）

	private UserService userService=new UserService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initCharsetContentType(response);//设置响应的字符编码和内容格式
		String servletPath=request.getServletPath();
		switch (servletPath) {
		case URL_LOGIN:
			doLogin(request,response);
			break;
		case URL_FORGET_CODE:
			doGetForgetCode(request,response);
			break;
		case URL_NOTIFICATION:
			doGetNotification(request,response);
			break;
		case URL_REGISTER:
		case URL_FORGET:
		case URL_MODIFY_INFO:
		case URL_MODIFY_PWD:
		case URL_FEEDBACK:	
			response.setStatus(405);
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
		initCharsetContentType(response);//设置响应的字符编码和内容格式
		String servletPath=request.getServletPath();
		switch (servletPath) {
		case URL_REGISTER:
			doRegister(request, response);
			break;
		case URL_FORGET:
			doForget(request,response);
			break;
		case URL_MODIFY_INFO:
			doModifyInfo(request,response);
			break;
		case URL_MODIFY_PWD:
			doModifyPwd(request,response);
			break;
		case URL_FEEDBACK:
			doFeedBack(request,response);
			break;
		default:
			doGet(request, response);
			break;
		}
	}

	//进行新用户注册
	private void doRegister(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String postBody=getPostBody(request);//读取POST body
		UserPO userPO=new Gson().fromJson(postBody,UserPO.class);
		int end=userService.register(userPO);
		DataResponse<Integer> jsonResponse;
		if(end==Constants.REGISTER_EMAIL_EXSIT){//邮箱已存在
			jsonResponse=new DataResponse<Integer>(false,StatusCode.CODE_EMAIL_EXSIT,null);
		}else if(end==Constants.REGISTER_ERROR_ID){//数据插入失败
			jsonResponse=new DataResponse<Integer>(false,StatusCode.CODE_REGISTER_ERROR,null);
		}else{//注册成功（返回Id）
			jsonResponse=new DataResponse<Integer>(true,StatusCode.CODE_SUCCEED,end);
		}
		writeJsonToClient(jsonResponse,response);//返回数据相应
	}

	//登录功能
	private void doLogin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String deviceType=request.getParameter("device_type");
		String systemVersion=request.getParameter("system_version");
		String deviceFinger=request.getParameter("device_finger");

		UserPO userPO=userService.login(email,password,deviceType,
				systemVersion,deviceFinger);

		DataResponse<UserPO> jsonResponse;
		if(userPO.getId()==Constants.LOGIN_ERROR_ID){//登录失败
			jsonResponse=new DataResponse<UserPO>(false,StatusCode.CODE_LOGIN_ERROR,null);
		}else{
			jsonResponse=new DataResponse<UserPO>(true,StatusCode.CODE_SUCCEED,userPO);
		}
		writeJsonToClient(jsonResponse,response);//返回数据相应
	}

	//修改用户信息
	private void doModifyInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int type=MathUtils.legalIntNum(request.getParameter("type"),UserService.TYPE_USERNAME);
		String content=request.getParameter("content");
		String email=request.getParameter("email");
		String sessionToken=request.getParameter("session_token");
		int code=userService.modifyInfo(type,content,sessionToken,email);
		
		BaseResponse jsonResponse=null;
		if(code==StatusCode.CODE_SUCCEED){
			jsonResponse=new BaseResponse(true,StatusCode.CODE_SUCCEED);
		}else{
			jsonResponse=new BaseResponse(false,code);
		}
		writeJsonToClient(jsonResponse, response);
	}

	//修改密码
	private void doModifyPwd(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String email=request.getParameter("email");
		String oldPwd=request.getParameter("old_pwd");
		String newPwd=request.getParameter("new_pwd");
		int code=userService.modifyPwd(email,oldPwd,newPwd);
		
		BaseResponse jsonResponse=null;
		if(code==StatusCode.CODE_SUCCEED){
			jsonResponse=new BaseResponse(true,StatusCode.CODE_SUCCEED);
		}else{
			jsonResponse=new BaseResponse(false,code);
		}
		writeJsonToClient(jsonResponse, response);
	}
	
	//接收用户反馈
	private void doFeedBack(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String email=request.getParameter("email");
		String content=request.getParameter("content");
		String deviceFinger=request.getParameter("device_finger");//设备指纹
		int code=userService.saveFeedback(email,deviceFinger,content);
		
		BaseResponse jsonResponse=null;
		if(code==StatusCode.CODE_SUCCEED){
			jsonResponse=new BaseResponse(true,StatusCode.CODE_SUCCEED);
		}else{
			jsonResponse=new BaseResponse(false,code);
		}
		writeJsonToClient(jsonResponse, response);
	}
	
	//获取系统通知
	private void doGetNotification(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int limit=MathUtils.legalIntNum(request.getParameter("limit"),Config.NOTIFICAITON_COUNT);
		int page=MathUtils.legalIntNum(request.getParameter("page"),1);
		
		List<NotificationPO> dataList=userService.getNotificationList(limit,page);
		DataResponse<List<NotificationPO>> jsonResponse=null;
		if(dataList.isEmpty()){//此时无数据
			jsonResponse=new DataResponse<List<NotificationPO>>(false,StatusCode.CODE_QUERY_NONE,dataList);
		}else{
			jsonResponse=new DataResponse<List<NotificationPO>>(true,StatusCode.CODE_SUCCEED,dataList);
		}
		writeJsonToClient(jsonResponse,response);
	}
	
	//获取[忘记密码]的验证码
	private void doGetForgetCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String email=request.getParameter("email");
		int code=userService.getForgetCode(email);
		
		BaseResponse jsonResponse=null;
		if(code==StatusCode.CODE_SUCCEED){
			jsonResponse=new BaseResponse(true,StatusCode.CODE_SUCCEED);
		}else{
			jsonResponse=new BaseResponse(false,code);
		}
		writeJsonToClient(jsonResponse, response);
	}
	
	//重置密码
	private void doForget(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String email=request.getParameter("email");
		String password=request.getParameter("password");//[加密后的密码]
		String code=request.getParameter("code");//验证码
		int statusCode=userService.doForget(email,password,code);
		
		BaseResponse jsonResponse=null;
		if(statusCode==StatusCode.CODE_SUCCEED){
			jsonResponse=new BaseResponse(true,StatusCode.CODE_SUCCEED);
		}else{
			jsonResponse=new BaseResponse(false,statusCode);
		}
		writeJsonToClient(jsonResponse,response);
	}

}
