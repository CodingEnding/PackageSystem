package com.ending.packagesystem.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ending.packagesystem.config.Constants;
import com.ending.packagesystem.config.StatusCode;
import com.ending.packagesystem.po.UserPO;
import com.ending.packagesystem.service.UserService;
import com.ending.packagesystem.vo.DataResponse;
import com.google.gson.Gson;

/**
 * 统一处理与用户相关的请求
 * @author CodingEnding
 */
@WebServlet(urlPatterns={"/api/v1/user/login",
		"/api/v1/user/register","/api/v1/user/forget"})
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public static final String URL_LOGIN="/api/v1/user/login";//用户登录（GET）
    public static final String URL_REGISTER="/api/v1/user/register";//用户注册（POST）
    public static final String URL_FORGET="/api/v1/user/forget";//找回密码（POST）
       
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
		case URL_REGISTER:
		case URL_FORGET:	
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
	
}
